package org.c4sg.util;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class GeoCodeUtil {
	
	private String state;
	private String country;
	
	public GeoCodeUtil(String state, String country) {
		this.state = state;
		this.country = country;
	}
	
	public Map<String,BigDecimal> getGeoCode() throws Exception {
		Map<String,BigDecimal> geocode = new HashMap<String,BigDecimal>();
		try {
			URL url = getRequestUrl();
			String response = getResponse(url);
			if(response != null) {
				Object obj = JSONValue.parse(response);
				
				if(obj instanceof JSONObject)
				{
					JSONObject jsonObject = (JSONObject) obj;
					JSONArray array = (JSONArray) jsonObject.get("results");					
					JSONObject element = (JSONObject) array.get(0);
					JSONObject geometry = (JSONObject) element.get("geometry");
					JSONObject location = (JSONObject) geometry.get("location");
					Double lng = (Double) location.get("lng");
					Double lat = (Double) location.get("lat");
					geocode.put("lng", new BigDecimal(lng));
					geocode.put("lat", new BigDecimal(lat));
				}
				return geocode;				
			}
			else {
				throw new Exception("Fail to convert to geocode");
			}
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
			
		}
	}
	private String getResponse(URL url) throws Exception {
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("GET");
		
		if (httpConnection.getResponseCode() == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		}
		else {
			throw new Exception("Fail to convert to geocode");
		}
	}
	
	private URL getRequestUrl() throws MalformedURLException, UnsupportedEncodingException {
		StringBuilder query = new StringBuilder();		
		query.append("https://maps.googleapis.com/maps/api/geocode/json?address=");
		query.append(URLEncoder.encode(getAddress(), "UTF-8"));
		query.append("&key=AIzaSyBViSnTCKnFTEc7l3hc02TxnmQXXr0IRh0");
		
		return new URL(query.toString());
	}
	
	private String getAddress() {
		StringBuilder address = new StringBuilder();
		
		if (this.state != null && !this.state.isEmpty()) {
			address.append(this.state);
			address.append(",");
		}
		if (this.country != null && !this.country.isEmpty()) {
			address.append(this.country);
		}
		return address.toString();
	}
	
}
