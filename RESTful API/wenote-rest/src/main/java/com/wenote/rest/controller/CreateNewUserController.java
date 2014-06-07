package com.wenote.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.wenote.rest.Constant;
import com.wenote.rest.domain.User;

@Controller
public class CreateNewUserController {
	
	@RequestMapping("/wenote/newuser")
    public ResponseEntity<Boolean> postNote(
    		@RequestParam(value="user_name", required=true) String user_name,
    		@RequestParam(value="password", required=true) String password) {
						
		System.out.println("createUser");
		System.out.println("\tuser_name:"+user_name);
		System.out.println("\tpassword:"+password);
		
		AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(new BasicAWSCredentials(Constant.accessKey, Constant.secretKey));
		ddb.setEndpoint("dynamodb.us-west-2.amazonaws.com");

		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        
		User newUser = new User();
		newUser.setUserName(user_name);
		newUser.setPassword(password);
		newUser.setFriends("null");
		
		if (mapper.load(User.class, user_name) != null) {
			return new ResponseEntity<Boolean>(false, headers, HttpStatus.CREATED);
		}
		
		mapper.save(newUser);
		
		return new ResponseEntity<Boolean>(true, headers, HttpStatus.CREATED);
	}
}
