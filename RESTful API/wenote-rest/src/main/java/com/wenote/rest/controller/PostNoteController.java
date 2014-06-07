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
import com.wenote.rest.domain.Note;

@Controller
public class PostNoteController {
	
	@RequestMapping("/wenote/post")
    public ResponseEntity<Boolean> postNote(
    		@RequestParam(value="user_name", required=true) String user_name,
    		@RequestParam(value="url", required=true) String url,
    		@RequestParam(value="title", required=true) String title,
    		@RequestParam(value="content", required=true) String content) {
		
		System.out.println("postNote");
		System.out.println("\tuser_name:"+user_name);
		System.out.println("\turl:"+url);
		System.out.println("\ttitle:"+title);
		System.out.println("\tcontent:"+content);
		
		Note newNote = new Note();
		newNote.allocateId();
		newNote.setUrl(url);
		newNote.setUserName(user_name);
		newNote.setTitle(title);
		newNote.setContent(content);
		newNote.updateChangeDate();
		
		AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(new BasicAWSCredentials(Constant.accessKey, Constant.secretKey));
		ddb.setEndpoint("dynamodb.us-west-2.amazonaws.com");

		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        
		mapper.save(newNote);
		
		return new ResponseEntity<Boolean>(true, headers, HttpStatus.CREATED);
	}
}
