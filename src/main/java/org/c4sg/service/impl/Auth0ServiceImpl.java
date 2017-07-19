package org.c4sg.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;

import org.c4sg.exception.BadRequestException;
import org.c4sg.exception.NotFoundException;
import org.c4sg.service.Auth0Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Service
public class Auth0ServiceImpl implements Auth0Service {

	String access_token = "";
	Date tokenCreated = null;
	double expires_in = 0;
	String scope = "";
	
	@Value("${auth0_domain}")
	private String auth0Domain;
	
	@Value("${auth0_client_id}")
	private String auth0ClientId;
	
	@Value("${auth0_client_secret}")
	private String auth0ClientSecret;
	
	private String auth0ApiUrl = "";
	private String auth0TokenUrl = "";
	
	
	@Override
	public void deleteAuth0User(String email) throws Exception {	
		
		auth0ApiUrl = auth0Domain + "/api/v2/";
		auth0TokenUrl = auth0Domain + "/oauth/token";
		
		if(access_token.isEmpty()){
			getAuth0ApiToken();					
		}		
		String api = "users/";
		String query = getAuth0UserId(email);
		URL url = getRequestUrl(api, query);
		
		HttpResponse<JsonNode> response = Unirest.delete(url.toString())
				  .header("content-type", "application/json")
				  .header("authorization", "Bearer " + access_token)
				  .asJson();		
		if(response == null){
			throw new BadRequestException("Could not delete the user");
		} else if(response.getStatus() != 204){		
			JsonNode responseBody = response.getBody();	
			JSONArray responseArray = responseBody.getArray();
			JSONObject responseObject = (JSONObject) responseArray.get(0);
				
			throw new BadRequestException("Error:" + (String) responseObject.get("error") 										
										+ ", status:" + response.getStatus() );
		}
	}
		
	@Override
	public String getAuth0ApiToken() throws Exception {
		
		tokenCreated = new Date();
		HttpResponse<JsonNode> response = Unirest.post(auth0TokenUrl)
				  .header("content-type", "application/json")
				  .body("{\"grant_type\":\"client_credentials\",\"client_id\": \""+ auth0ClientId +"\",\"client_secret\": \""+ auth0ClientSecret +"\",\"audience\": \""+ auth0ApiUrl +"\"}")
				  .asJson();
		
		if(response == null){
			throw new BadRequestException("Could not get access token");
		} else {
			JsonNode responseBody = response.getBody();			
			JSONObject responseObject = responseBody.getObject();
			if(responseObject != null && response.getStatus() == 200){				
					access_token = responseObject.getString("access_token");
					expires_in = responseObject.getDouble("expires_in");
					scope = responseObject.getString("scope");
				
			} else {
				throw new BadRequestException("Error:" + (String) responseObject.get("error") 				 
											+ ", status:" + response.getStatus());
			}			
		}		
		return access_token;
	}
	
	@Override
	public String getAuth0UserId(String email) throws Exception {
		
		String userid = "0";
		String api = "users?q=";
		String query = "email:\""+ email +"\"";
		URL url = getRequestUrl(api, query);
		HttpResponse<JsonNode> response = Unirest.get(url.toString())
				  .header("content-type", "application/json")
				  .header("authorization", "Bearer " + access_token)
				  .asJson();
		if(response == null){
			throw new BadRequestException("Could not find the user");
		} else {
			JsonNode responseBody = response.getBody();
			JSONArray responseArray = responseBody.getArray();
			if(responseArray.length() > 0){
				
				JSONObject responseObject = (JSONObject) responseArray.get(0);
				if(response.getStatus() == 200){
					userid = (String) responseObject.get("user_id");
				}
				else {
					throw new BadRequestException("Error:" + (String) responseObject.get("error") 				
					+ ", status:" + response.getStatus());
				}
			} else {
				throw new BadRequestException("Could not find the user");
			}							
		}		
		return userid;
	}
				
	private URL getRequestUrl(String api, String query) throws MalformedURLException, UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder();		
		urlBuilder.append(auth0ApiUrl);
		urlBuilder.append(api);
		urlBuilder.append(URLEncoder.encode(query, "UTF-8"));		
		return new URL(urlBuilder.toString());
	}	
	
}
