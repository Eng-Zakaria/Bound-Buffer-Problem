package com.example.boundbuffer.Models;

import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public static String rootDataBase= "D:\\Java programming\\OS2-project\\Bound-Buffer-Problem\\DataBase";
    public static int TotalNoTickets = LastPosion(rootDataBase +"\\preload\\idTicket.txt");
    public static ArrayList<Ticket> tickets;
    public static int NoCustomers = LastPosion( rootDataBase+"\\preload\\idCT.txt");
    public static int NoVendors = LastPosion(rootDataBase+"\\preload\\idVEN.txt");
    public static ArrayList<Customer> customers;
    public static ArrayList<Vendor> vendors;
    public static int newStateForticketsread = 1;
    public static int loadAllTicks = 1;



    public BoundBuffer() {
        if (customers == null) customers = new ArrayList<>(1000);
        if (vendors == null) vendors = new ArrayList<>(2000);
        if (tickets == null) tickets = new ArrayList<>(10000);

    }


    public static void main(String[] args) {
        System.out.println("here in bound buffer");
        BoundBuffer b = new BoundBuffer();

        System.out.println(tickets.size());
        System.out.println(vendors.toString());
        System.out.println(customers.toString());

/*
        for (int i=0;i< 10;i++) {
            customers.add(new Customer("m" + i, "m" + i + "@gmail.com", "c1234" + i, 10000.0, 1));
            vendors.add(new Vendor("v" + i, "user" + i, "v1234" + i, ".jpg", "dfdadfsad", 1));
        }
*/



      for (int i=0;i<20;i++) {
          if (i < 10)
              b.login("m"+i+"@gmail.com", "c1234"+i);
         else
             b.login("user"+(i - 10),"v1234"+ (i -10));
      }



        System.out.println("---------------main--------");


        loadAllTickets(rootDataBase+"\\ticketsVendors");
        setTicketsInAll();

        System.out.println(tickets.toString());
        System.out.println(customers.toString());
        System.out.println(vendors.toString());
        System.out.println(vendors.get(0).getTicketsForSellByEveryVendor().toString());
        System.out.println(vendors.get(5).getTicketsForSellByEveryVendor().toString());

        for (int i=0;i<customers.size();i++) {
            try {
                customers.get(i).addToCart(tickets.get(0), tickets.get(0).getQuantity());
            }catch (Exception e){
                System.out.println("there is no tickets in our system for now ,Check that later");
            }
        }

        for (int i=0;i < customers.size();i++){

            System.out.println("-----------------cart of customer"+ customers.get(i).getName()+"---------------");
            for (int ii =0; ii < customers.get(i).getCart().getNoTicketsInCart();ii++){
                System.out.println("name of ticket in cart:"+customers.get(i).getCart().getTicketsCart()[ii].getName() + " quantity: "+customers.get(i).getCart().getTicketsCart()[ii].getQuantity() );

            }
            System.out.println("--------------------------End Cart of ->"+customers.get(i).getName()+"-------------");
            System.out.println("---------------------------\n-------------------------\n");

        }

        System.out.println("         ----------------------------------------");
        System.out.println("----------------MultiThreading----------------------------");
        System.out.println("     ---------------------------------------------------");


        for (int i=0;i<customers.size();i++)
            customers.get(i).run();

        for (int i=0;i<vendors.size();i++)
            vendors.get(i).run();

        System.out.println("         ----------------------------------------");
        System.out.println("----------------MultiThreading----------------------------");
        System.out.println("      --------------------------------------------------------------------");

        for (int i=0;i< customers.size();i++) {
            System.out.println("customer name: "+customers.get(i).getName()+" tickets that's paid by: "+customers.get(i).getNoTicketPaidbyCustomers());
        }



      for (int i=0;i<customers.size();i++ )
        System.out.println(customers.get(0).logout());

        for (int i=0;i<vendors.size();i++ )
            System.out.println(vendors.get(0).logout());

    }

    public static ArrayList<Ticket> loadingDataTickets(String pathDirectory) {

        int NoTicket = NumberOfFilesinFolder(pathDirectory,".txt");

        ArrayList<String[]> DateCt = ReadDatainFloder(pathDirectory);
        ArrayList<Ticket> ticketss = new ArrayList<>();
       /* for every element in DataCt ex 0 String[]

        Atribute----------- Value[0]
        Atribute----------- Value[1]
        Atribute----------- Value[2]
        Atribute----------- Value[3]

        */
        for (int i = 0; i < DateCt.size(); i++) {
            int indexTempTicket = 0;
            int issoldticket = 0;
            int issuehapped = 0;

            String[] tempTicket = new String[DateCt.get(i).length];
            for (int ii = 0; ii < DateCt.get(i).length; ii++) {

                String line = DateCt.get(i)[ii];
                if (line.contains("~")) {
                    issoldticket = 1;
                }
                String av[] = line.split(": ", 2);

                int indexAttribute = searchForIndexAttriubeTicket(av[0]);
                if (indexAttribute < 0 || line.contains("%error%")) {
                    issuehapped = 1;
                    continue;
                }
                tempTicket[indexAttribute] = av[1];
                indexTempTicket++;
            }
            if (issuehapped == 1 || indexTempTicket < 12)
                continue;
            //"PathFolderOwner",        "PathOwner", "PathInViewCT",    "imagePath"  ,"id", "name", "type","quantity","Description","StartTime","EndTime","price" // 12
            //           0                      1              2           3          4       5      6              7       8             9          10     11
            //String PathFolderOwner,String pathOwner,String name, String type, int quantity,double price, String imagePath, String description,String startTime ,String EndTime) //10

            Ticket t = new Ticket(tempTicket[0], tempTicket[1], tempTicket[5], tempTicket[6], Integer.valueOf(String.valueOf(tempTicket[7])), Double.valueOf(tempTicket[11]), tempTicket[3], tempTicket[8], tempTicket[9], tempTicket[10], 0);
            t.setPathInViewCT(tempTicket[2]);
            t.setId(Integer.valueOf(tempTicket[4]));
            int Deadticket = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            try {
                if (sdf.parse(now()).after(sdf.parse(t.getEndTime()))) {
                    Deadticket = 1;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (issoldticket == 1 || t.getQuantity() == 0) {
                t.setIssold(1);
                t.MakeNoLongerAvailablity();
            }else {
               t.setIssold(0);
            }
            if (Deadticket == 1) {
                t.setDeadticket(1);
                t.MakeNoLongerAvailablity();
            }else {
                t.setDeadticket(0);
            }

            ticketss.add(t);


        }
        return ticketss;

    }


    public static String now() {
        Calendar cal = Calendar.getInstance();

        Format f = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = f.format(new Date());
        return strDate;
    }

    public int compareTwoDates(Date d1, Date d2) {

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
        if (isValidFormat("dd-mm-yy", dateInString, Locale.ENGLISH)) return null;
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
            String sposion="";
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
        System.out.println("here " + filename + path);
        System.out.println("-------------------------");
        System.out.println(path);
        System.out.println("-------------------------");
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

    public static int searchForIndexAttriubeTicket(String attribute) {

        String[] nameofattribues = {"PathFolderOwner", "PathOwner", "PathInViewCT", "imagePath", "id", "name", "type", "quantity", "Description", "StartTime", "EndTime", "price"}; //

        for (int i = 0; i < nameofattribues.length; i++) {
            if (nameofattribues[i].equals(attribute))
                return i;

        }
        return -1;
    }

    public int WriteData(String path, String []Atributes, String []Values, int NofValues) {
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
    public static String[] dataWrittenInFilesName(String PathFolderToReadPathesNames, int index) {
        String[] NamesFailes = Directorylist(PathFolderToReadPathesNames, 0,".txt");

        String[] result = new String[NamesFailes.length];
        for (int i = 0; i < NamesFailes.length; i++) {
            int Begin = NamesFailes[i].indexOf('[', 0);
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

            result[i] = NamesFailes[i].substring(Begin + 1, End);
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


    public static ArrayList<String[]> ReadDatainFloder(String pathFolder) {
        ArrayList<String[]> Data = new ArrayList<String[]>();
        String[] filetext = Directorylist(pathFolder, 1,".txt");
        for (int i = 0; i < filetext.length; i++) {
            Data.add(readDatafile(filetext[i]));
        }
        return Data;
    }


    public static String[] readDatafile(String path) {
        if (path.contains("!")) {
            System.out.println("this file is deleted file");
            return null;
        }
        int N = Nattriubeinfile(path);
        BufferedReader reader;
        String[] eachLines = new String[N];
        int index = 0;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) {
                eachLines[index] = line;
                // read next line to read
                line = reader.readLine();
                index++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return eachLines;
    }

    public static String[] DirectoryFolders(String path_SubDirectories_from_directory_NotFiles, int modeFullpath) {

        File[] directories = new File(path_SubDirectories_from_directory_NotFiles).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        String[] d = new String[directories.length];
        int index = 0;
        if (modeFullpath == 1) {
            for (int i = 0; i < directories.length; i++) {
                try {
                    if (!directories[i].getName().contains("!")) {
                        d[index] = directories[i].getCanonicalPath();
                        index++;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            for (int i = 0; i < directories.length; i++) {
                if (!directories[i].getName().contains("!")) {
                    d[index] = directories[i].getName();
                    index++;
                }
            }

        }
        return d;

    }

    public static String[] Directorylist(String path, int modeFullPath , String extentForFiles_endfile) {
        String[] filesname = null;
        try {
            File folder = new File(path);
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return name.endsWith(extentForFiles_endfile);
                }
            };
            File[] files = folder.listFiles(filter);


            filesname = new String[files.length];

            int index = 0;
            if (modeFullPath == 1) {
                for (int i = 0; i < files.length; i++) {
                    if (!files[i].getName().contains("!")) {
                        filesname[i] = files[i].getCanonicalPath();
                        index++;
                    }
                }
            } else {
                for (int i = 0; i < files.length; i++) {
                    if (!files[i].getName().contains("!")) {
                        filesname[i] = files[i].getName();
                        index++;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return filesname;

    }

    public static int editValueLine(String path, String nameOfAttributeToEdit, String ValueToBeRepalce, int index) {
        File myFile = new File(path);
        ArrayList<String> lineContent = new ArrayList<String>();
        Scanner myReader = null;
        try {
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (myReader.hasNextLine()) {
            lineContent.add(myReader.nextLine());
        }
        myReader.close();
        if (!lineContent.get(index).contains(nameOfAttributeToEdit)) return 0;

        String av[] = lineContent.get(index).split(": ", 2);
        lineContent.remove(index);
        lineContent.add(index, av[0] + ": " + ValueToBeRepalce);

        try {


            FileWriter myWriter = new FileWriter(path);
            for (String eachLine : lineContent) {
                myWriter.write(eachLine + "\n");
            }
            myWriter.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return 1;

    }

    public static int loadAllTickets(String pathDerectory) {

        if(loadAllTicks != 1)
            return 0;

        String[] foldersVendors = DirectoryFolders(pathDerectory, 1);
        tickets = new ArrayList<>();
        for (int i = 0; i < foldersVendors.length; i++) {
            ArrayList<Ticket> t = loadingDataTickets(foldersVendors[i]);
            System.out.println("t size " + t.size());
            for (int iii = 0; iii < t.size(); iii++) {
                tickets.add(t.get(iii));
            }


        }
        loadAllTicks = 0;
        return 1;
    }


    public static int setTicketsInAll() {
        if(newStateForticketsread == 0)
            return 0;


        for (int i = 0; i < vendors.size(); i++) {

            int index = -1;

            for (int x = 0; x < vendors.get(i).getTicketsForSellByEveryVendor().size(); x++) {
                for (int j = 0; j < tickets.size(); j++) {
                    if (vendors.get(i).getTicketsForSellByEveryVendor().get(x).getId() == tickets.get(j).getId() &&vendors.get(i).getTicketsForSellByEveryVendor().get(x).hashCode() !=  tickets.get(j).hashCode()) {

                        if(index < 0)
                            index = j;

                        tickets.remove(j);

                    }
                }
            }
            if(index >=0 )
            tickets.addAll(index,vendors.get(i).getTicketsForSellByEveryVendor());
        }
        newStateForticketsread =0;
        return 1;

    }





    public static int stringContainInArrayString(String [] s ,String f){
        for(int i=0;i<s.length;i++){
            if(s[i].equals(f))
                return i;

        }
        return -1;
    }
    public BoundBuffer readProfileData(String pathfiletobeRead,int cv){
        String[] dataInFile = this.readDatafile(pathfiletobeRead);


        int noAttribute = 0;
        String[] Attribues = null;


        if(cv == 1){
         String[] nameofattribuesinct = {"id", "name", "email", "password","noTicketPaidbyCustomers","balance","pathForallQrsFloderCT"};//
          Attribues = nameofattribuesinct;

        }else if(cv == 2) {
          String[] nameofattribuesinven = {"imagePath", "id", "nameOfStore", "userName", "password", "Description", "TicketsForSell", "TicketsIncludeQuantity", "pathFolderTicketsCreatedByMeToSave"};
          Attribues = nameofattribuesinven;
          cv = 2;
        }else {
            System.out.println("object arg. should be either Customer or Vendor ");
            return null;
        }
        // check the data is valid or not

        for(int i=0;i<dataInFile.length;i++) {

            String line =dataInFile[i];
            String av[] = line.split(": ", 2);

            int indexAttribute = stringContainInArrayString(Attribues,av[0]);

            if(indexAttribute < 0 || line.contains("%error%")) {
                System.out.println("their is an issue in this file wrong data");
                return null;
            }
            dataInFile[i] =av[1];
            noAttribute++;
        }

        if(noAttribute < Attribues.length){
            System.out.println("this file has missed data");
            return null;
        }


        //      Customer(String name,String email,String password,double balance,int newData)
        //                     0      1        2       3             4                      5                   6
        //  nameOfAttribues = {"id", "name", "email", "password","noTicketPaidbyCustomers","balance","pathForallQrsFloderCT"};//

        if(cv == 1){
        Customer c = new Customer(dataInFile[1], dataInFile[2], dataInFile[3], Double.parseDouble(dataInFile[5]),0);

        c.setId(Integer.valueOf(dataInFile[0]));

        c.setNoTicketPaidbyCustomers(Integer.valueOf(dataInFile[4]),1);

        c.setPathForAllQrsFolder(dataInFile[6]);

        c.setPathFileForInfo(pathfiletobeRead);

        customers.add(c);

        loadAllTickets(rootDataBase+"\\ticketsVendors");

        return c;

        }else if(cv == 2) {
               //
              //    public Vendor(String nameOfStore,String username,String password,String imagepath,String des,int newData)

             //        0         1         2            3           4             5               6                 7                                         8
            //     "imagePath", "id", "nameOfStore", "userName", "password", "Description", "TicketsForSell", "TicketsIncludeQuantity", "pathFolderTicketsCreatedByMeToSave"};
            Vendor v = new Vendor(dataInFile[2], dataInFile[3], dataInFile[4], dataInFile[0],dataInFile[5],0);

            v.setId(Integer.valueOf(dataInFile[1]));
            v.setNoTickets(Integer.valueOf(dataInFile[6]),1);
            v.setTotalnoTicketsIcludeQuntity(Integer.valueOf(dataInFile[7]), 1);
            v.setPathFolderTicketCreatedByVendor(dataInFile[8]);


            v.setPathInfofile(pathfiletobeRead);
            ArrayList<Ticket> t =loadingDataTickets(v.getPathFolderTicketCreatedByVendor());

            v.setTicketsForSellByEveryVendor(t);

             for (int i=0;i<v.getTicketsForSellByEveryVendor().size();i++){
                 v.getTicketsForSellByEveryVendor().get(i).setOwner(v);
             }


            vendors.add(v);

         newStateForticketsread = 1;
            return v;
        }

        return null;

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
                System.out.println("this line is not you required to be change (check the index)");
                return null;
            }


        }
        catch(IOException e){
            System.out.println(e);
        }


         return null;
    }
    public static int NumberOfFilesinFolder(String path,String extendOfFiles) {
        int N=0;
        try {
            File folder = new File(path);
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return name.endsWith(extendOfFiles);
                }
            };
            File[] files = folder.listFiles(filter);
            for(int i=0;i<files.length;i++){
                if(!files[i].getName().contains("!")){
                    N++;
                }

            }

            return N;
        }catch (Exception e){
            return -1;
        }
    }


    public static int writeInFileAppendNArgu(String pathfile, String[] arg,String[] value,int N) {
        int index = (int) sizeTextFile(pathfile);
        try (BufferedWriter bw
                     = new BufferedWriter(new FileWriter(pathfile, true))) {
            for (int i = 0; i < N; i++) {

                bw.append(arg[i]);

                bw.append(": ");
                bw.append(value[i]);
                bw.newLine();
            }
            bw.newLine();
            bw.flush();

        }catch(IOException ex){
            ex.printStackTrace();
        }


        return index;

    }

    public static long sizeTextFile(String pathFile){
        String file = pathFile;
        File f = new File(file);
        return f.length();

    }

     public static int addLoginData(String pathToSaveLoginData,String accountname,String password,String infoPathFile){
         String[] argus = {"account","password","deleted","islogin","pathInfo"};

         String[] values = {accountname,password,String.valueOf(0),String.valueOf(0),infoPathFile};

         if(argus.length != values.length) return 0;

        return writeInFileAppendNArgu(pathToSaveLoginData,argus,values,argus.length);

        }

    public static String searchInSearchFile(String path, String uniqueArgToGuide, String uniqueValueToGuide,String nameOfAttributeYouSearchForHisValue){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {

                if(line.equals(uniqueArgToGuide+": "+uniqueValueToGuide)) {
                    while (!line.equals("\n") && line != null) {

                        line = reader.readLine();
                        if (line.contains(nameOfAttributeYouSearchForHisValue)) {
                             String value = line.substring(line.indexOf(": ")+2);
                              return value;
                        }


                    }
                }

                line = reader.readLine();

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] blockSearchInSearchFile(String path, String uniqueArgToGuide, String uniqueValueToGuide){
        BufferedReader reader;
         String[] block=null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) {

                if(line.equals(uniqueArgToGuide+": "+uniqueValueToGuide)) {
                    int lineIndex=1;
                    block = new String[5];
                    block[0] = line;
                    line = reader.readLine();

                    while (!(line.equals("\n")) && line != null && lineIndex < block.length) {

                        block[lineIndex] = line;
                        line = reader.readLine();

                        lineIndex++;
                    }
                    return block;
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return block;
    }


    public static String checkAccount(String accountToBeCheck,String nameOfAttribute){
        String value = "%error%";
        String pathAccount=null;
        String pathFolder=null;
        if(accountToBeCheck.contains("@")) {
            pathAccount = rootDataBase+"\\SearchData\\CustomersLoginData.txt";
            pathFolder = rootDataBase+"\\Customers";
        }
        else {
            pathAccount = rootDataBase+"\\SearchData\\vendorsLoginData.txt";
            pathFolder = rootDataBase+"\\vendors";
        }

        String[]data= dataWrittenInFilesName(pathFolder,1);

        if(stringContainInArrayString(data,accountToBeCheck) >=0 ){

            value = searchInSearchFile(pathAccount,"account",accountToBeCheck,nameOfAttribute);
            return value;

        }else {

            return value;
        }

    }

    public static int editValueInSearchFile(String path,String uniqueArgToGuide, String uniqueValueToGuide,String nameOfAttributeToEdit ,String valueToBeRepalce) {
        File myFile = new File(path);
        ArrayList<String> lineContent = new ArrayList<String>();
        Scanner myReader = null;

        try {
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (myReader.hasNextLine()) {
            lineContent.add(myReader.nextLine());
        }
        myReader.close();


        int index= -1 ;
        for (int i=0;i<lineContent.size();i++){

            if(lineContent.get(i).equals(uniqueArgToGuide+": "+uniqueValueToGuide)) {

                for (int j= i;j<=i+5;j++){

                    if(lineContent.get(j).contains(nameOfAttributeToEdit)){

                        index = j;
                        break;
                    }

                }

            }


        }
        String newValue = nameOfAttributeToEdit +": "+valueToBeRepalce;
        String[] av = new String[2];
        if(lineContent != null) {
             av = lineContent.get(index).split(": ", 2);
        }
        if(av[0].equals(nameOfAttributeToEdit)) {
            lineContent.remove(index);
            lineContent.add(index, newValue);
        }else {
            System.out.println("invalid name of Edit Argu");

        }
        try {


            FileWriter myWriter = new FileWriter(path);
            for (String eachLine : lineContent) {
                myWriter.write(eachLine + "\n");
            }
            myWriter.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return 1;

    }


    /*
    *
    *
    *
    * */

    public Pair<BoundBuffer , Integer>login(String account, String password){
        int whichOne = 0;
        String iscorrect = checkAccount(account,"islogin");

        if(iscorrect.equals("%error%") || iscorrect == null){
            System.out.println("here");
            return new Pair ( null,-1 );
        }else if(iscorrect.equals("1")){
            return new Pair ( null,-2 );

        }


        if(account.contains("@"))
            whichOne = 1;

        else
            whichOne = 2;


        String[] validationInfo=null;

        if(whichOne == 1)
          validationInfo= blockSearchInSearchFile(rootDataBase+"\\SearchData\\CustomersLoginData.txt","account",account);
        else
          validationInfo = blockSearchInSearchFile(rootDataBase+"\\SearchData\\vendorsLoginData.txt","account",account);

        if(validationInfo == null) return new Pair ( null,-3 );



        for(int i=0;i<validationInfo.length;i++){
            validationInfo[i] = validationInfo[i].substring(validationInfo[i].indexOf(": ")+2);
        }




        if( !(password.equals(validationInfo[1]))   && !(account.equals(validationInfo[0]))  )
            return new Pair ( null,-1 );

        //account:          0
        //password:         1
        //deleted:          2
        //islogin:          3
        //pathInfo:         4

        if(validationInfo[2].equals("1"))
            return new Pair ( null,-2 );
        else if (validationInfo[3].equals("1"))
            return new Pair ( null,-4);



        BoundBuffer read = readProfileData(validationInfo[4],whichOne);

        if(read instanceof  Customer){

            //  editValueInSearchFile(rootDataBase+"\\SearchData\\CustomersLoginData.txt","account",account,"islogin","1");

         }else if(read instanceof Vendor){

           // editValueInSearchFile(rootDataBase+"\\SearchData\\vendorsLoginData.txt","account",account,"islogin","1");

        }
        System.out.println("read cv : "+read);

        return new Pair(read, 1);
    }

    public static int signUp(int cv,String[] newData) {
        for (int i=0;i<newData.length;i++){
            if(newData[i] == null)
                return 0;

        }



        if (cv == 1) {

            if(!newData[1].contains("@")) {
                System.out.println("you must enter an email");
                return -1;
            }
            if(newData.length != 4){
                System.out.println("you have to fil all data required");
                return -2;
            }

            String[] emails = dataWrittenInFilesName(rootDataBase+"\\Customers",1);

            System.out.println(Arrays.toString(emails));
            if (stringContainInArrayString(emails, newData[1]) >= 0) {
                return 0;
            }
            // String name,String email,String password,double balance,int newData 5
            Customer c = new Customer(newData[0], newData[1], newData[2], Double.parseDouble(newData[3]), 1);
            c = null;
            return 1;
        } else if (cv == 2) {

            if(newData[1].contains("@")) {
                System.out.println("invaild data enter in username (must be not include @  .  % )");
                return -1;
            }

            if(newData.length != 5){
                System.out.println("you have to fil all data required");
                return -2;
            }


            String[] userNames = dataWrittenInFilesName(rootDataBase+"\\vendors", 1);
            System.out.println(Arrays.toString(userNames));
            if (stringContainInArrayString(userNames, newData[1]) >= 0) {
                return 0;
            }

            //String nameOfStore,String username,String password,String imagepath,String des,int newData
            Vendor v = new Vendor(newData[0], newData[1],newData[2],newData[3],newData[4],1);
            v = null;
           return 1;
        }
          return -1;
    }

    public static int logout(BoundBuffer b, String account,String password){
        String path = null;


        if(b instanceof Customer){
            customers.remove(b);
            path = rootDataBase+"\\SearchData\\CustomersLoginData.txt";
        }
        else if (b instanceof  Vendor) {
            vendors.remove(b);
        path =rootDataBase+"\\SearchData\\vendorsLoginData.txt";
        }
        int check = editValueInSearchFile(path,"account",account,"islogin","0");


        return check;
    }

    public static String renameFile(String abouslutePath,String newName){
        Path source = Paths.get(abouslutePath);

        try {
            if(!newName.contains("."))
                return null;

            String s= new File(abouslutePath).getAbsolutePath();
            int end = s.lastIndexOf("\\");
            s = s.substring(0,end+1);
            System.out.println(s+newName);

            Files.move(source, source.resolveSibling( s + newName));
            return s+newName;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }



    }
        public static String renameDirectory(String nameDirectory , String newName){
            File dir = new File(nameDirectory);
            if (!dir.isDirectory()) {
                System.err.println("There is no directory @ given path");
                return null;
            } else {

                File newDir = new File(dir.getParent() + "\\" + newName);
                dir.renameTo(newDir);
                try {
                    return newDir.getCanonicalPath();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }

        public static int makeFileBeDeleted(String path, String name){
        System.out.println(renameFile(path,"! "+name));


        return 1;
        }




    }










