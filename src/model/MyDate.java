package model;

import java.time.LocalDate;
import java.util.stream.IntStream;

public class MyDate {
    private int day, month, year;

    public MyDate(int day, int month, int year){
        set(day, month, year);
    }

    public MyDate(int day, String month, int year){
        set(day, MyDate.convertToMonthNumber(month), year);
    }

    public MyDate(){
        LocalDate now = LocalDate.now();
        this.day = now.getDayOfMonth();
        this.month = now.getMonthValue();
        this.year = now.getYear();
    }

    public static int convertToMonthNumber(String monthName){
        switch (monthName){
            case "January" : return 1;
            case "February" : return 2;
            case "March" : return 3;
            case "April" : return 4;
            case "May" : return 5;
            case "June" : return 6;
            case "July" : return 7;
            case "August" : return 8;
            case "September" : return 9;
            case "October" : return 10;
            case "November" : return 11;
            case "December" : return 12;
        }
        throw new IllegalArgumentException("Invalid month name");
    }

    public void set(int day, int month, int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Invalid year provided ");
        }
        else {
            this.year = year ;
        }

        if (month > 12 || month <= 0) {
            throw new IllegalArgumentException("Invalid month provided ");
        }
        else {
            this.month = month;
        }

        if (day > this.numberOfDaysInMonth() || day <= 0) {
            throw new IllegalArgumentException("Invalid day provided");
        }
        else{
            this.day = day;
        }
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isLeapYear(){
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public String  getMonthName(){
        switch (month){
            case 1 :
                return "January";
            case 2 :
                return "February";
            case 3 :
                return "March";
            case 4 :
                return "April";
            case 5 :
                return "May";
            case 6 :
                return "June";
            case 7 :
                return "July";
            case 8 :
                return "August";
            case 9 :
                return "September";
            case 10 :
                return "October";
            case 11 :
                return "November";
            case 12 :
                return "December";
        }
        throw new IllegalArgumentException("Invalid month number provided");
    }

    public void stepForward(int days){
        if (days < 0) {
            throw new IllegalArgumentException("Negative value provided");
        }

        for (int i = 1; i <= days; i++) {
            if (day < this.numberOfDaysInMonth()) {
                day += 1;
            } else if (month < 12) {
                day = 1;
                month += 1;
            } else {
                day = 1;
                month = 1;
                year += 1;
            }
        }
    }

    public int numberOfDaysInMonth(){
        int[] month30 = {4,6,9,11};

        if (month == 2 && isLeapYear())
            return 29;
        else if (month == 2)
            return 28;
        else if (IntStream.of(month30).anyMatch(x -> x == month))
            return 30;
        else
            return 31;
    }

    public boolean isBefore(MyDate other){
        if (other == null) {
            throw new IllegalArgumentException("Null object provided");
        }

        if (other.year > this.year){
            return true;
        }
        else if ((other.year == this.year) && (other.month > this.month)){
            return true;
        }
        else if ((other.month == this.month) && (other.day > this.day)){
            return true;
        }
        else
            return false;
    }

    public int yearBetween(MyDate other){
        if (other == null) {
            throw new IllegalArgumentException("Null object provided");
        }

        int yearBetween;
        if (other.year > this.year){
            yearBetween = other.year - this.year;

            if (other.month < this.month){
                return yearBetween  - 1;
            }
        }
        else {
            yearBetween = this.year - other.year;

            if (this.month < other.month){
                return yearBetween  - 1;
            }
        }
        return yearBetween;
    }

    public String dayOfWeek(){
        int k, j;
        if (month == 1 || month == 2){
            k = (year - 1) % 100;
            j = (year - 1) / 100;
            month += 12;
        }
        else {
            k = year % 100;
            j = year / 100;
        }

        int theDay = ( day + (13*(month + 1))/5 + k + k/4 + j/4 + 5*j) % 7;

        switch (theDay){
            case 0 :
                return "Saturday";
            case 1 :
                return "Sunday";
            case 2 :
                return "Monday";
            case 3 :
                return "Tuesday";
            case 4 :
                return "Wednesday";
            case 5 :
                return "Thursday";
            case 6 :
                return "Friday";
        }
        return "Error";
    }

    public String getAstroSign(){
        if (new MyDate(20,3,year).isBefore(this) && this.isBefore(new MyDate(20,4,year)) ){
            return "Aries";
        }
        else if (new MyDate(19,4,year).isBefore(this) && this.isBefore(new MyDate(21,2,year))){
            return "Taurus";
        }
        else if (new MyDate(20,5,year).isBefore(this) && this.isBefore(new MyDate(21,6,year))){
            return "Gemini";
        }
        else if (new MyDate(20,6,year).isBefore(this) && this.isBefore(new MyDate(23,7,year))){
            return "Cancer";
        }
        else if (new MyDate(22,7,year).isBefore(this) && this.isBefore(new MyDate(23,8,year))){
            return "Leo";
        }
        else if (new MyDate(22,8,year).isBefore(this) && this.isBefore(new MyDate(23,9,year))){
            return "Virgo";
        }
        else if (new MyDate(22,9,year).isBefore(this) && this.isBefore(new MyDate(23,10,year))){
            return "Libra";
        }
        else if (new MyDate(22,10,year).isBefore(this) && this.isBefore(new MyDate(22,11,year))){
            return "Scorpio";
        }
        else if (new MyDate(21,11,year).isBefore(this) && this.isBefore(new MyDate(22,12,year))){
            return "Sagittarius";
        }
        else if (new MyDate(21,12,year).isBefore(this) && this.isBefore(new MyDate(20,1,year))){
            return "Capricorn";
        }
        else if (new MyDate(19,1,year).isBefore(this) && this.isBefore(new MyDate(19,2,year))){
            return "Aquarius";
        }
        else if (new MyDate(18,2,year).isBefore(this) && this.isBefore(new MyDate(21,3,year))){
            return "Pisces";
        }
        return "Error";
    }

    public MyDate copy(){
        return new MyDate(day, month, year);
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", day,month,year);
    }
}

