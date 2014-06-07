package com.wenote.rest.controller;

import java.util.ArrayList;
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
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.wenote.rest.Constant;
import com.wenote.rest.Util;
import com.wenote.rest.domain.Note;
import com.wenote.rest.domain.User;

@Controller
public class NoteQueryController {

	@RequestMapping("/wenote/notes")
    public ResponseEntity<List<Note>> getNotes(
    		@RequestParam(value="user_name", required=true) String user_name,
    		@RequestParam(value="url", required=false, defaultValue="/") String url) {
		
		System.out.println("noteQuery");
		System.out.println("\tuser_name:"+user_name);
		System.out.println("\turl:"+url);
		
		AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(new BasicAWSCredentials(Constant.accessKey, Constant.secretKey));
		ddb.setEndpoint("dynamodb.us-west-2.amazonaws.com");

		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        
		Condition urlCondition = null;
		if (url.equals("/")) {
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
			scanExpression.addFilterCondition("userName", 
					new Condition()
						.withComparisonOperator("EQ")
						.withAttributeValueList(new AttributeValue().withS(user_name)));
			
			scanExpression.addFilterCondition("url", 
					new Condition()
						.withComparisonOperator("NOT_NULL"));
			

	        
			return new ResponseEntity<List<Note>>(mapper.scan(Note.class, scanExpression), headers, HttpStatus.OK);
			
		} else {
			ArrayList<Note> result = new ArrayList<Note>();
			
			//get notes of itself
			urlCondition = new Condition()
				.withComparisonOperator("EQ")
				.withAttributeValueList(new AttributeValue().withS(url));
			
			Note hashKey = new Note();
			hashKey.setUserName(user_name);
			
	        DynamoDBQueryExpression<Note> queryExpression = new DynamoDBQueryExpression<Note>()
	        		.withIndexName("url-index")
	        		.withHashKeyValues(hashKey)
	        		.withRangeKeyCondition("url", urlCondition);
	        
	        result.addAll(mapper.query(Note.class, queryExpression));
	        
	        User user = mapper.load(User.class, user_name);
	        if(user == null || Util.getFriendList(user).isEmpty())
	        	return new ResponseEntity<List<Note>>(result, headers, HttpStatus.OK);

	        for(String s : Util.getFriendList(user)) {
	        	hashKey.setUserName(s);
	        	queryExpression = new DynamoDBQueryExpression<Note>()
		        		.withIndexName("url-index")
		        		.withHashKeyValues(hashKey)
		        		.withRangeKeyCondition("url", urlCondition);
	        	
		        result.addAll(mapper.query(Note.class, queryExpression));
	        }
	        
        	return new ResponseEntity<List<Note>>(result, headers, HttpStatus.OK);
		}
    }
}
