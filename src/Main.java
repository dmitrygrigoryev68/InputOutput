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

        outputFromMapToFile(InputToSortedMapByBirthday("inputbirthdaylist.txt"));
    }

    public static Map<Integer, String> InputToSortedMapByBirthday(String fileName) {
        Map<Integer, String> map = new TreeMap<>();
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            String currentLine;
            while ((currentLine = is.readLine()) != null) {
                map.put(bithDayTransformer(currentLine), currentLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public static Integer bithDayTransformer(String string) {
        String date = string.substring(string.indexOf(","));
        return Integer.parseInt(date.substring(7) + date.substring(4, 6) + date.substring(1, 3));
    }

    public static Integer priceTransformer(String string) {
        String [] price = string.split(",");
        return Integer.parseInt(price[price.length-1]);
    }

    public static void outputFromMapToFile(Map<Integer, String> map) {
        try (BufferedWriter printer = new BufferedWriter(new FileWriter("outputbirthday.txt"))) {
            for (String s : map.values()) {
                printer.write(s);
                printer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
