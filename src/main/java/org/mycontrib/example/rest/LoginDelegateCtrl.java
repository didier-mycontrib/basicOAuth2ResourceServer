package org.mycontrib.example.rest;

import java.nio.charset.Charset;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="/myapi/auth" , 
                headers="Accept=application/json")
public class LoginDelegateCtrl {
	
	private static Logger logger = LoggerFactory.getLogger(LoginDelegateCtrl.class);
	
	private static final String ACCESS_TOKEN_URL = "http://localhost:8081/basic-oauth-server/oauth/token";
	
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	
	HttpHeaders createBasicHttpAuthHeaders(String username, String password){
		/*
		   return new HttpHeaders() {{
		         String auth = username + ":" + password;
		         byte[] encodedAuth = Base64.getEncoder().encode(
		            auth.getBytes(Charset.forName("US-ASCII")) );
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		      }};
		*/
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(
           auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
		headers.add("Authorization", authHeader);
		return headers;
		}
	
	@PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest loginRequest) {    	
    	logger.debug("/login , loginRequest:"+loginRequest);
    	String authResponse="{}";
    	try{
    		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
    		params.add("username", loginRequest.getUsername());
    		params.add("password", loginRequest.getPassword());
    		params.add("grant_type", "password");
    		HttpHeaders headers = createBasicHttpAuthHeaders("fooClientIdPassword","secret");
    		HttpEntity<MultiValueMap<String, String>> entityReq = new HttpEntity<MultiValueMap<String, String>>(params, headers);
    		
    		//ResponseEntity<String> tokenResponse = restTemplate.postForEntity(ACCESS_TOKEN_URL,params, String.class);
    		ResponseEntity<String> tokenResponse= restTemplate.exchange(ACCESS_TOKEN_URL, 
    				                                                    HttpMethod.POST, 
    				                                                    entityReq, 
    				                                                    String.class);
    		authResponse=tokenResponse.getBody();
	        logger.debug("/login authResponse:" + authResponse.toString());
	        return ResponseEntity.ok(authResponse);
		} catch (Exception e) {
			logger.debug("echec authentification:" + e.getMessage()); //for log
		     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		    		              .body(authResponse);
		    		            
		}
        
    }
}
