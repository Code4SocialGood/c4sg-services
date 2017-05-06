package org.c4sg.util;

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
import org.c4sg.dto.UserDTO;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class GeoCodeUtil {
	
	String state;
	String country;
	
	public GeoCodeUtil(UserDTO userDTO)
	{
		this.state = userDTO.getState();
		this.country = userDTO.getCountry();
	}
	
	public Map<String,BigDecimal> getGeoCode() throws Exception 
	{
		Map<String,BigDecimal> geocode = new HashMap<String,BigDecimal>();
		try{
			URL url = getRequestUrl();
			String response = getResponse(url);
			if(response != null)
			{
				Object obj = JSONValue.parse(response);		        

		        if (obj instanceof JSONArray) {
		            JSONArray array = (JSONArray) obj;
		            if (array.size() > 0) {
		                JSONObject jsonObject = (JSONObject) array.get(0);

		                String lon = (String) jsonObject.get("lon");
		                String lat = (String) jsonObject.get("lat");		                
		                geocode.put("lon", new BigDecimal(lon));
		                geocode.put("lat", new BigDecimal(lat));
		                
		            }
		        }
		        return geocode;
			}
			else{
				throw new Exception("Fail to convert to geocode");
			}
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
			
		}		
	}
	private String getResponse(URL url) throws Exception 
	{
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();		
		httpConnection.setRequestMethod("GET");
		
		if(httpConnection.getResponseCode() == 200)
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        return response.toString();
		}
		else
		{
			throw new Exception("Fail to convert to geocode"); 
		}
	}
	
	private URL getRequestUrl() throws MalformedURLException, UnsupportedEncodingException
	{
		StringBuffer query = new StringBuffer();
		query.append("http://nominatim.openstreetmap.org/search?q=");
		query.append(URLEncoder.encode(getAddress(), "UTF-8"));
		query.append("&format=json&addressdetails=1");			
		
		URL url = new URL(query.toString());
		
		return url;
	}
	
	private String getAddress() {
		StringBuffer address = new StringBuffer();

		if(this.state != null && !this.state.isEmpty())
			address.append(" ");
				
		if(this.country != null && !this.country.isEmpty())
			address.append(this.country);
				
		return address.toString();
	}

}
