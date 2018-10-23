import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;


public class Main {

    public static void main(String[] args) {


        //Create string array for 1.2
        String[] strings = {"aaa", "bbb", "a", "bb"};

        //Create the list of persons for 1.4

        Person p1 = new Person("Ivan", "Draga", 41, new Laptop("MacBook", 1700));
        Person p2 = new Person("Stepan", "Praga", 16, new Laptop("MacBookPro", 2500));
        Person p3 = new Person("Oleg", "Braga", 41, new Laptop("MacBookMini", 1200));
        Person p4 = new Person("Bogdan", "Wlaga", 17, new Laptop("MacBook13", 1400));
        Person p5 = new Person("Roman", "Schpaga", 28, new Laptop("MacBook15", 1800));
        Person p6 = new Person("Feodor", "Saga", 14, new Laptop("MacBook35", 29000));
        Person p7 = new Person("Natalia", "Draga", 38, new Laptop("MacBookAple", 22500));

        List<Person> persons = new LinkedList<>();

        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);
        persons.add(p6);
        persons.add(p7);

//Create sort keys for 2.2 and 2.3
        Key keyBirthday = s -> Integer.parseInt(s.substring(s.indexOf(",")).substring(7)
                + s.substring(s.indexOf(",")).substring(4, 6)
                + s.substring(s.indexOf(",")).substring(1, 3));
        Key keyPrice = s -> Integer.parseInt(s.split(",")[s.split(",").length - 1]);

//2.3
        outputFromMapToFile(inputToSortedMap
                        ("inputBirthdayList.txt", keyBirthday),
                "outputBirthdayList.txt");
//2.2 prim (not nesessary task)
        outputFromMapToFile(inputToSortedMap
                        ("inputTest.csv", keyPrice),
                "outputGadgetPriceList.txt");
//1.1
        System.out.println(sumFromFile("inputNumbers.txt"));
//1.2
        System.out.println(sortedArray(strings));
//1.3
        myListPrinter(inputAndSortPersonByAge("inputTest.csv"));
//1.4
        outputFromSortedByAgeListToFile(persons, "output.csv");
//2.1.1
        //copyFileBytes("c:\\TEMP\\eclipse.zip", "c:\\TEMP2\\xxx.zip ");
//2.1.2
        //copyFileBlocksOfBytes("c:\\TEMP\\eclipse.zip", "c:\\TEMP2\\xxx1.zip ");
//2.1.3
        //copyFileWithAvailable("c:\\TEMP\\eclipse.zip", "c:\\TEMP2\\xxx2.zip ");
//2.2.
        inputToPersonForGadgets("inputTest.csv", "outputSortedBySumOfGadgets.csv");
    }

    //input from file to Map
    public static Map<Integer, String> inputToSortedMap(String fileName, Key key) {
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
        try (BufferedWriter os = new BufferedWriter(new FileWriter(fileName))) {
            for (String s : map.values()) {
                os.write(s);
                os.newLine();
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
                .sorted(Comparator.comparingInt(s -> s.length()))
                .collect(Collectors.toList());
    }

    //input from file to List of persons, age > 17
    public static List<Person> inputAndSortPersonByAge(String fileName) {
        List<Person> list = new LinkedList<>();
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            String currentLine;
            while ((currentLine = is.readLine()) != null) {
                if (stringToPerson(currentLine).getAge() > 17) {
                    list.add(stringToPerson(currentLine));
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

    //printer of List
    public static void myListPrinter(List<Person> person) {
        person.stream()
                .forEach(s -> System.out.println(s));
    }

    //output from sorted List
    public static void outputFromSortedByAgeListToFile(List<Person> list, String fileName) {
        try (BufferedWriter printer = new BufferedWriter(new FileWriter(fileName))) {
            Collections.sort(list, Comparator.comparingInt(Person::getAge));
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i).getFullName() + ", " + list.get(i).getAge();
                printer.write(s);
                printer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //copyFile with  bytes
    public static void copyFileBytes(String from, String to) {
        try (InputStream is = new FileInputStream(from);
             OutputStream os = new FileOutputStream(to)) {
            int readFile;
            while ((readFile = is.read()) != -1) {
                System.out.println("Availiable " + is.available());
                os.write(readFile);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //copyFile with blocks of bytes
    public static void copyFileBlocksOfBytes(String from, String to) {
        try (InputStream is = new FileInputStream(from);
             OutputStream os = new FileOutputStream(to)) {
            byte[] buffer = new byte[10000];
            while ((is.read(buffer)) != -1) {
                os.write(buffer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //copyFile with available()
    public static void copyFileWithAvailable(String from, String to) {
        try (InputStream is = new FileInputStream(from);
             OutputStream os = new FileOutputStream(to)) {
            byte[] buffer = new byte[256];
            while (is.available() != 0) {
                is.read(buffer);
                os.write(buffer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // sum gadget's prises for each person and sort by sum
    public static Map<String, Integer> inputToPersonForGadgets(String inputFileName, String outputFileName) {

        Map<String, Integer> collect = new HashMap<>();
        try (BufferedReader is = new BufferedReader(new FileReader(inputFileName));
             PrintWriter pw = new PrintWriter(outputFileName)) {
            collect = is.lines().map(s -> stringToPerson(s))
                    .collect(Collectors.groupingBy((p -> p.getFullName()),
                            Collectors.summingInt((p -> p.getLaptop().getPrice()))));

            Set<Entry<String, Integer>> entrySet = collect.entrySet();
            List<Entry<String, Integer>> entries = new ArrayList<>(entrySet);
            entries.sort(Entry.comparingByValue());
            entries.forEach((e) -> pw.write(((((e.getKey() + "," + e.getValue() + "\r\n"))))));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return collect;
    }

    //create an object Person from read String
    public static Person stringToPerson(String string) {
        String[] strings = string.split(",");
        Laptop laptop = new Laptop(strings[3], Integer.valueOf(strings[4]));
        Person person = new Person(strings[0], strings[1], Integer.valueOf(strings[2]), laptop);

        return person;
    }
}
