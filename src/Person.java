public class Person {

    private String firstName;
    private String secondName;
    private int age;
    private Laptop laptop;

    public Person(String firstName, String secondName, int age, Laptop laptop) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.laptop = laptop;
    }

    public String getFullName() {
        return firstName + " " + secondName;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Name - " + getFullName()  + ", age: " + age;
    }
}