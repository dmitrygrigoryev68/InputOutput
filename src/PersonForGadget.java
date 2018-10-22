public class PersonForGadget {

    private String firstName;
    private String secondName;
    private Laptop laptop;

    public PersonForGadget(String firstName, String secondName, Laptop laptop) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.laptop = laptop;
    }

    public String getFullName() {
        return firstName + " " + secondName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    @Override
    public String toString() {
        return "PersonForGadget{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", laptop=" + laptop +
                '}';
    }
}