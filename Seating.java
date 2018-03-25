import java.io.*;
import java.util.*;

public class Seating {
    /**
     * Main class for determining seating. Stores student preferences and instantiates
     * table array based on the number of rotations in event.
     *
     * Need to check seating afterwards: this program uses a stable-marriage-like algorithm
     * but has unbalanced preference lists; thus a perfect solution may not be possible.
     *
     * Example: If a student receives less companies than # of rotations, this indicates that
     * the student is one of the later signups. There may not be available seating solutions and
     * the user will need to manually adjust maximum load for tables or manipulate the final seating.
     */
    private ArrayList<Table> tables;
    private Map<String, Integer> companyToInt;
    private Map<Integer, String> intToCompany;
    private ArrayList<Student> students; //can make students a static variable
    private int rotations; //figure out how to do multiple rotations

    /**
     * Constructor for seating chart.
     * @param rotations
     */
    public Seating(int rotations) {
        this.rotations = rotations;
        this.tables = new ArrayList<>();
        this.students = new ArrayList<>();
        this.companyToInt = new HashMap<>();
        this.intToCompany = new HashMap<>();
    }

    /**
     * Constructs the tables based on maximum load of each company from a CSV file.
     * @param filename
     */
    public void readTables(String filename) {
        try {
            // Read csv file
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = "";

            // Skip first line
            br.readLine();

            // Reading from second line
            int companyCount = 0;
            while((line = br.readLine()) != null) {
                String[] companyDetails = line.split(",");
                intToCompany.put(companyCount, companyDetails[0]);
                companyToInt.put(companyDetails[0], companyCount);
                tables.add(new Table(companyDetails[0], Integer.parseInt(companyDetails[1]), rotations));
                companyCount++;
            }

        }
        catch(Exception ee) {
            ee.printStackTrace();
        }
    }

    /**
     * Constructs the students array based on student preferences from a CSVfile.
     * @param filename
     */
    public void readStudents(String filename) {
        try {
            // Read csv file
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = "";

            // Skip first line
            br.readLine();

            // Reading from second line
            while((line = br.readLine()) != null) {
                String[] studentDetails = line.split(",");
                LinkedList<String> companies = new LinkedList<>();
                Map<String, Integer> companyScores = new HashMap<>();
                for (int i = 1; i < studentDetails.length; i++) {
                    companies.add(studentDetails[i]);
                    companyScores.put(studentDetails[i], i);
                }
                Student student = new Student(studentDetails[0], companies, companyScores);
                students.add(student);
            }

        }
        catch(Exception ee) {
            ee.printStackTrace();
        }
    }

    /**
     * Does seating based on the preference lists of students and what seatings are available
     * at each rotation.
     */
    public void doSeating() {
        for (int rotation = 0; rotation < rotations; rotation++) {
            for (Student student : students) {
                boolean seated = false;
                int companyIndex = 0;
                while (!seated && companyIndex < student.companies.size()) {
                    String company = student.companies.get(companyIndex);
                    int companyInt = companyToInt.get(company);
                    if (!tables.get(companyInt).isFull(rotation)) {
                        seated = true;
                        tables.get(companyInt).addStudent(student.name, rotation);
                        student.addSeat(company);
                        student.companies.remove(companyIndex);
                    }
                    companyIndex++;
                }
            }
        }
    }

    /**
     * Prompts for user input and does everything.
     * Sample format for student file: SampleStudents.csv
     * Sample format for company file: SampleCompanies.csv
     * Sample seating output: SampleFinal.csv
     */

    public static Seating doEverything() {
        // Prompt for user input.
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the filename of students:");
        String studentfilename = reader.nextLine();
        System.out.println("Please enter the filename of companies:");
        String companyfilename = reader.nextLine();
        System.out.println("Please enter the filename I should write to:");
        String outputfilename = reader.nextLine();
        System.out.println("Please enter the number of rotations:");
        int rotations = reader.nextInt();
        reader.close();

        // Create seating object with provided info.
        Seating seating = new Seating(rotations);
        seating.readTables(companyfilename);
        seating.readStudents(studentfilename);

        // Make seating based on provided info.
        seating.doSeating();

        // Writes to CSV.
        seating.writeToCSV(outputfilename);
        return seating;
    }

    /**
     * Writes the seating arrangement as a CSV file. Seating is printed based on
     * student name and then by company name and rotation.
     */
    public void writeToCSV(String filename) {
        String comma = ",";
        String newLine = "\n";
        PrintWriter writer = null;
        try {
            // Student seating:
            writer = new PrintWriter(filename);
            writer.write("Student Name");
            writer.write(comma);
            for (int i = 1; i <= rotations; i++) {
                writer.write("Rotation" + " " + i);
                writer.write(comma);
            }
            writer.write(newLine);
            for (Student student : students) {
                writer.write(student.name);
                writer.write(comma);
                writer.write(student.seating.toString().replace("[", "")
                        .replace(", ", comma).replace("]", newLine));
            }

            // For each rotation, by table:
            for (int i = 1; i <= rotations; i++) {
                writer.write(newLine);
                writer.write(newLine);
                writer.write("Rotation" + " " + i);
                writer.write(newLine);
                for (Table table : tables) {
                    writer.write(table.company);
                    writer.write(comma);
                    writer.write(table.students.get(i - 1).toString().replace("[", "")
                            .replace(", ", comma).replace("]", newLine));
                }
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public static void main(String[] args) {
        Seating seating = doEverything();
        for (Student student : seating.students){
            System.out.println(student.name + "|" + student.seating + "|" + student.satisfaction);
        }
        for (Table table : seating.tables) {
            System.out.println(table.company + "|" + table.students);
        }
    }
}
