import java.util.ArrayList;

public class Table {
    /**
     * Each table can occupy one company and multiple students up to a maximum load.
     */
    public String company;
    private int maxLoad;
    public ArrayList<ArrayList<String>> students;

    /**
     * Constructor for table based on company name and maximum load.
     * Students are to be added to the instantiated array list.
     * @param company
     * @param maxLoad
     */
    public Table(String company, int maxLoad, int rotations) {
        this.company = company;
        this.maxLoad = maxLoad;
        this.students = new ArrayList<ArrayList<String>>(rotations);
        for (int i = 0; i < rotations; i++) {
            students.add(new ArrayList<String>());
        }
    }

    /**
     * Returns true if the table cannot accommodate more students during this rotation.
     * @param rotation
     * @return
     */
    public boolean isFull(int rotation) {
        return size(rotation) == maxLoad;
    }

    /**
     * Returns the number of students currently at table during this rotation.
     * @param rotation
     * @return
     */
    private int size(int rotation) {
        return students.get(rotation).size();
    }

    /**
     * Adds a student to the end of the list.
     * @param student
     */
    public void addStudent(String student, int rotation) {
        if (isFull(rotation)) {
            throw new ArrayIndexOutOfBoundsException("Table is already full.");
        }
        students.get(rotation).add(student);
    }

    public static void main(String[] args) {
        Table maxterial = new Table("maxterial", 3, 2);
        maxterial.addStudent("Alex", 0);
        maxterial.addStudent("Jessica", 0);
        maxterial.addStudent("Richard", 0);
        System.out.println(maxterial.isFull(0));
        System.out.println(maxterial.isFull(1));
        maxterial.addStudent("Michael", 1);
        maxterial.addStudent("Jingwei", 0);
    }
}
