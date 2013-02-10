package com.floor7.calfood;

class Date {

	/* Put your private data fields here. */
	private int month = 1;
	private int day = 1;
	private int year = 1;
	/**
	 * Constructs a date with the given month, day and year. If the date is not
	 * valid, the entire program will halt with an error message.
	 * 
	 * @param month
	 *            is a month, numbered in the range 1...12.
	 * @param day
	 *            is between 1 and the number of days in the given month.
	 * @param year
	 *            is the year in question, with no digits omitted.
	 */
	public Date(int month, int day, int year) {
		try{
			if (!isValidDate(month, day, year)){
				throwError();
			}
			this.month = month;
			this.day = day;
			this.year = year;
		}
		catch (Exception e){
			throwError();
		}
	}

	/**
	 * Constructs a Date object corresponding to the given string.
	 * 
	 * @param s
	 *            should be a string of the form "month/day/year" where month
	 *            must be one or two digits, day must be one or two digits, and
	 *            year must be between 1 and 4 digits. If s does not match these
	 *            requirements or is not a valid date, the program halts with an
	 *            error message.
	 */
	public Date(String s) {
		try{
			String[] parts = s.split("/");
			if (parts[0].length() > 2 || parts[1].length() > 2 || parts[2].length() > 4 
					|| parts[0] == null || parts[1] == null || parts[2] == null){
				throwError();
			}
			if (!isValidDate(month, day, year)){
				throwError();
			}
			month = Integer.parseInt(parts[0]);
			day = Integer.parseInt(parts[1]);
			year = Integer.parseInt(parts[2]);
		}
		catch(Exception e){
			throwError();
		}
		
	}
	private static void throwError(){
		System.out.println("Dawg, are you fo real? Dat ain't no date.");
		System.exit(0);
	}
	/**
	 * Checks whether the given year is a leap year.
	 * 
	 * @return true if and only if the input year is a leap year.
	 */
	public static boolean isLeapYear(int year) {
		if (year % 400 == 0) {
			return true;
		} else if (year % 100 == 0) {
			return false;
		} else if (year % 4 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of days in a given month.
	 * 
	 * @param month
	 *            is a month, numbered in the range 1...12.
	 * @param year
	 *            is the year in question, with no digits omitted.
	 * @return the number of days in the given month.
	 */
	public static int daysInMonth(int month, int year) {
		switch (month) {
		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			return 31;
		case 4: case 6: case 9: case 11:
			return 30;
		case 2:
			if (isLeapYear(year)){
				return 29;
			}
			return 28;
		default:
			return -1;
		}
	}

	/**
	 * Checks whether the given date is valid.
	 * 
	 * @return true if and only if month/day/year constitute a valid date.
	 * 
	 *         Years prior to A.D. 1 are NOT valid.
	 */
	public static boolean isValidDate(int month, int day, int year) {
		if (year <= 0){
			return false;
		}
		if (month <= 0 || month > 12){
			return false;
		}
		if (day <= 0 || day > daysInMonth(month, year)){
			return false;
		}
		return true;
	}

	/**
	 * Returns a string representation of this date in the form month/day/year.
	 * The month, day, and year are expressed in full as integers; for example,
	 * 12/7/2006 or 3/21/407.
	 * 
	 * @return a String representation of this date.
	 */
	public String toString() {
		return String.format("%d/%d/%d", month, day, year);
	}

	/**
	 * Determines whether this Date is before the Date d.
	 * 
	 * @return true if and only if this Date is before d.
	 */
	public boolean isBefore(Date d) {
		if (this.year < d.year){
			return true;
		}
		if (this.year == d.year){
			if (this.month < d.month){
				return true;
			}
			if (this.month == d.month){
				if (this.day < d.day){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determines whether this Date is after the Date d.
	 * 
	 * @return true if and only if this Date is after d.
	 */
	public boolean isAfter(Date d) {
		if (this.isBefore(d)){
			return false;
		}
		if (this.day == d.day && this.month == d.month && this.year == d.year){
			return false;
		}
		return true;
	}

	/**
	 * Returns the number of this Date in the year.
	 * 
	 * @return a number n in the range 1...366, inclusive, such that this Date
	 *         is the nth day of its year. (366 is used only for December 31 in
	 *         a leap year.)
	 */
	public int dayInYear() {
		int total = 0;
		for (int i = month-1; i >= 1; i--){
			total += daysInMonth(i, year);
		}
		total += day;
		return total;
	}


	/**
	 * Determines the difference in days between d and this Date. For example,
	 * if this Date is 12/15/2012 and d is 12/14/2012, the difference is 1. If
	 * this Date occurs before d, the result is negative.
	 * 
	 * @return the difference in days between d and this date.
	 */
	public int difference(Date d) {
		if (this.year == d.year){
			return this.dayInYear() - d.dayInYear();
		}
		int highest = Math.max(this.year, d.year);
		int lowest = Math.min(this.year, d.year);
		int total = 0;
		for (int i = lowest+1; i < highest; i++){  //adds up full years in between
			total += 365;
			if (isLeapYear(i)){
				total += 1;
			}
		}
		if (this.isAfter(d)){
			total += this.dayInYear();
			total += 365 - d.dayInYear();
			if(isLeapYear(d.year)){
				total += 1;
			}
		}
		else {
			total += d.dayInYear();
			total += 365 - this.dayInYear();
			if(isLeapYear(this.year)){
				total += 1;
			}
			total *= -1;
		}
		return total;
	}
	
	/** Return the date of the day after today. */
	public Date getTomorrow() {
		int nextmonth = month;
		int nextday = day;
		int nextyear = year;
		
		if (Date.isValidDate(nextmonth, nextday + 1, nextyear)) {
			Date tomorrow = new Date(nextmonth, nextday + 1, nextyear);
			return tomorrow;
		} else if (Date.isValidDate(nextmonth + 1, 1, nextyear)) {
			Date tomorrow = new Date(nextmonth + 1, 1, nextyear);
			return tomorrow;
		} else {
			Date tomorrow = new Date(1, 1, nextyear + 1);
			return tomorrow;
		}
	}
}	