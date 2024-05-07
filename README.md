# StudentManagement
The Student Management System allows users to perform basic CRUD (Create, Read, Update, Delete) operations on a student database. It provides a GUI interface where users can input student information such as name, age, gender, percentage, and attendance. Users can fetch all existing student records, insert new records, delete existing records by ID, and update existing records by ID

Technologies Used:
Java Swing: Java Swing is used for creating the graphical user interface (GUI) components like buttons, text fields, checkboxes, labels, etc. It provides a platform-independent way to build rich desktop applications in Java.

MySQL Database: MySQL is used as the backend database management system. It stores the student records and handles CRUD operations through JDBC (Java Database Connectivity). JDBC is a Java API for connecting and executing queries on a database.

JDBC (Java Database Connectivity): JDBC is used to connect Java applications with databases. It provides methods for executing SQL queries, fetching results, and managing database connections. In this project, JDBC is used to connect to the MySQL database and execute CRUD operations.

Features:
Insert: Allows users to insert new student records into the database by providing name, age, gender, percentage, and attendance.
Fetch: Retrieves all existing student records from the database and displays them in a JTextArea.
Delete: Allows users to delete a student record by specifying the ID of the student.
Update: Allows users to update an existing student record by specifying the ID and modifying the name, age, gender, percentage, or attendance.

How It Works:
The main class App extends JFrame and implements the ActionListener interface to handle button clicks.
GUI components like text fields, buttons, checkboxes, and labels are created using Java Swing and added to the frame.
Database connection parameters (URL, username, password) are defined.
ActionListener is implemented to handle button clicks (fetchButton, insertButton, deleteButton, updateButton).
Inside the actionPerformed method, CRUD operations are performed based on the button clicked.
SQL queries are executed using JDBC to interact with the MySQL database.
Exception handling is implemented to manage errors like SQL exceptions and input validation errors.
