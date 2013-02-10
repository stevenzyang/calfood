package com.floor7.calfood;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashSet;
import java.util.Calendar;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.*;

public class ScrapeTest {
	public static void main(String[] args) {
		int days_in_advance = 16;
		int total_num_of_foods = 0;
		HashSet<String> Crossroads = new HashSet<String>();
		HashSet<String> Cafe_3 = new HashSet<String>();
		HashSet<String> Clark_Kerr = new HashSet<String>();
		HashSet<String> Foothill = new HashSet<String>();
		HashSet<String> allfoods = new HashSet<String>();
		ArrayList<Food> foods = new ArrayList<Food>();
		Calendar cal = Calendar.getInstance();
		Pattern foodpattern = Pattern.compile("openDescWin\\('','(.*?)'\\)");
		String URL1 = "http://services.housing.berkeley.edu/FoodPro/dining/static/diningmenus.asp?dtCurDate=";
		String URL2 = "&strCurLocation=";
		String date = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
		Date today = new Date(date);
		date = today.toString();
		String[] location = {"01", "03", "04", "06"};
		try {
			PrintWriter out = new PrintWriter(new FileWriter("foods.txt"));
			for (int i = 0; i < days_in_advance; i++) {
				for (int j = 0; j < location.length; j++) {
					String URL = URL1 + date + URL2 + location[j];
					Document doc = Jsoup.connect(URL).get();
					Element body = doc.body();
					Matcher m = foodpattern.matcher(body.html());
					while (m.find()) {
						total_num_of_foods += 1;
						String s = m.group(1);
						switch(j) {
							case 0:
								Crossroads.add(s);
								break;
							case 1:
								Cafe_3.add(s);
								break;
							case 2:
								Clark_Kerr.add(s);
								break;
							case 3:
								Foothill.add(s);
								break;
							default:
								break;
						}
					}
				}
				today = today.getTomorrow();
				date = today.toString();
			}
			for (String s : Crossroads) {
				out1.println(s);
			}
			for (String s : Cafe_3) {
				out2.println(s);
			}
			for (String s : Clark_Kerr) {
				out3.println(s);
			}
			for (String s : Foothill) {
				out4.println(s);
			}
			System.out.println("Found " + total_num_of_foods + " foods");
			out1.close();
			out2.close();
			out3.close();
			out4.close();
		} catch (IOException e) {
			System.err.println("Error");
		}
	}
}

class Food {
	public String name;
	public ArrayList<FoodCoordinate> appearances = new ArrayList<FoodCoordinate>();
	public int rating = 0;
	
	void addCoordinate(String l, Date d, String t) {
		appearances.add(new FoodCoordinate(l, d, t));
	}
	
//	@Override
//	public boolean equals(Object obj) {
//	}
}


class FoodCoordinate {
	private String location;
	private Date date;
	private String time;
	
	FoodCoordinate(String l, Date d, String t) {
		location = l;
		date = d;
		time = t;
	}
	
	String getLocation() {
		return location;
	}
	
	Date getDate() {
		return date;
	}
	

	String getTime() {
		return time;
	}

}
