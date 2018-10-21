import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        try (InputStream is = new FileInputStream("inputBirthdayList.txt")) {
            int read;
            byte[] buffer = new byte[128];
            while ((read = is.read(buffer)) != -1) {
                System.out.println("Read bytes " + read);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Key keyBirthday = s -> Integer.parseInt(s.substring(s.indexOf(",")).substring(7)
                + s.substring(s.indexOf(",")).substring(4, 6)
                + s.substring(s.indexOf(",")).substring(1, 3));
        Key keyPrice = s -> Integer.parseInt(s.split(",")[s.split(",").length - 1]);


        outputFromMapToFile(InputToSortedMapByBirthday("inputbirthdaylist.txt",keyBirthday));

        outputFromMapToFile(InputToSortedMapByBirthday("gadgets.txt",keyPrice));
}


    public static Map<Integer, String> InputToSortedMapByBirthday(String fileName, Key key) {
        Map<Integer, String> map = new TreeMap<>();
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            String currentLine;
            while ((currentLine = is.readLine()) != null) {
                map.put(key.key(currentLine), currentLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return map;
    }


    public static void outputFromMapToFile(Map<Integer, String> map) {
        try (BufferedWriter printer = new BufferedWriter(new FileWriter("outputgadgets.txt"))) {
            for (String s : map.values()) {
                printer.write(s);
                printer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
