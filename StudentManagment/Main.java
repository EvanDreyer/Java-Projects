// Importing all the Swing classes and other Java utilities we’ll use
// Swing is used for GUI (Graphical User Interface) components like buttons, labels, and tables
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

// Our main class extends JFrame, which basically means we’re creating a window
public class Main extends JFrame {

    // These are the GUI components that will appear in the window
    private JPanel MainPanel;        // The main panel that holds everything
    private JLabel Jlabel;           // A label for "Name"
    private JTextField Jname;        // A text field for entering a student's name
    private JButton Jadd;            // Button to add a student
    private JButton removeButton;    // Button to remove a student
    private JTable JTable;           // A table to display the list of students
    private JTextField JGrade;       // A text field for entering the student's grade

    // This ArrayList will store Student objects in memory
    private ArrayList<Student> studentList = new ArrayList<>();

    // This handles the data model for our JTable (like the backend of the table)
    private DefaultTableModel tableModel;

    // The constructor is where everything in the GUI is created and connected together
    public Main() {

        // Basic setup for the window
        setTitle("Student Management");             // Sets the window title
        setDefaultCloseOperation(EXIT_ON_CLOSE);    // Closes program when window is closed
        setSize(500, 500);                          // Sets window size
        setLocationRelativeTo(null);                // Centers the window on screen

        // Create the main panel that will hold all the GUI components
        MainPanel = new JPanel();
        MainPanel.setLayout(null); // Using null layout lets us manually position components with setBounds

        // Create and initialize the components
        Jlabel = new JLabel("Name:");    // Label to describe the text field
        Jname = new JTextField();// Text field to input student name
        JLabel gradeLabel = new JLabel ("Grade");
        Jadd = new JButton("Add");       // Button to add the student
        removeButton = new JButton("Remove"); // Button to remove a selected student
        JTable = new JTable();           // Table to display all students
        JGrade = new JTextField();       // Text field to input student grade

        // Set manual positions and sizes for each component
        // (x position, y position, width, height)
        Jlabel.setBounds(20, 20, 80, 25);
        gradeLabel.setBounds(20, 50, 80, 25);
        Jname.setBounds(100, 20, 150, 25);
        JGrade.setBounds(100, 50, 50, 25);
        Jadd.setBounds(260, 20, 80, 25);
        removeButton.setBounds(260, 50, 80, 25);
        JTable.setBounds(20, 80, 440, 300);

        // Add all the components to the main panel
        MainPanel.add(Jlabel);
        MainPanel.add(Jname);
        MainPanel.add(gradeLabel);
        MainPanel.add(JGrade);
        MainPanel.add(Jadd);
        MainPanel.add(removeButton);
        MainPanel.add(JTable);

        // Create the table model — this defines the structure of the table (columns, rows)
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");   // First column for names
        tableModel.addColumn("Grade");  // Second column for grades

        // Attach the table model to the JTable so they work together
        JTable.setModel(tableModel);

        // --- ADD BUTTON FUNCTIONALITY ---
        // When the "Add" button is clicked, we want to take the input from the text fields and add it to the table
        Jadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text typed into the input fields
                String name = Jname.getText();
                String grade = JGrade.getText();

                // Create a new Student object using that data
                Student student = new Student(name, grade);

                // Add that student to our ArrayList (so we have the data stored in memory)
                studentList.add(student);

                // Create a new table row (Vector is used by DefaultTableModel to store rows)
                Vector<String> row = new Vector<>();
                row.add(name);
                row.add(grade);

                // Add the row to the table model (which updates the JTable visually)
                tableModel.addRow(row);

                // Clear the input fields for the next entry
                Jname.setText("");
                JGrade.setText("");
            }
        });

        // --- REMOVE BUTTON FUNCTIONALITY ---
        // When "Remove" is clicked, it deletes the selected row from the table and list
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the currently selected row index from the JTable
                int selectedRow = JTable.getSelectedRow();

                // Check that a row is actually selected (-1 means nothing is selected)
                if (selectedRow != -1) {
                    // Remove that row visually from the table
                    tableModel.removeRow(selectedRow);

                    // Also remove that student from our ArrayList to keep data consistent
                    studentList.remove(selectedRow);
                }
            }
        });

        // Add the panel to the JFrame so it shows up in the window
        setContentPane(MainPanel);

        // Finally, make the window visible
        setVisible(true);
    }

    // This is the main method that launches the application
    public static void main(String[] args) {
        // SwingUtilities.invokeLater makes sure the GUI runs on the correct thread
        SwingUtilities.invokeLater(() -> new Main());
    }

    // --- STUDENT CLASS ---
    // This small class represents one student’s data (name + grade)
    private class Student {
        private String name;
        private String grade;

        // Constructor to create a new Student object
        public Student(String name, String grade) {
            this.name = name;
            this.grade = grade;
        }

        // Getters so we can access the student’s info later if needed
        public String getName() {
            return name;
        }

        public String getGrade() {
            return grade;
        }
    }
}
