package com.wenote.rest.controller;

import java.util.ArrayList;

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
import com.wenote.rest.Util;
import com.wenote.rest.domain.User;

@Controller
public class AddFriendController {
	
	@RequestMapping("/wenote/addfriend")
    public ResponseEntity<Boolean> postNote(
    		@RequestParam(value="user_name", required=true) String user_name,
    		@RequestParam(value="friend", required=true) String friend) {
						
		System.out.println("add friend");
		System.out.println("\tuser_name:"+user_name);
		System.out.println("\tfriend:"+friend);
		
		AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(new BasicAWSCredentials(Constant.accessKey, Constant.secretKey));
		ddb.setEndpoint("dynamodb.us-west-2.amazonaws.com");

		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        
        //User not found
        User user = mapper.load(User.class, user_name);
		if (user == null) {
			return new ResponseEntity<Boolean>(false, headers, HttpStatus.NOT_MODIFIED);
		}
		
		//friend not found
		if(mapper.load(User.class, friend) == null) {
			return new ResponseEntity<Boolean>(false, headers, HttpStatus.NOT_MODIFIED);
		}
		
		ArrayList<String> friends = Util.getFriendList(user);
		if(friends.contains(friend)){
			return new ResponseEntity<Boolean>(true, headers, HttpStatus.OK);
		} 
		
		if(!friends.isEmpty()) {
			user.setFriends(user.getFriends()+","+friend);
		} else {
			user.setFriends(friend);
		}
		
		mapper.save(user);
		
		return new ResponseEntity<Boolean>(true, headers, HttpStatus.OK);
	}
}
