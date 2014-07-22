package com.minecraftmarket.minecraftmarket.util;

import com.minecraftmarket.minecraftmarket.Market;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Update {

	private final Plugin plugin;
	private UpdateType type;
	private String versionName;
	private String versionLink;

	private URL url;
	private File file;
	private Thread thread;

	private final int id = 64572;
	private static final String TITLE_VALUE = "name";
	private static final String LINK_VALUE = "downloadUrl";
	private static final String QUERY = "/servermods/files?projectIds=";
	private static final String HOST = "https://api.curseforge.com";

	private static final String[] NO_UPDATE_TAG = { "-DEV", "-PRE", "-SNAPSHOT" };
	private static final int BYTE_SIZE = 1024;
	private String updateFolder;
	private Update.UpdateResult result = Update.UpdateResult.SUCCESS;

	public enum UpdateResult {
		SUCCESS, NO_UPDATE, DISABLED, FAIL_DOWNLOAD, FAIL_NOVERSION, FAIL_BADID, FAIL_APIKEY, UPDATE_AVAILABLE
	}

	public enum UpdateType {
		DEFAULT, NO_VERSION_CHECK, NO_DOWNLOAD
	}

	public Update() {
		Log.log("Auto-update is enabled. Checking for updates...");
		this.plugin = Market.getPlugin();
		this.type = UpdateType.DEFAULT;
		this.file = Market.getPlugin().getPluginFile();
		this.updateFolder = plugin.getServer().getUpdateFolder();

		try {
			this.url = new URL(Update.HOST + Update.QUERY + id);
		} catch (MalformedURLException e) {
			Log.log("An error has occurred while checking for updates.");
			Log.log(e);
			this.result = UpdateResult.FAIL_BADID;
		}

		this.thread = new Thread(new UpdateRunnable());
		this.thread.start();
	}

	public Update.UpdateResult getResult() {
		this.waitForThread();
		return this.result;
	}


	private void waitForThread() {
		if ((this.thread != null) && this.thread.isAlive()) {
			try {
				this.thread.join();
			} catch (InterruptedException e) {
				Log.log(e);
			}
		}
	}

	private void saveFile(File folder, String file, String link) {
		if (!folder.exists()) {
			folder.mkdir();
		}
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {

			URL url = new URL(link);
			int fileLength = url.openConnection().getContentLength();
			in = new BufferedInputStream(url.openStream());
			fout = new FileOutputStream(folder.getAbsolutePath() + File.separator + file);

			byte[] data = new byte[Update.BYTE_SIZE];

			Log.log(" -------  Update found! Downloading update: " + this.versionName + " ------- ");

			int count;
			long downloaded = 0;
			while ((count = in.read(data, 0, Update.BYTE_SIZE)) != -1) {
				downloaded += count;
				fout.write(data, 0, count);
				int percent = (int) ((downloaded * 100) / fileLength);
				int inKB = (int) (downloaded / 1024);
				int totalInKB = (int) (fileLength / 1024);
				if (percent % 10 == 0) {
					Log.log("  Downloading update: " + percent + "% downloaded.  (" + inKB + " KB / " + totalInKB +" KB Downloaded)");
				}
			}

			Log.log(" ----------------------------- Update Successful ----------------------------- ");
			Log.log(" *Please restart the server to use " + this.versionName);
		} catch (Exception e) {
			Log.log("Update was found but failed to download the update");
			Log.log(e);
			this.result = Update.UpdateResult.FAIL_DOWNLOAD;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	private boolean versionCheck(String title) {
		if (this.type != UpdateType.NO_VERSION_CHECK) {
			String localVersion = this.plugin.getDescription().getVersion();

			if (title.split(" ").length == 2) {
				String[] sp = title.split(" ");

				String remoteVersion = sp[1];
				if (remoteVersion.startsWith("v")) {
					remoteVersion = remoteVersion.substring(1);
				}

				if (this.hasTag(localVersion) || !this.shouldUpdate(localVersion, remoteVersion)) {
					this.result = Update.UpdateResult.NO_UPDATE;
					return false;
				}
			} else {
				Log.log("An error has occurred while checking for remote version");
				this.result = Update.UpdateResult.FAIL_NOVERSION;
				return false;
			}
		}
		return true;
	}

	public boolean shouldUpdate(String localVersion, String remoteVersion) {
		return !localVersion.equalsIgnoreCase(remoteVersion);
	}

	private boolean hasTag(String version) {
		for (String string : Update.NO_UPDATE_TAG) {
			if (version.contains(string)) {
				return true;
			}
		}
		return false;
	}

	private boolean read() {
		try {
			final URLConnection conn = this.url.openConnection();
			conn.setConnectTimeout(5000);

			conn.setDoOutput(true);

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String response = reader.readLine();

			JSONArray array = (JSONArray) JSONValue.parse(response);

			this.versionName = (String) ((JSONObject) array.get(array.size() - 1)).get(Update.TITLE_VALUE);
			this.versionLink = (String) ((JSONObject) array.get(array.size() - 1)).get(Update.LINK_VALUE);

			return true;
		} catch (IOException e) {
			this.plugin.getLogger().severe("Failed to contact dev.bukkit.org for updating.");
			Log.log(e);
			return false;
		}
	}

	private class UpdateRunnable implements Runnable {

		@Override
		public void run() {
			if (Update.this.url != null) {
				if (Update.this.read()) {
					if (Update.this.versionCheck(Update.this.versionName)) {
						if ((Update.this.versionLink != null) && (Update.this.type != UpdateType.NO_DOWNLOAD)) {
							String name = Update.this.file.getName();
							if (Update.this.versionLink.endsWith(".zip")) {
								final String[] split = Update.this.versionLink.split("/");
								name = split[split.length - 1];
							}
							Update.this.saveFile(new File(Update.this.plugin.getDataFolder().getParent(), Update.this.updateFolder), name, Update.this.versionLink);
						} else {
							Update.this.result = UpdateResult.UPDATE_AVAILABLE;
						}
					}
				}
			}
		}
	}
}