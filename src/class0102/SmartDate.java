package class0102;
import rlgs4.Date;

public class SmartDate extends Date{

	public SmartDate(int month, int day, int year) {
		super(month, day, year);
	}
	
	private int intervalYear(Date that) {
		if(this.compareTo(that)>0) {
			throw new IllegalArgumentException("Invalid sequence");
		}
		
		SmartDate tmp = makeValid(that.year(), this.month(), this.day());
		
		return tmp.compareTo(that)<=0 ? that.year()-this.year() : that.year()-this.year()-1;
	}
	
	private int intervalMonth(Date that) {
		int months = 0;
		int years = intervalYear(that);
		int acYears = that.year()-this.year();
		
		if(years != acYears) {
			months += 12-this.month();
			SmartDate tmp = makeValid(that.year(), that.month(), this.day());
			if(tmp.compareTo(that)>=0) {
				months += that.month();
			}else{
				months+=that.month()-1;
			}
		}else{
			SmartDate tmp = makeValid(that.year(), that.month(), this.day());
			if(tmp.compareTo(that)>=0) {
				months += that.month()-this.month();
			}else{
				months += that.month()-this.month()-1;
			}
		}
		
		months += 12*years;
		return months;
	}
	
	private String dayOfWeek() {
		int days = 0;
		for(int i=1900; i<this.year(); i++) {
			days += isLeapYear(i)? 366:365;
		}
		for(int i=1; i<this.month(); i++) {
			switch(i) {
			case 1:case 3:case 5:case 7:case 8:case 10:case 12:days+=31;break;
			case 4:case 6:case 9: case 11:days+=30;break;
			case 2:days += isLeapYear(i)? 29:28;break;
			}
//			System.out.println(days);
		}
		days += this.day()-1;
		String date = "";
		switch((days%7+1)%7) {
			case 0:date = "Sunday";break;
			case 1:date = "Monday";break;
			case 2:date = "Tuesday";break;
			case 3:date = "Wednesday";break;
			case 4:date = "Thursday";break;
			case 5:date = "Friday";break;
			case 6:date = "Saturday";break;
		}
		return date;
	}
	
	private static boolean isLeapYear(int y) {
		return y%4==0 && y%100!=0 || y%400==0;
	}
	
	private static SmartDate makeValid(int year, int month, int day) {
		switch(month) {
		case 1:case 3:case 5:case 7:case 8:case 10:case 12:
			day = day>31? 31:day;break;
		case 4:case 6:case 9: case 11:
			day = day>30? 30:day;break;
		case 2:
			if(isLeapYear(year)) {
				day = day>29? 29:day;
			}else{
				day = day>28? 28:day;
			}
			;break;
		}
		return new SmartDate(month, day, year);
	}
	
	public static void main(String[] args) {
		System.out.println(new SmartDate(1, 1, 1900).dayOfWeek());
		SmartDate testYear1 = new SmartDate(2, 29, 2000);
		SmartDate testYear2 = new SmartDate(2, 28, 2002);
		System.out.println(testYear1.intervalYear(testYear2));
		System.out.println(testYear1.intervalMonth(testYear2));
//		System.out.println(makeValid(2014, 2, 30).toString());
	}

}
