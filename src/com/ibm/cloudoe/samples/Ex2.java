package com.ibm.cloudoe.samples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/ex2")
public class Ex2 {

	/**
	 * Entry point from AJAX call.
	 */
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public String getInformation(@FormParam("text") String text) {
		// Format: 100,50,20,10,5,2,1:57
		String lines[] = text.split("\\n");
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m;
		StringBuilder r = new StringBuilder();
		List<Integer> nums;
		int changeToCalculate;
		for (int i=0; i<lines.length-1;i++) {
			// Parse the input into lists of integers.
			nums = new ArrayList<>();
			m = p.matcher(lines[i]);
			while (m.find()) {
				 nums.add(Integer.parseInt(m.group()));
			}
			
			// Last number is the change value.
			changeToCalculate = nums.remove(nums.size()-1);
			
			// Create the output HTML.
			r.append("<p>");
			for (Pair<Integer, Integer> values : calculateChange(nums, changeToCalculate)) {
				if (values.right != 0) {
					r.append(values.left + "x" + values.right + ",");
				}
			}
			r.deleteCharAt(r.length()-1);
			r.append("</p>");
		}
		return r.toString();
	}
	
	/**
	 *  Returns a list of pairs where the left value is the coin value, and the right value is how many to use.
	 */
	private List<Pair<Integer, Integer>> calculateChange(List<Integer> coins, int changeToMake) {
		Collections.sort(coins, Collections.reverseOrder());
		List<Pair<Integer, Integer>> result = new ArrayList<>();
		for (int coin : coins) {
			result.add(new Pair<Integer, Integer>(coin, 0));
		}
		int i = 0;
		while (changeToMake > 0) {
			if (changeToMake - coins.get(i) >= 0) {
				changeToMake -= coins.get(i);
				result.get(i).right++;
			}
			else {
				i++;
			}
		}
		return result;
	}
}

