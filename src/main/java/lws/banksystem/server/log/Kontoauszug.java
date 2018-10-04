package lws.banksystem.server.log;
import java.io.*;
import java.time.*;


public class Kontoauszug {
    public static LocalDate dateTime = LocalDate.now();

    public static void createPostBox(String accountnum) {
        File file = new File(accountnum + "new");

        //Create the file
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String creatSkeleton(String name, String acc_num) {
        String headfott = "1and1_Bank\n";
        String head = "Konto-Nr " + acc_num + "\t" + name + "\n";
        String body = "Datum\t\t" + "Buchungtext\t\t\t" + "Betrag\n";
        String skeleton = headfott + head + body;
        return skeleton;
    }

    public static void dataSevr(String accountnum, String acc_Balance, String bookingText) {
        // LocalDate dateTime = LocalDate.now();
        String bodyin = dateTime + "\t\t" + bookingText + "\t\t\t" + acc_Balance + " €\n";
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

    public static void fileWriterAttribute(String accountnum, String name) {
        try {
            File file = new File(accountnum + "new");// heir pfart eintragen
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);

            if (!file.exists()) {
                file.createNewFile();

            } else {
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(creatSkeleton(name, accountnum));
                System.out.println("Done");
                bw.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String  readAccontData(String accountnum) {
        String name = accountnum + "new";
        FileReader filer = null;
        String data = "";
        try {
            filer = new FileReader(name);
            // heir vondaten bank aktuel konto stand holen
            int i;
            while ((i = filer.read()) != -1) {
                System.out.print((char) i);
                data = data + (char) i;
            }
            filer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void readAccontDataold(String accountnum)  {
        String name = accountnum + "old";
        FileReader filer = null;
        try {
            filer = new FileReader(name);
            // heir vondaten bank aktuel konto stand holen
            int i;
            while ((i = filer.read()) != -1) {
                System.out.print((char) i);
            }
            filer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void dataMoving(String accountnum, String name) {

        FileReader filer = null;
        try {
            filer = new FileReader(accountnum + "new");
            String text = "";
            BufferedReader br = new BufferedReader(filer);
            String[] text1 = new String[1024];
            boolean cont = true;

            FileWriter writer = new FileWriter(accountnum + "old", true);
            BufferedWriter bw = new BufferedWriter(writer);
            int i;
            while ((i = filer.read()) != -1) {
                text = text + (char) i;
            }
            writer.append("\n" + dateTime + "\n" + text);
            bw.close();
            writer.close();
            filer.close();
            FileWriter filew = new FileWriter(accountnum + "new");
            filew.write(creatSkeleton(name, accountnum));
            filew.flush();
            filew.close();
            System.out.println("end");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

