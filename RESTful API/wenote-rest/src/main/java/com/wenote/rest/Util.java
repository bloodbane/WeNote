package com.wenote.rest;

import java.util.ArrayList;

import com.wenote.rest.domain.User;

public class Util {
	static public ArrayList<String> getFriendList(User user) {
		ArrayList<String> ret = new ArrayList<String>();

		String friends = user.getFriends();
		if(friends.equals("null"))
			return ret;
		
		String[] friend = user.getFriends().split(",");
		
		for(String s : friend) {
			ret.add(s);
		}
		
		return ret;
	}
	
	static public String getFriendString(ArrayList<String> friends) {
		StringBuffer sb = new StringBuffer();
		for(String s : friends) {
			sb.append(s);
			sb.append(",");
		}
		
		sb.deleteCharAt(sb.lastIndexOf(","));
		
		return sb.toString();
	}
}
