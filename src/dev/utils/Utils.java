package dev.utils;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class Utils {
	
	public static String loadFile(String path) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public static int parseInt(String n) {
		try {
			return Integer.parseInt(n);
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void delay(long millis) {
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis()-start < millis) {}
	}
	
	
	public static Optional<Point> calcIntersectionPoint(){
		return null;
	}
}
