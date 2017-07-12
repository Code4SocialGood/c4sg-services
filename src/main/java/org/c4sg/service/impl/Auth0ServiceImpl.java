package org.c4sg.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.c4sg.service.Auth0Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

//import net.minidev.json.JSONObject;

@Service
public class Auth0ServiceImpl implements Auth0Service {

	@Override
	public String getAuth0ApiToken() throws Exception {
		return "token";
	}
	
	@Override
	public String getAuth0UserId(String email) throws Exception {
		
		String api = "users?q=";
		String query = "email:\"mytestacct70@gmail.com\"";
		URL url = getRequestUrl(api, query);
		HttpResponse<JsonNode> response = Unirest.get(url.toString())
				  .header("content-type", "application/json")
				  .header("authorization", "Bearer token")
				  .asJson();
		JsonNode responseBody = response.getBody();
		JSONArray responseArray = responseBody.getArray();
		JSONObject responseObject = (JSONObject) responseArray.get(0);
		//JSONArray identitiesArray = (JSONArray) responseObject.get("identities");
		//JSONObject identiryObject = (JSONObject) identitiesArray.get(0);
		String userid = (String) responseObject.get("user_id");		
		
		return userid;
	}
	
	@Override
	public void deleteAuth0User(String email) throws Exception {
		
		String api = "users/";
		String query = getAuth0UserId(email);
		URL url = getRequestUrl(api, query);
		HttpResponse<JsonNode> response = Unirest.delete(url.toString())
				  .header("content-type", "application/json")
				  .header("authorization", "Bearer token")
				  .asJson();
		JsonNode responseBody = response.getBody();
		/*JSONArray responseArray = responseBody.getArray();
		JSONObject responseObject = (JSONObject) responseArray.get(0);
		JSONArray identitiesArray = (JSONArray) responseObject.get("identities");
		JSONObject identiryObject = (JSONObject) identitiesArray.get(0);
		String userid = (String) identiryObject.get("user_id");		
		
		return userid;*/
	}
	
	private URL getRequestUrl(String api, String query) throws MalformedURLException, UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder();		
		urlBuilder.append("https://c4sg-dev.auth0.com/api/v2/");
		urlBuilder.append(api);
		urlBuilder.append(URLEncoder.encode(query, "UTF-8"));		
		return new URL(urlBuilder.toString());
	}
}