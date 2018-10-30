import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

//2.3
        personByBirthday("inputBirthdayList.txt",
                "outputBirthdayList.txt");
//1.1
        System.out.println(sumFromFile("inputNumbers.txt"));
//1.2
        System.out.println(sortedArray(strings));
//1.3
        inputAndSortPersonByAge("inputTest.csv");
//1.4
        outputFromSortedByAgeListToFile(persons, "output.csv");
//2.1.1
        //copyFileBytes("c:\\TEMP\\eclipse.zip", "c:\\TEMP2\\xxx.zip ");
//2.1.2
        //copyFileBlocksOfBytes("c:\\TEMP\\eclipse.zip", "c:\\TEMP2\\xxx1.zip ");
//2.1.3
        //copyFileWithAvailable("c:\\TEMP\\eclipse.zip", "c:\\TEMP2\\xxx2.zip ");
//2.2.
        personForGadgets("inputTest.csv",
                "outputSortedBySumOfGadgets.csv");

         System.out.println(diffDatesFromFile("dates.txt"));
    }

    //input from file to Map
    public static Map<Integer, String> personByBirthday(String inputFileName, String outputFileName) {
        Map<Integer, String> collect = new HashMap<>();
        try (BufferedReader is = new BufferedReader(new FileReader(inputFileName));
             PrintWriter pw = new PrintWriter(outputFileName)) {
            is.lines()
                    .collect(Collectors.groupingBy(s -> dateTransformer(s.split(",")[1])))
                    .entrySet()
                    .stream()
                    .sorted(Entry.comparingByKey())
                    .forEach((e) -> pw.write(e.getValue() + "\r\n"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return collect;
    }

    //input and sum
    public static Integer sumFromFile(String fileName) {
        Integer sum = 0;
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            sum = is.lines().collect(Collectors.summingInt(Integer::valueOf));
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
    public static void inputAndSortPersonByAge(String fileName) {
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            is.lines()
                    .map(s -> stringToPerson(s))
                    .collect(Collectors.groupingBy((p -> p.getAge()),
                            Collectors.groupingBy((p -> p.getFullName()))))
                    .entrySet()
                    .stream()
                    .sorted(Entry.comparingByKey())
                    .filter(s->s.getKey()>17)
                    .forEach((e) -> System.out.println(e.getValue().values()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //output from sorted List
    public static void outputFromSortedByAgeListToFile(List<Person> list, String fileName) {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            list
                    .stream()
                    .sorted(Comparator.comparingInt(Person::getAge))
                    .forEach((e) -> pw.write(e.getFullName() + e.getAge() + "\r\n"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // sum gadget's prises for each person and sort by sum
    public static Map<String, Integer> personForGadgets(String inputFileName,
                                                        String outputFileName) {
        Map<String, Integer> collect = new HashMap<>();
        try (BufferedReader is = new BufferedReader(new FileReader(inputFileName));
             PrintWriter pw = new PrintWriter(outputFileName)) {
            is
                    .lines()
                    .map(s -> stringToPerson(s))
                    .collect(Collectors.groupingBy((p -> p.getFullName()), Collectors.summingInt((p -> p.getLaptop().getPrice()))))
                    .entrySet()
                    .stream().sorted(Entry.comparingByValue())
                    .forEach((e) -> pw.write(e.getKey() + "," + e.getValue() + "\r\n"));

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

    //Date reverse function
    public static Date dateTransformer(String string) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy",Locale.GERMANY);
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

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
    public static Integer diffFromFile(String fileName) {
        Integer diff = 0;
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            List<Integer> list = is
                    .lines()
                    .map(i -> Integer.valueOf(i))
                    .sorted()
                    .collect(Collectors.toList());
            diff = list.get(list.size()-1) - list.get(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return diff;
    }
    public static Long diffDatesFromFile(String fileName) {
        Long diff = Long.valueOf(0);
        try (BufferedReader is = new BufferedReader(new FileReader(fileName))) {
            List<LocalDate> list = is
                    .lines()
                    .map(s->LocalDate.parse(s))
                    .sorted()
                    .collect(Collectors.toList());
            diff = ChronoUnit.DAYS.between(list.get(0),list.get(list.size()-1));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return diff;
    }
}
