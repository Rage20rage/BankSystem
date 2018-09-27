package lws.banksystem.server.log;
/*
public class kontoauszug {
}*/

import java.io.File;
import java.io.*;
import java.time.LocalDate;


public class kontoauszug {
    public static LocalDate dateTime = LocalDate.now();

    public static void PostBoxCreatenew(String accountnum) throws Exception {
        File file = new File(accountnum + "new");

        //Create the file
        if (file.createNewFile()) {
            System.out.println("File is created!");
            //Write Content
            FileWriter writer = new FileWriter(file);
            LocalDate dateTime = LocalDate.now();
            writer.write(dateTime.toString() + "\n");
            writer.close();
        } else {
            System.out.println("File already exists.");
        }
    }

    public static void PostBoxCreateold(String accountnum) throws Exception {
        File file = new File(accountnum + "old");
        file.setReadOnly();
        //Create the file
        if (file.createNewFile()) {
            System.out.println("File is created!");
            //Write Content
            FileWriter writer = new FileWriter(file);
            LocalDate dateTime = LocalDate.now();
            writer.write(dateTime.toString());
            writer.close();
        } else {
            System.out.println("File already exists.");

        }
    }


    public static String CreatSkeleton() {
        //heir DB verbendung
        String name = "Adnan Almsuker";
        String acc_num = "1234";
        String headfott = "1and1_Bank\n";
        String head = "Konto-Nr " + acc_num + "\t" + name + "\n";
        String body = "Datum\t\t" + "Buchungtext\t\t" + "Betrag\n";
        //String staff=dateTime.toString()+"\t"+buchungtext+"\t"+betrag;
        String skeleton = headfott + head + body/*+staff*/;
        return skeleton;
    }

    public synchronized static void DataSevr(String accountnum, String acc_Balance, String bookingText) throws IOException {
        // LocalDate dateTime = LocalDate.now();
        String bodyin = dateTime + "\t" + bookingText + "\t" + "\t" + acc_Balance + " €\n";
        String test = accountnum + "new";
        File file;// heir pfart eintragen
        file = new File(test);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(test, true);
            BufferedWriter bw = new BufferedWriter(fw);
            fw.append(bodyin);
            fw.flush();
            fw.close();

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized static void FileWriterAttribute(String accountnum) {
        try {
            File file = new File(accountnum + "new");// heir pfart eintragen
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);

            if (!file.exists()) {
                file.createNewFile();

            } else {
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(CreatSkeleton());
                System.out.println("Done");
                bw.close();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void ReadAccontData(String accountnum) throws IOException {
        String name = accountnum + "new";
        FileReader filer = new FileReader(name);
        // heir vondaten bank aktuel konto stand holen
        int i;
        while ((i = filer.read()) != -1) {
            System.out.print((char) i);
        }
        filer.close();

    }
    public static void ReadAccontDataold(String accountnum) throws IOException {
        String name = accountnum + "old";
        FileReader filer = new FileReader(name);
        // heir vondaten bank aktuel konto stand holen
        int i;
        while ((i = filer.read()) != -1) {
            System.out.print((char) i);
        }
        filer.close();

    }


    public synchronized static void Datamoving(String accountnum) throws IOException {

        FileReader filer = new FileReader(accountnum + "new");
        //char[] a = new char[Integer.parseInt(accountnum + "new".toString().length())];
        // filer.read(a);
        String text = "";
        BufferedReader br = new BufferedReader(filer);
        String[] text1 = new String[1024];
        boolean cont = true;

        FileWriter writer = new FileWriter(accountnum + "old", true);
        BufferedWriter bw = new BufferedWriter(writer);
        int i;
        while ((i = filer.read()) != -1) {
            text += (char) i;
        }
        writer.append("\n" + dateTime + "\n" + text);
        bw.close();

        writer.close();
        filer.close();

        FileWriter filew = new FileWriter(accountnum + "new");
        filew.write(CreatSkeleton());
        filew.flush();
        filew.close();
        System.out.println("end");
        //File file = new File("test");
        //file.deleteOnExit();
    }


    /*public static void main(String[] args) throws Exception {
        System.out.println(CreatSkeleton());
        //PostBoxCreatenew("2");
        //PostBoxCreatenew("1");
        //PostBoxCreateold("2");
        //FileWriterAttribute("1" );
        //FileWriterAttribute("2");

        DataSevr("2", "18", "Einzahlen");
        DataSevr("1", "18", "Einzahlen");
        ReadAccontData("2");
        //Datamoving("2");
        //ReadAccontDataold("1");

        // nur test zwike
    }*/


}

