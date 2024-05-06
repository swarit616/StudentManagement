package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class App extends JFrame implements ActionListener {
    private JTextField nameField, ageField, percentageField, attendanceField, deleteIdField, updateIdField;
    private JCheckBox maleCheckBox, femaleCheckBox;
    private JButton fetchButton, insertButton, deleteButton, updateButton;
    private JTextArea displayArea;

    public App() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        // Labels and text fields
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        maleCheckBox = new JCheckBox("Male");
        femaleCheckBox = new JCheckBox("Female");
        genderPanel.add(maleCheckBox);
        genderPanel.add(femaleCheckBox);
        inputPanel.add(genderPanel);

        inputPanel.add(new JLabel("Percentage:"));
        percentageField = new JTextField();
        inputPanel.add(percentageField);

        inputPanel.add(new JLabel("Attendance:"));
        attendanceField = new JTextField();
        inputPanel.add(attendanceField);

        inputPanel.add(new JLabel("Delete by ID:"));
        deleteIdField = new JTextField();
        inputPanel.add(deleteIdField);

        inputPanel.add(new JLabel("Update by ID:"));
        updateIdField = new JTextField();
        inputPanel.add(updateIdField);

        // Buttons
        fetchButton = new JButton("Fetch");
        fetchButton.addActionListener(this);
        inputPanel.add(fetchButton);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(this);
        inputPanel.add(insertButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        inputPanel.add(deleteButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        inputPanel.add(updateButton);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String url = "jdbc:mysql://localhost:3306/student_db";
        String username = "root";
        String password = "root";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            if (e.getSource() == fetchButton) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
                displayArea.setText(""); // Clear previous data
                while (resultSet.next()) {
                    displayArea.append("ID: " + resultSet.getInt("id") +
                            ", Name: " + resultSet.getString("name") +
                            ", Age: " + resultSet.getInt("age") +
                            ", Gender: " + resultSet.getString("gender") +
                            ", Percentage: " + resultSet.getDouble("percentage") +
                            ", Attendance: " + resultSet.getDouble("attendance") + "\n");
                }
            } else if (e.getSource() == insertButton) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String gender = (maleCheckBox.isSelected()) ? "Male" : "Female";
                double percentage = Double.parseDouble(percentageField.getText());
                double attendance = Double.parseDouble(attendanceField.getText());

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO students (name, age, gender, percentage, attendance) VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, gender);
                preparedStatement.setDouble(4, percentage);
                preparedStatement.setDouble(5, attendance);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "A new student was inserted successfully!");
                }
            } else if (e.getSource() == deleteButton) {
                int idToDelete = Integer.parseInt(deleteIdField.getText());
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM students WHERE id = ?");
                preparedStatement.setInt(1, idToDelete);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Student with ID " + idToDelete + " was deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No student found with ID " + idToDelete);
                }
            } else if (e.getSource() == updateButton) {
                int idToUpdate = Integer.parseInt(updateIdField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String gender = (maleCheckBox.isSelected()) ? "Male" : "Female";
                double percentage = Double.parseDouble(percentageField.getText());
                double attendance = Double.parseDouble(attendanceField.getText());

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE students SET name=?, age=?, gender=?, percentage=?, attendance=? WHERE id=?");
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, gender);
                preparedStatement.setDouble(4, percentage);
                preparedStatement.setDouble(5, attendance);
                preparedStatement.setInt(6, idToUpdate);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Student with ID " + idToUpdate + " was updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No student found with ID " + idToUpdate);
                }
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
