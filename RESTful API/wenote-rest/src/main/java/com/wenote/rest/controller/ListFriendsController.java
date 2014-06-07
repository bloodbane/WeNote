package com.wenote.rest.controller;

import java.util.List;

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
public class ListFriendsController {

	@RequestMapping("/wenote/listfriend")
    public ResponseEntity<List<String>> getNotes(
    		@RequestParam(value="user_name", required=true) String user_name) {
		
		System.out.println("list friend");
		System.out.println("\tuser_name:"+user_name);
		
		AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(new BasicAWSCredentials(Constant.accessKey, Constant.secretKey));
		ddb.setEndpoint("dynamodb.us-west-2.amazonaws.com");

		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        
        User user = mapper.load(User.class, user_name);

		return new ResponseEntity<List<String>>(Util.getFriendList(user), headers, HttpStatus.OK);

    }
}
