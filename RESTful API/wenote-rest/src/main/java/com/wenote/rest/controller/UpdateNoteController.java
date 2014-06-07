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
public class UpdateNoteController {
	
	@RequestMapping("/wenote/update")
    public ResponseEntity<Boolean> postNote(
    		@RequestParam(value="user_name", required=true) String user_name,
    		@RequestParam(value="id", required=true) String id,
    		@RequestParam(value="title", required=true) String title,
    		@RequestParam(value="content", required=true) String content) {
		
		System.out.println("updateNote");
		System.out.println("\tuser_name:"+user_name);
		System.out.println("\tid:"+id);
		System.out.println("\ttitle:"+title);
		System.out.println("\tcontent:"+content);
		
		AmazonDynamoDBClient ddb = new AmazonDynamoDBClient(new BasicAWSCredentials(Constant.accessKey, Constant.secretKey));
		ddb.setEndpoint("dynamodb.us-west-2.amazonaws.com");

		DynamoDBMapper mapper = new DynamoDBMapper(ddb);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
		
		Note existingNote = mapper.load(Note.class, user_name, id);
		if (existingNote == null) {
			return new ResponseEntity<Boolean>(false, headers, HttpStatus.OK);
		}
		
		existingNote.setTitle(title);
		existingNote.setContent(content);
		existingNote.updateChangeDate();

		mapper.save(existingNote);
		
		return new ResponseEntity<Boolean>(true, headers, HttpStatus.OK);
	}
}
