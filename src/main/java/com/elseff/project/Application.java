package com.elseff.project;

import com.elseff.project.modules.student.StudentService;
import com.elseff.project.util.ConsoleColors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    private static final StudentService studentService = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.print(ConsoleColors.GREEN_BOLD);
            System.out.println("Actions:");
            System.out.println("1. Insert into student");
            System.out.println("2. Select from student");
            System.out.println("3. Update student name");
            System.out.println("4. Delete from student");
            System.out.println("-1. Exit");
            System.out.print("Select an action: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print(ConsoleColors.BLUE_BOLD);
                    System.out.println("Insert into student...");
                    System.out.print("Write student name: ");
                    String name = scanner.next();
                    int insertIntoStudent = studentService.insertIntoStudent(name);
                    if (insertIntoStudent > 0) {
                        System.out.println("Successful!");
                    } else {
                        System.out.println("Something wrong...");
                    }
                    System.out.println(ConsoleColors.RESET);
                }
                case 2 -> {
                    System.out.print(ConsoleColors.CYAN_BOLD);
                    System.out.println("--- STUDENT ---");
                    ResultSet resultSet = studentService.selectFromStudent();
                    try {
                        boolean hasNext = false;
                        while (resultSet.next()) {
                            System.out.println("| " + resultSet.getInt(1) + " | " + resultSet.getString(2) + " |");
                            hasNext = true;
                        }
                        if (!hasNext) {
                            System.out.println("Table student is empty...");
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(ConsoleColors.RESET);
                }
                case 3 -> {
                    System.out.print(ConsoleColors.PURPLE_BOLD);
                    System.out.println("Updating student name");
                    ResultSet resultSet = studentService.selectFromStudent();
                    try {
                        boolean hasNext = false;
                        while (resultSet.next()) {
                            System.out.println("| " + resultSet.getInt(1) + " | " + resultSet.getString(2) + " |");
                            hasNext = true;
                        }
                        if (!hasNext) {
                            System.out.println("Table student is empty...");
                        } else {
                            System.out.println("Select student: ");
                            Long studentId = scanner.nextLong();

                            ResultSet studentByIdRS = studentService.selectFromStudentById(studentId);
                            String studentName = null;
                            while (studentByIdRS.next()) {
                                studentName = studentByIdRS.getString(2);
                            }
                            if (studentName != null) {
                                System.out.println("Write new name");
                                String updatedName = scanner.next();

                                int i = studentService.updateStudentName(studentId, updatedName);
                                if (i > 0) {
                                    System.out.println("Successful!");
                                } else {
                                    System.out.println("Something wrong...");
                                }
                            } else {
                                System.out.println("Could now find student with id: " + studentId);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Delete from student");
                    ResultSet resultSet = studentService.selectFromStudent();
                    try {
                        boolean hasNext = false;
                        while (resultSet.next()) {
                            System.out.println("| " + resultSet.getInt(1) + " | " + resultSet.getString(2) + " |");
                            hasNext = true;
                        }
                        if (!hasNext) {
                            System.out.println("Table student is empty...");
                        } else {
                            System.out.print("Write student id: ");
                            long id = scanner.nextLong();

                            ResultSet studentByIdResultSet = studentService.selectFromStudentById(id);
                            String studentName = null;
                            while (studentByIdResultSet.next()) {
                                studentName = studentByIdResultSet.getString(2);
                            }
                            if (studentName != null) {
                                int deleteFromStudent = studentService.deleteFromStudent(id);
                                if (deleteFromStudent > 0) {
                                    System.out.println("Successful!");
                                } else {
                                    System.out.println("Something wrong...");
                                }
                            } else {
                                System.out.println("Could now find student with id: " + id);
                            }
                        }
                        System.out.println(ConsoleColors.RESET);
                    } catch (SQLException e) {
                        System.out.print(ConsoleColors.RESET);
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        while (choice != -1);

        System.out.print(ConsoleColors.RESET + ConsoleColors.RED);
        System.out.println("Exit");
    }
}
