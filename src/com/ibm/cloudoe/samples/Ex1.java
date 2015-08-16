package com.ibm.cloudoe.samples;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/ex1")
public class Ex1 {
	
	static HashMap<Integer, BigInteger> factorials = new HashMap<>();

	/**
	 * Entry point of AJAX call.
	 */
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public String getInformation(@FormParam("text") String text) {
		String lines[] = text.split("\\n");
		System.out.println(Arrays.toString(lines));
		StringBuilder r = new StringBuilder();
		for(int i=0; i<lines.length-1; i++) {
			r.append("<p>" + factorial(Integer.parseInt(lines[i])) + "</p>");
		}
		return r.toString();
	}
	
	/**
	 * Checks the 'factorials' cache to avoid unnecessary calculations. 
	 * Returns a BigInteger in order to handle results of an arbitrary size.
	 */
	private static BigInteger factorial(int n) {
		if (n == 0) return BigInteger.ONE;
		if (factorials.containsKey(n)) return factorials.get(n);
		
		BigInteger result = factorial(n-1).multiply(new BigInteger(Integer.toString(n)));
		factorials.put(n,result);
		return result;
	}
}
