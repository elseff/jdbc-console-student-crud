package com.elseff.project.modules.student;

import com.elseff.project.util.ConsoleColors;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import static com.elseff.project.modules.student.StudentQueries.*;

public class StudentService {
    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";

    public StudentService() {
        init();
    }

    private void init() {
        try {
            Instant start = Instant.now();
            ClassLoader classLoader = getClass().getClassLoader();
            java.net.URL resource = classLoader.getResource("student.yaml");
            File studentYamlFile = new File(Objects.requireNonNull(resource).toURI());
            InputStream inputStream = new FileInputStream(Objects.requireNonNull(studentYamlFile));
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            URL = (String) data.get("db_url");
            USER = (String) data.get("db_user");
            PASSWORD = (String) data.get("db_password");

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                ResultSet resultSet = connection.getMetaData()
                        .getTables("learn_jdbc", "public", "student", null);
                Statement statement = connection.createStatement();

                if (!resultSet.next()) {
                    statement.executeUpdate(CREATE_TABLE_STUDENT);
                    System.out.println(ConsoleColors.YELLOW_BOLD + "Created table --- student");
                    System.out.print(ConsoleColors.RESET);
                    Instant end = Instant.now();
                    Duration interval = Duration.between(start, end);
                    System.out.println("Initialisation in " + interval.toMillis() + "ms");
                }
            } catch (SQLException e) {
                System.out.print(ConsoleColors.RESET);
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
            System.exit(-1);
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public int insertIntoStudent(String name) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(String.format(INSERT_INTO_STUDENT, name));
        } catch (SQLException e) {
            System.out.print(ConsoleColors.RESET);
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public ResultSet selectFromStudent() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(SELECT_FROM_STUDENT);
        } catch (SQLException e) {
            System.out.print(ConsoleColors.RESET);
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ResultSet selectFromStudentById(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(String.format(SELECT_FROM_STUDENT_BY_ID, id));
        } catch (SQLException e) {
            System.out.print(ConsoleColors.RESET);
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int updateStudentName(Long studentId, String name) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(String.format(UPDATE_STUDENT_NAME, name, studentId));
        } catch (SQLException e) {
            System.out.print(ConsoleColors.RESET);
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int deleteFromStudent(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(String.format(DELETE_FROM_STUDENT, id));
        } catch (SQLException e) {
            System.out.print(ConsoleColors.RESET);
            System.err.println(e.getMessage());
            return -1;
        }
    }
}
