import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Student {
    /**
     * Students initially submit preference lists of companies they would like to sit with.
     * Seating is determined by a first-come-first-serve basis; satisfaction tracks the overall
     * satisfaction with the seating, where a low score is more preferable.
     */
    public String name;
    public LinkedList<String> companies;
    public Map<String, Integer> companyScores;
    public ArrayList<String> seating;
    public int satisfaction;

    /**
     * Constructor for student includes all but the final seating and satisfaction,
     * which are assigned based on the other information.
     * @param student
     * @param companies
     * @param companyScores
     */
    public Student(String student, LinkedList<String> companies, Map<String, Integer> companyScores) {
        this.name = student;
        this.companies = companies;
        this.companyScores = companyScores;
        this.seating = new ArrayList<>();
        this.satisfaction = 0;
    }

    /**
     * Adds this company to the final seating of the student. Out of bounds errors should be
     * controlled by the looping for each rotation in Seating.java.
     * @param company
     */
    public void addSeat(String company) {
        this.seating.add(company);
        this.satisfaction += companyScores.get(company);
    }
}
