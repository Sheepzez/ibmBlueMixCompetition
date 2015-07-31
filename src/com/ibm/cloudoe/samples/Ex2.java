package com.ibm.cloudoe.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/ex2")
public class Ex2 {

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public String getInformation(@FormParam("text") String text) {
		long startTime = System.currentTimeMillis();
		//100,50,20,10,5,2,1:57
		String lines[] = text.split("\\n");
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m;
		String line;
		StringBuilder r = new StringBuilder();
		List<Integer> nums;
		int changeToCalculate;
		for (int i=0; i<lines.length-1;i++) {
			nums = new ArrayList<>();
			line = lines[i];
			m = p.matcher(line);
			while (m.find()) {
				 nums.add(Integer.parseInt(m.group()));
			}
			changeToCalculate = nums.remove(nums.size()-1);
			r.append("<p>Coins available: " + nums.toString() +" | Change to calc: " + changeToCalculate + "</p>");
		}
		long endTime = System.currentTimeMillis();
	    r.append("<h4>Total execution time: " + (endTime-startTime) + "ms</h4>"); 
		return r.toString();
	}
}

