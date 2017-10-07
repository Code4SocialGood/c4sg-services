package org.c4sg.service.impl;

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

import org.c4sg.service.GeocodeService;
import org.c4sg.util.CountryCodeConverterUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

@Service
public class GoogleGeocodeService implements GeocodeService {
	
	@Value("${google_map_api_key}")
	private String googleApiKey;

	@Override
	public Map<String, BigDecimal> getGeoCode(String state, String country) throws Exception {
		
		Map<String,BigDecimal> geocode = new HashMap<String,BigDecimal>();
		
		// validate input
		if(country != null && !country.isEmpty()) {
			StringBuilder address = new StringBuilder();
			if (state != null && !state.isEmpty()) {
				address.append(state);
				address.append(",");
			}
			String countryCode = CountryCodeConverterUtil.convertToIso2(country);
			address.append(countryCode);
			
			try {
				URL url = getRequestUrl(address.toString());
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
		
		return geocode;
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
		query.append("&key=" + googleApiKey);
		
		return new URL(query.toString());
	}

}
