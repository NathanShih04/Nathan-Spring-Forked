package com.nighthawk.spring_portfolio.mvc.calendar;
import java.util.*;

// Prototype Implementation

public class APCalendar {

    /** Returns true if year is a leap year and false otherwise.
     * isLeapYear(2019) returns False
     * isLeapYear(2016) returns True
     */          
    public static boolean isLeapYear(int year) {

        // Checks to see if variable year is divisible by 4, 100, and 400 in order to check for leapyear status
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)){
            return true;
        }
        // Returns false if not
        else{
            return false;
        }
    }
        
    /** Returns the value representing the day of the week 
     * 0 denotes Sunday, 
     * 1 denotes Monday, ..., 
     * 6 denotes Saturday. 
     * firstDayOfYear(2019) returns 2 for Tuesday.
    */

    // Determines what day the first day of the year falls on in any given year, using 2022 as the baseline
    public static int firstDayOfYear(int year) {
        int currentYearFirstDay = 6;

        // First day in 2022 is sat so 6
        if (year == 2022) {
            currentYearFirstDay = 6;
        } 
        // Uses 2022 as baseline for calculations for years before 2022
        else if (year < 2022) {
            int yearsAway = 2022 - year;
            int leapYearsAway = numberOfLeapYears(year, 2022);
            int nonLeapYearsAway = yearsAway - leapYearsAway;
            int daysAway = (leapYearsAway * 366) + (nonLeapYearsAway * 365);
            // Decrements from 6 but once it reaches negatives, resets back to 6
            for (int i = 0; i < daysAway; i++) {
                currentYearFirstDay--;
                if (currentYearFirstDay == -1) {
                    currentYearFirstDay = 6;
                }
            }
        } 
        // Uses 2022 as baseline for calculations for years after 2022
        else if (year > 2022) {
            int yearsAway = year - 2022;
            int leapYearsAway = numberOfLeapYears(2022, year-1);
            int nonLeapYearsAway = yearsAway - leapYearsAway;
            int daysAway = (leapYearsAway * 366) + (nonLeapYearsAway * 365);
            // Increments from 0 but once it reaches 7, resets back to 0
            for (int i = 0; i < daysAway; i++) {
                currentYearFirstDay++;
                if (currentYearFirstDay == 7) {
                    currentYearFirstDay = 0;
                }
            }
        }
        
        return currentYearFirstDay;
    }

    /** Returns n, where month, day, and year specify the nth day of the year.
     * This method accounts for whether year is a leap year. 
     * dayOfYear(1, 1, 2019) return 1
     * dayOfYear(3, 1, 2017) returns 60, since 2017 is not a leap year
     * dayOfYear(3, 1, 2016) returns 61, since 2016 is a leap year. 
    */ 

    // Rohit helped expain map.
    public static int dayOfYear(int month, int day, int year) {
        int num = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 0);
        map.put(2, 31);
        map.put(3, 59);
        map.put(4, 90);
        map.put(5, 120);
        map.put(6, 151);
        map.put(7, 181);
        map.put(8, 212);
        map.put(9, 243);
        map.put(10, 273);
        map.put(11, 304);
        map.put(12, 334);
        if (isLeapYear(year) && month > 2) {
            num = map.get(month) + day + 1;

        } else {
            num = map.get(month) + day;
        }

        return num;
    }

    /** Returns the number of leap years between year1 and year2, inclusive.
     * Precondition: 0 <= year1 <= year2
    */ 

    // COunts the number of leapyears
    public static int numberOfLeapYears(int year1, int year2) {
        int leapCount = 0;

        // Increments leapCount if isLeapYear for a given year is true
        for (int i = year1; i <= year2; i++ ) {
            if (isLeapYear(i) == true) {
                leapCount++;
            }
        }

        return leapCount;
    }

    /** Returns the value representing the day of the week for the given date
     * Precondition: The date represented by month, day, year is a valid date.
    */

    // Gives the day of week that a certain day falls on
    public static int dayOfWeek(int month, int day, int year) { 
        int yearFirstDay = firstDayOfYear(year);
        // Non leap year
        int feb = 28;
        // If it is a leap year, add a day
         if (isLeapYear(year) == true) {
            feb = 29;
        }
        int daysAway = 0;
        // Gives the number of days in each month, Jan - Dec, feb changes on a leap year
        int[] months = {31, feb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i < month - 1; i++) {
            daysAway += months[i];
        }

        daysAway += (day - 1);
        int weekDay = yearFirstDay;
        for (int i = 0; i < daysAway; i++) {
            weekDay++;
            if (weekDay == 7) {
                weekDay = 0;
            }
        }

        return weekDay;
    }

    /** Tester method */
    public static void main(String[] args) {
        // Uses user input to enter dates
        Scanner inp = new Scanner(System.in);
        // Private access modifiers
        System.out.println("Enter year:");
        int year1 = inp.nextInt();
        System.out.println("firstDayOfYear: " + APCalendar.firstDayOfYear(year1));

        System.out.println("Enter year:");
        int year2 = inp.nextInt();
        System.out.println("Enter month:");
        int month2 = inp.nextInt();
        System.out.println("Enter day:");
        int day2 = inp.nextInt();
        System.out.println("dayOfYear: " + APCalendar.dayOfYear(month2, day2, year2));

        // Public access modifiers
        System.out.println("Enter year:");
        int year3 = inp.nextInt();
        System.out.println("isLeapYear: " + APCalendar.isLeapYear(year3));

        System.out.println("Enter year:");
        int year4 = inp.nextInt();
        System.out.println("Year after what you just inputted:");
        int yearInput4After = inp.nextInt();
        System.out.println("numberOfLeapYears: " + APCalendar.numberOfLeapYears(year4, yearInput4After));

        System.out.println("Enter year:");
        int year5 = inp.nextInt();
        System.out.println("Enter month:");
        int month5 = inp.nextInt();
        System.out.println("Enter day:");
        int day5 = inp.nextInt();
        System.out.println("dayOfWeek: " + APCalendar.dayOfWeek(month5, day5, year5));
    }

}