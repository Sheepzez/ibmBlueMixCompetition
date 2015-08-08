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

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public String getInformation(@FormParam("text") String text) {
		//long startTime = System.currentTimeMillis();
		
		// Format:
		// a * x + b * y = u
		// c * x + d * y = v
		Pattern p = Pattern.compile("(-?\\d*)x\\+?(-?\\d*)y=(\\d+)");
		
		Matcher m;
		StringBuilder r = new StringBuilder();
		String lines[] = text.split("\\n");
		System.out.println(Arrays.toString(lines));
		float a,b,c,d,u,v;
		String ma, mb, mc, md, mu, mv;
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
			if (i % 2 == 0) {
				a = b = c = d = u = v = 0;
				if (m.find()) {
					ma = m.group(1);
					mb = m.group(2);
					mu = m.group(3);

					a = getFloat(ma);
					b = getFloat(mb);
					u = getFloat(mu);
				}
				else {
					continue;
				}
			}
			else {
				if (m.find()) {
					mc = m.group(1);
					md = m.group(2);
					mv = m.group(3);

					c = getFloat(mc);
					d = getFloat(md);
					v = getFloat(mv);
					
					System.out.println("Solving: (" + a +")x + ("+b+")y = "+u +" and (" + c +")x + ("+d+")y = "+v);
					Pair<Float, Float> result = solve(a,b,c,d,u,v);
					r.append("<p>x=" + Math.round(result.left) + " y=" + Math.round(result.right) + "</p>");
				}
				else {
					continue;
				}
				
			}
			i++;
		}
		
		//long endTime = System.currentTimeMillis();
	    //System.out.println("Total execution time: " + (endTime-startTime) + "ms"); 
		return r.toString();
	}
	
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


