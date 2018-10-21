import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {


        String [] strings = {"aaa","bbb","a","bb"};

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


        outputFromMapToFile(InputToSortedMapByBirthday
                        ("inputBirthdayList.txt", keyBirthday),
                "outputBirthdayList.txt");

        outputFromMapToFile(InputToSortedMapByBirthday
                        ("inputGadgetPriceList.txt", keyPrice),
                "outputGadgetPriceList.txt");

        System.out.println(sumFromFile("numbers.txt"));

        System.out.println(sortedArray(strings));
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


    public static void outputFromMapToFile(Map<Integer, String> map, String fileName) {
        try (BufferedWriter printer = new BufferedWriter(new FileWriter(fileName))) {
            for (String s : map.values()) {
                printer.write(s);
                printer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Integer sumFromFile(String fileName) {
        Integer sum = 0;
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            String read;
            while ((read = is.readLine()) != null) {
                sum += Integer.parseInt(read);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sum;
    }
    public static List<String> sortedArray (String [] strings){
        List<String> list = Arrays.asList(strings);

        return  list
                .stream()
                .sorted(new StringByLengthComparator())
                .collect(Collectors.toList());

    }


}
