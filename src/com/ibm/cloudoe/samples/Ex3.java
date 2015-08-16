package com.ibm.cloudoe.samples;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/ex3")
public class Ex3 {

	/**
	 * Entry point for the AJAX call.
	 */
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public String getInformation(@FormParam("text") String text) {
		// Format:
		// a * x + b * y = u
		// c * x + d * y = v
		Pattern p = Pattern.compile("(-?\\d*)x\\+?(-?\\d*)y=(\\d+)");
		
		Matcher m;
		StringBuilder r = new StringBuilder();
		String lines[] = text.split("\\n");
		System.out.println(Arrays.toString(lines));
		float a,b,c,d,u,v;
		a = b = c = d = u = v = 0;
		int i=0;
		for(String line : lines) {
			if (line=="#") {
				continue;
			}
			if (line=="##") {
				break;
			}
			line = line.replaceAll("\\s", "");
			m = p.matcher(line);
			
			// First pass retrieves a, b, and u. Second retrieves c, d, and v.
			if (i % 2 == 0) {
				a = b = c = d = u = v = 0;
				if (m.find()) {
					a = getFloat(m.group(1));
					b = getFloat(m.group(2));
					u = getFloat(m.group(3));
				}
				else {
					continue;
				}
			}
			else {
				if (m.find()) {
					c = getFloat(m.group(1));
					d = getFloat(m.group(2));
					v = getFloat(m.group(3));
					
					Pair<Float, Float> result = solve(a,b,c,d,u,v);
					r.append("<p>x=" + Math.round(result.left) + " y=" + Math.round(result.right) + "</p>");
				}
				else {
					continue;
				}
				
			}
			i++;
		}
		return r.toString();
	}
	
	/**
	 * Solves a two variable, two equation system of equations.
	 */
	private Pair<Float, Float> solve(float a, float b, float c, float d, float u, float v) {
		if (Math.abs(a) > Math.abs(c)) {
	         float f = u * c / a;
	         float g = b * c / a;
	         float y = (v - f) / (d - g);
	         return new Pair<Float, Float>((f - g * y) / c, y);
		}
	    else {
	         float f = v * a / c;
	         float g = d * a / c;
	         float y = (u - f) / (b - g);
	         return new Pair<Float, Float>((f - g * y) / a, y);
	    }
	}
	
	private float getFloat(String s) {
		switch (s) {
			case "":
				return 1;
			case "-":
				return -1;
			default:
				return Float.parseFloat(s);
		}
	}
	
	
}


