package com.floor7.calfood;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Calendar;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class ScrapeTest {
	
	public static final String XR = "01";
	public static final String C3 = "03";
	public static final String CK = "04";
	public static final String FH = "06";
	public static final String[] diningHalls = {XR, C3, FH, CK};
	
	public static void main(String[] args) throws IOException{
		ArrayList<Food> foods = getFoods(C3);
		for (Food food : foods){
			System.out.println(food);
		} 
		//updateFoods();
	}
	
	public static void updateFoods() {
		int testoverlap = 0;
		int days_in_advance = 16;
		int total_num_of_foods = 0;
		//boolean isBreakfast = false;
		//boolean isLunch = false;
		//boolean isDinner = false;
		Hashtable<String, Food> foodtable = new Hashtable<String, Food>();
		//Pattern breakfast = Pattern.compile("");
		Calendar cal = Calendar.getInstance();
		String date = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
		Date today = new Date(date);
		date = today.toString();
		try {
			PrintWriter out = new PrintWriter(new FileWriter("foods.txt"));
			for (int i = 0; i < days_in_advance; i++) {
				for (int j = 0; j < diningHalls.length; j++) {
					String URL = getURL(diningHalls[j], date);
					Matcher m = matchPattern(("openDescWin\\('','(.*?)'\\)"), URL);
					//isBreakfast = true;
					while (m.find()) {
						total_num_of_foods += 1;
						String s = m.group(1);
						if (foodtable.containsKey(s)) {
							testoverlap++;
							foodtable.get(s).addCoordinate(diningHalls[j], today, "All Day");
						} else {
							Food temp = new Food(s);
							temp.addCoordinate(diningHalls[j], today, "All Day");
							foodtable.put(s, temp);
						}
					}
				}
				today = today.getTomorrow();
				date = today.toString();
			}
			for (Food f : foodtable.values()) {
				out.println(f);
				for (FoodCoordinate fc : f.appearances) {
					if (fc.getLocation().equals("Cafe 3")) {
						out.println("\t" + fc.getLocation() + "\t\t" + fc.getDate() + "\t" + fc.getTime());
					} else {
						out.println("\t" + fc.getLocation() + "\t" + fc.getDate() + "\t" + fc.getTime());
					}
				}
				out.println();
			}
			System.out.println("Found " + total_num_of_foods + " foods");
			System.out.println("Had " + testoverlap + " overlaps.");
			out.close();
		} catch (IOException e) {
			System.err.println("Error");
		}
	}
	
	/**
	 * @param loc the dining hall. Valid inputs are
	 * 			ScrapeTest.XR, ScrapeTest.C3, ScrapeTest.CK, and ScrapeTest.FH
	 * @return the list of foods from the location
	 */
	public static ArrayList<Food> getFoods(String loc) throws IOException {
		Calendar cal = Calendar.getInstance();
		Date date = new Date((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		return getFoods(loc, date);
	}

	public static ArrayList<Food> getFoods(String loc, Date date) throws IOException {
		String d = date.toString();
		String URL = getURL(loc, d);
		Matcher m = matchPattern("openDescWin\\('','(.*?)'\\)", URL);
		ArrayList<Food> foods = new ArrayList<Food>(15);
		while (m.find()) {
			foods.add(new Food(m.group(1)));
		}
		return foods;
	}
	
	private static Matcher matchPattern(String regex, String URL) throws IOException {
		Pattern foodpattern = Pattern.compile(regex);
		Document doc = Jsoup.connect(URL).get();
		Element body = doc.body();
		Matcher m = foodpattern.matcher(body.html());
		return m;
	}
	
	
	// returns URL of location at date
	public static String getURL(String location, String date){
		String URL1 = "http://services.housing.berkeley.edu/FoodPro/dining/static/diningmenus.asp?dtCurDate=";
		String URL2 = "&strCurLocation=";
		Calendar cal = Calendar.getInstance();
		String URL = URL1 + date + URL2 + location;
		return URL;
	}
	
	public static String diningCodeToName(String code){
		if (code.equals(XR)){
			return "Crossroads";
		} else if (code.equals(C3)){
			return "Cafe 3";
		} else if (code.equals(FH)){
			return "Foothill";
		} else if (code.equals(CK)){
			return "Clark Kerr";
		}
		return "Bad Code";
	}
}

class Food {
	public String name;
	public ArrayList<FoodCoordinate> appearances = new ArrayList<FoodCoordinate>();
	public int rating = 0;

	public Food(String n) {
		name = n;
		rating = 0;
	}

	void addCoordinate(String l, Date d, String t) {
		appearances.add(new FoodCoordinate(l, d, t));
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		if (this.toString().equals(((Food) obj).toString())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}


class FoodCoordinate {
	private String location;
	private Date date;
	private String time;

	FoodCoordinate(String l, Date d, String t) {
		location = ScrapeTest.diningCodeToName(l);
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
