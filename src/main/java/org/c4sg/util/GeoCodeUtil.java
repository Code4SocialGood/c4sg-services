package org.c4sg.util;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.c4sg.dto.UserDTO;

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
	
	String state;
	String country;
	
	public GeoCodeUtil(UserDTO userDTO) {
		this.state = userDTO.getState();
		this.country = userDTO.getCountry();
	}
	
	public Map<String,BigDecimal> getGeoCode() throws Exception {
		
		Map<String,BigDecimal> geocode = new HashMap<String,BigDecimal>();
		String address = getAddress();
		if(address.isEmpty()) {
			return geocode;
		}
		
		try {
			URL url = getRequestUrl(address);
			String response = getResponse(url);
			if(response != null) {
				Object obj = JSONValue.parse(response);
				
				if(obj instanceof JSONObject) {
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
			} else {
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
	
	private URL getRequestUrl(String url) throws MalformedURLException, UnsupportedEncodingException {
		StringBuilder query = new StringBuilder();		
		query.append("https://maps.googleapis.com/maps/api/geocode/json?address=");
		query.append(URLEncoder.encode(url, "UTF-8"));
		query.append("&key=AIzaSyBViSnTCKnFTEc7l3hc02TxnmQXXr0IRh0");
		
		return new URL(query.toString());
	}
	
	private String getAddress() {
		if((this.state != null && !this.state.isEmpty()) && (this.country != null && !this.country.isEmpty())) {
			StringBuilder address = new StringBuilder();
			address.append(this.state);
			address.append(",");
			address.append(this.country);
			return address.toString();
		}
		return "";
		
	}
	
	public static void main(String[] args) throws Exception {
		UserDTO u = new UserDTO();
		u.setState("Lagos");
		u.setCountry("Nigeria");
		
		GeoCodeUtil gu = new GeoCodeUtil(u);
		Map<String, BigDecimal> geoCode = gu.getGeoCode();
		System.out.println(geoCode);
		
		u = new UserDTO();
		gu = new GeoCodeUtil(u);
		geoCode = gu.getGeoCode();
		System.out.println(geoCode);
	}
	
}
