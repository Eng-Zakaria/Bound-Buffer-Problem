package com.example.boundbuffer.Models;

import java.io.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class BoundBuffer {
    public static int TotalNoTickets = LastPosion("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idTicket.txt");
    public static ArrayList<Ticket> tickets;
    public static int NoCustomers = LastPosion("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idCT.txt");
    public static int NoVendors = LastPosion("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\preload\\idVEN.txt");
    public static ArrayList<Customer> customers;
    public static ArrayList<Vendor> vendors;


    public BoundBuffer() {
        if (customers == null) customers = new ArrayList<>(10);
        if (vendors == null) vendors = new ArrayList<>(10);
        if (tickets == null) tickets = new ArrayList<>(20);

    }


    public static void main(String[] args) {
        BoundBuffer b = new BoundBuffer();
        b.ReadDatafile("D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase\\alltickets");


        /*
        System.out.println(NoCustomers);
        Customer c1 = new Customer("tito","t@gmial","123",13.2);
        Customer c2 = new Customer("ro","r@gmial","1234",10000.1);
        Customer c3 = new Customer("mooo","moo@gmial","12345",213);
        System.out.println(NoCustomers);

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
         */


    }


    public String now() {
        Calendar cal = Calendar.getInstance();

        Format f = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = f.format(new Date());
        return strDate;
    }

    public int compareTwoDates(Date d1, Date d2) {
        System.out.println("d1 " + d1);
        System.out.println("d2 " + d2);
        if (d1 == null || d2 == null) return -1;
        if (d1.compareTo(d2) > 0) {
            return 1;
        } else if (d1.compareTo(d2) < 0) {
            return 2;
        } else if (d1.compareTo(d2) == 0) {
            return 0;
        }

        return -1;
    }

    public Date convertStringToDate(String dateInString) {
        System.out.println("in convert " + dateInString);
        if (isValidFormat("dd-MM-yyyy", dateInString, Locale.ENGLISH)) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(dateInString);
            System.out.println("date " + date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static String NewFloder(String path, String name) {
        File f = new File(path + name);
        if (!f.exists()) {
            f.mkdirs();
        }
        return path + name;

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

    public static int LastPosion(String path) {

        String Last_postinoS = GetFirstLine(path);
        return Integer.valueOf(Last_postinoS);

    }

    public static String GetFirstLine(String path) {
        try {
            String sposion = new String();
            File myObj = new File(path);
            Scanner Reader = new Scanner(myObj);
            sposion = Reader.nextLine();
            Reader.close();
            return sposion;
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return "%$%";
        }

    }

    public static int setId(int id, String path) {
        id++;
        ChangeLastPosion(path, id);
        return 1;
    }

    private static void ChangeLastPosion(String path, int lastPostion) {
        try {
            FileWriter myWriter = new FileWriter(path);

            myWriter.write(lastPostion + "");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.to change a last postion");
            e.printStackTrace();
        }
    }

    public String Creetefiletxt(String filename, String path) {
        System.out.println("here" + filename + path);
        File Newfile = new File(path + filename + ".txt");
        if (Newfile.exists()) {
            System.out.println("File already exists");
            return path + filename + ".txt";
        } else {
            try {
                if (Newfile.createNewFile()) {
                    System.out.printf("Successfully created new file: %s%n", Newfile);
                    return path + filename + ".txt";
                } else {
                    System.out.printf("Failed to create new file: %s%n", Newfile);
                    return "%Error%";
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public int WriteData(String path, String Atributes[], String Values[], int NofValues) {
        for (int i = 0; i < NofValues; i++) {
            try (BufferedWriter bw
                         = new BufferedWriter(new FileWriter(path, true))) {

                String atribute, value;

                atribute = Atributes[i];
                value = Values[i];
                bw.write(atribute);
                bw.write(": ");
                bw.write(value);
                bw.newLine();
                bw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        return 1;
    }

    // Data written in file name as pattern -> [index 1] [index 2] [index 3]
    public String[] DataWrittenInFilesName(String PathFolderToReadPathesNames, int index) {
        String[] NamesFailes = Directorylist(PathFolderToReadPathesNames, 0);
        String[] result = new String[NamesFailes.length];
        for (int i = 0; i < NamesFailes.length; i++) {
            int Begin = 0;
            int End = 0;
            int x = 0;
            while (index != 1) {
                x = NamesFailes[i].indexOf('[', x);
                if (x > 0)
                    Begin = x;
                x++;
                index--;
            }
            End = NamesFailes[i].indexOf(']', Begin);

            result[i] = NamesFailes[i].substring(Begin, End);
        }


        return result;
    }

    public static int Nattriubeinfile(String path) {
        int Lines = 0;

        try {
            File file = new File(path);


            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                sc.nextLine();
                Lines++;
            }
            sc.close();
            return Lines;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return Lines;
    }

    public static int lengthAttribute(String line) {

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ')
                return i - 1;
        }
        return 0;
    }

    public static int lenghthVaule(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ')
                return line.length() - (i + 1);

        }
        return 0;
    }


    public List<String[][]> ReadDatainFloder(String pathFolder) {
        List<String[][]> Data = new ArrayList<String[][]>();
        String[] filetext = Directorylist(pathFolder, 1);
        for (int i = 0; i < filetext.length; i++) {
            System.out.println(filetext[i]);
            Data.add(ReadDatafile(filetext[i]));
        }
        return Data;
    }

    public String[][] ReadDatafile(String path) {
        int N = Nattriubeinfile(path);
        String[][] Data = new String[N][2];
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            int u = 0;
            while (line != null) {
                System.out.println(line);

                String[] linesplit = line.split(": ", 2);

                Data[u][0] = String.valueOf(linesplit[0]);
                Data[u][1] = String.valueOf(linesplit[1]);
                System.out.println(Data[u][0] + Data[u][1] );

                u++;
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Data;
    }

    public String[] Directorylist(String path, int mode) {
        String[] filesname = null;
        try {
            File folder = new File(path);
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return name.endsWith(".txt");
                }
            };
            File[] files = folder.listFiles(filter);


            filesname = new String[files.length];


            if (mode == 1) {
                for (int i = 0; i < files.length; i++) {
                    filesname[i] = files[i].getCanonicalPath();
                }
            } else {
                for (int i = 0; i < files.length; i++) {
                    filesname[i] = files[i].getName();
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return filesname;

    }

    public static int EditValueLine(String path,String nameOfAttributeToEdit ,String ValueToBeRepalce,int index) {
        File myFile = new File(path);
        ArrayList<String> fileContent = new ArrayList<String>();
        Scanner myReader = null;
        try {
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (myReader.hasNextLine()) {
            fileContent.add(myReader.nextLine());
        }
        myReader.close();
        if(!fileContent.get(index).contains(nameOfAttributeToEdit)) return 0;
        fileContent.remove(index);
        fileContent.add(index,ValueToBeRepalce);
        try {


            FileWriter myWriter = new FileWriter(path);
            for (String eachLine : fileContent) {
                myWriter.write(eachLine + "\n");
            }
            myWriter.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return 1;

    }
    public static String ReadValueLine(String path,String nameOfAttributeToRead ,int indexLinetoRed) {
        String line = null;

        try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
            for (int i = 0; i < indexLinetoRed; i++) br.readLine();
            line = br.readLine();
            String[] AV = line.split(": ",2);
            if(AV[0] == nameOfAttributeToRead){

                 return AV[1];
            }else {
                System.out.println("this line is not you required (check the index)");
                return null;
            }


        }
        catch(IOException e){
            System.out.println(e);
        }


         return null;
    }
    public static int NumberOftextFilesinFolder(String path) {
        try {
            File folder = new File(path);
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return name.endsWith(".txt");
                }
            };
            File[] files = folder.listFiles(filter);


            return files.length;
        }catch (Exception e){

        }
        return -1;
    }


}




