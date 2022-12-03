package com.example.boundbuffer.Models;

import java.io.File;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BoundBuffer{
    public static int TotalNoTickets = 0;
    public static ArrayList<Ticket>tickets;
    public static int NoCustomers=0;
    public static int NoVendors=0;
    public static ArrayList<Customer> customers;
    public static ArrayList<Vendor> vendors;






    public BoundBuffer(){
        if(customers == null) customers = new ArrayList<>(10);
        if(vendors == null)vendors = new ArrayList<>(10);
        if(tickets == null)tickets = new ArrayList<>(10);

    }


    public static void main (String[] args) {
        BoundBuffer b = new BoundBuffer();
        Vendor v1 = new Vendor("mohamed",".png","akjdklfjdakl");
        Vendor v2 = new Vendor("ail",".jpg","adjfdajiaj");
        v1.addTicket("m1","selver",13,120,".png","adkjfkajdklf","12-12-2022");
        v1.addTicket("m2","selver",40,120,".png","adkjfkajdklf","12-12-2022");
        v1.addTicket("m3","selver",12,120,".png","adkjfkajdklf","12-12-2022");
        v1.addTicket("m4","selver",12,120,".png","adkjfkajdklf","12-12-2022");
        v1.addTicket("m5","selver",12,120,".png","adkjfkajdklf","12-12-2022");
        v1.addTicket("m6","selver",12,120,".png","adkjfkajdklf","12-12-2022");



        Customer c1 = new Customer("tito","t@gmial",13.2);
        Customer c2 = new Customer("ro","r@gmial",10000.1);
        Customer c3 = new Customer("mooo","moo@gmial",213);


        c2.addToCart(tickets.get(0),5);
        c2.addToCart(tickets.get(1),10);
        c2.addToCart(tickets.get(2),1);

        for (int i=0;i<c2.getCart().getNoTicketsInCart();i++) {
            System.out.println(c2.getCart().getTicketsCart()[i].getName() +" "+ c2.getCart().getQuentatiyForEachTickets()[i]);
        }


        System.out.println(c2.getCart().getTotalNumberInCart());

        c2.reduceQuentatiyInCart(tickets.get(0),8);

        System.out.println("-------------------");
        for (int i=0;i<c2.getCart().getNoTicketsInCart();i++) {
            System.out.println(c2.getCart().getTicketsCart()[i].getName() +" "+c2.getCart().getQuentatiyForEachTickets()[i] );
        }

        System.out.println("tickets :__"+c2.getNoTicketPaidbyCustomers());
        c2.checkOut();
        System.out.println("tickets :__"+c2.getNoTicketPaidbyCustomers());


        for (int i=0;i<c2.getIndexQr();i++)
        System.out.println(c2.getAllQrs()[i]);

        System.out.println("--------------");

        System.out.println(c1.checkOut());



    }


    public String now() {
        Calendar cal = Calendar.getInstance();

        Format f = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = f.format(new Date());
        return strDate;
    }
    public int compareTwoDates(Date d1,Date d2) {
        System.out.println("d1 "+d1);
        System.out.println("d2 "+d2);
        if(d1==null || d2 == null) return -1;
        if (d1.compareTo(d2) > 0) {
            return 1;
        } else if (d1.compareTo(d2) < 0) {
            return 2;
        } else if (d1.compareTo(d2) == 0) {
            return 0;
        }

        return -1;
    }
    public Date convertStringToDate(String dateInString){
        System.out.println("in convert "+dateInString);
        if(isValidFormat("dd-MM-yyyy",dateInString,Locale.ENGLISH)) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(dateInString);
            System.out.println("date "+date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
    public static String NewFloder(String path,String name) {
        File f = new File(path+name);
        if (!f.exists()){
            f.mkdirs();
        }
         return path+name;

    }
    public static boolean isValidFormat(String format, String value, Locale locale) {
        LocalDateTime ldt = null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    // Debugging purposes
                    //e2.printStackTrace();
                }
            }
        }

        return false;
    }
}
