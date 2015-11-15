package com.minecraftmarket.minecraftmarket.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.minecraftmarket.minecraftmarket.json.JSONArray;
import com.minecraftmarket.minecraftmarket.json.JSONException;

public class Json {

	public static String getJSON(String url) throws JSONException {
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setRequestProperty("Accept", "application/json");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(10000);
			c.setReadTimeout(10000);
			c.connect();
			int status = c.getResponseCode();
			switch (status) {
			case 200:
			case 201:
				Scanner s = new Scanner(c.getInputStream());
				s.useDelimiter("\\Z");
				return s.next();
			}

		} catch (IOException ex) {
		}
		return "";
	}

	public String getJsonString(JSONArray jsonresult, int i, String args0) throws JSONException {
		return jsonresult.getJSONObject(1).getString(args0);
	}
	
	public static boolean isJson(String results) {
		if (results.startsWith("{") && results.endsWith("}")) {
			return true;
		}
		return false;
	}

}
