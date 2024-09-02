import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradingSystem extends JFrame {
    private JTextField nameField, classField, subjectsField, marksField;
    private JTextArea displayArea;

    public GradingSystem() {
        // Set up the frame
        setTitle("Student Grading System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Input fields
        add(new JLabel("Student Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Class:"));
        classField = new JTextField();
        add(classField);

        add(new JLabel("Total Subjects:"));
        subjectsField = new JTextField();
        add(subjectsField);

        add(new JLabel("Total Marks:"));
        marksField = new JTextField();
        add(marksField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new AddStudentListener());
        add(addButton);

        JButton displayButton = new JButton("Display Students");
        displayButton.addActionListener(new DisplayStudentsListener());
        add(displayButton);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea));
    }

    private class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String className = classField.getText();
            int totalSubjects = Integer.parseInt(subjectsField.getText());
            int totalMarks = Integer.parseInt(marksField.getText());

            try (Connection conn = Data.getConnection()) {
                String sql = "INSERT INTO students (name, class, total_subjects, total_marks) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, className);
                pstmt.setInt(3, totalSubjects);
                pstmt.setInt(4, totalMarks);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Student added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Could not add student.");
            }
        }
    }

    private class DisplayStudentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = Data.getConnection()) {
                String sql = "SELECT * FROM students";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                displayArea.setText("");
                while (rs.next()) {
                    displayArea.append("ID: " + rs.getInt("id") +
                            ", Name: " + rs.getString("name") +
                            ", Class: " + rs.getString("class") +
                            ", Subjects: " + rs.getInt("total_subjects") +
                            ", Marks: " + rs.getInt("total_marks") + "\n");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Could not retrieve students.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GradingSystem().setVisible(true);
        });
    }
}
