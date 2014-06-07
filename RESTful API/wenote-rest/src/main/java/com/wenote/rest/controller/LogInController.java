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
public class LogInController {

	@RequestMapping(value="/wenote/login")
    public ResponseEntity<Boolean> login(
            @RequestParam(value="user_name", required=true) String name,
            @RequestParam(value="password", required=true) String password) {
		
		System.out.println("Login\n\tuser_name:"+name+", password:"+password);
		AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(new BasicAWSCredentials(Constant.accessKey, Constant.secretKey));
		ddb.setEndpoint("dynamodb.us-west-2.amazonaws.com");
		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        
		User user = mapper.load(User.class, name);
		if(user == null) {
			return new ResponseEntity<Boolean>(false, headers, HttpStatus.OK);
		}
		
		if(!user.getPassword().equals(password)) {
			return new ResponseEntity<Boolean>(false, headers, HttpStatus.OK);
		}
		        
        return new ResponseEntity<Boolean>(true, headers, HttpStatus.OK);
    }
}
