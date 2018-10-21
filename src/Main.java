import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {

//Create string array for 1.2
        String[] strings = {"aaa", "bbb", "a", "bb"};
//Create the list of persons for 1.4
        PersonForCSV p1 = new PersonForCSV("Ivan", "Draga", 41);
        PersonForCSV p2 = new PersonForCSV("Stepan", "Praga", 16);
        PersonForCSV p3 = new PersonForCSV("Oleg", "Braga", 41);
        PersonForCSV p4 = new PersonForCSV("Bogdan", "Wlaga", 17);
        PersonForCSV p5 = new PersonForCSV("Roman", "Schpaga", 28);
        PersonForCSV p6 = new PersonForCSV("Feodor", "Saga", 14);
        PersonForCSV p7 = new PersonForCSV("Natalia", "Draga", 38);

        List<PersonForCSV> persons = new LinkedList<>();

        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);
        persons.add(p6);
        persons.add(p7);


        try (InputStream is = new FileInputStream("inputBirthdayList.txt")) {
            int read;
            byte[] buffer = new byte[128];
            while ((read = is.read(buffer)) != -1) {
                System.out.println("Read bytes " + read);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//Create sort keys for 2.2 and 2.3
        Key keyBirthday = s -> Integer.parseInt(s.substring(s.indexOf(",")).substring(7)
                + s.substring(s.indexOf(",")).substring(4, 6)
                + s.substring(s.indexOf(",")).substring(1, 3));
        Key keyPrice = s -> Integer.parseInt(s.split(",")[s.split(",").length - 1]);

//2.3
        outputFromMapToFile(InputToSortedMap
                        ("inputBirthdayList.txt", keyBirthday),
                "outputBirthdayList.txt");
//2.2
        outputFromMapToFile(InputToSortedMap
                        ("inputGadgetPriceList.txt", keyPrice),
                "outputGadgetPriceList.txt");
//1.1
        System.out.println(sumFromFile("numbers.txt"));
//1.2
        System.out.println(sortedArray(strings));
//1.3
        myListPrinter(sortByAge("test.csv"));
//1.4
        outputFromSortedByAgeListToFile(persons,"output.csv");
    }

//input from file to Map
    public static Map<Integer, String> InputToSortedMap(String fileName, Key key) {
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

//output from Map to file
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
//input and sum
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
//sorting of array by Stream API
    public static List<String> sortedArray(String[] strings) {
        List<String> list = Arrays.asList(strings);
        return list
                .stream()
                .sorted(new StringByLengthComparator())
                .collect(Collectors.toList());

    }
//input from file to List of persons, age > 17
    public static List<Person> sortByAge(String fileName) {
        List<Person> list = new LinkedList<>();
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            String currentLine;
            while ((currentLine = is.readLine()) != null) {
                if (stringToPersonTransformer(currentLine).getAge() > 17) {
                    list.add(stringToPersonTransformer(currentLine));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list
                .stream()
                .sorted(Comparator.comparingInt(s -> s.getAge()))
                .collect(Collectors.toList());
    }
// transforming String from file to class Person

    public static Person stringToPersonTransformer(String string) {
        String name = string.split(",")[0];
        int age = Integer.parseInt(string.split(",")[1]);
        return new Person(name, age);
    }
//printer of List
    public static void myListPrinter(List<Person> person) {
        person.stream()
                .forEach(s -> System.out.println(s));
    }
//output from sorted List
    public static void outputFromSortedByAgeListToFile(List<PersonForCSV> list, String fileName) {
        try (BufferedWriter printer = new BufferedWriter(new FileWriter(fileName))) {
            Collections.sort(list,Comparator.comparingInt(PersonForCSV::getAge));
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i).getFirstName() + " "
                        + list.get(i).getSecondName()
                        + ", " + list.get(i).getAge();
                printer.write(s);
                printer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
