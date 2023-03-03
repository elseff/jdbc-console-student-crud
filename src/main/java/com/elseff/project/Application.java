package com.elseff.project;

import com.elseff.project.modules.model.Student;
import com.elseff.project.modules.student.StudentService;
import com.elseff.project.util.ConsoleColors;

import java.util.List;
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
                    Student student = new Student(name);
                    Student insertIntoStudent = studentService.insertIntoStudent(student);
                    if (insertIntoStudent != null)
                        System.out.println("Successful!");
                    else
                        System.out.println("Something wrong...");

                    System.out.println(ConsoleColors.RESET);
                }
                case 2 -> {
                    System.out.print(ConsoleColors.CYAN_BOLD);
                    System.out.println("--- STUDENT ---");
                    List<Student> students = studentService.selectFromStudent();
                    if (students.size() == 0) {
                        System.out.println("Table student is empty...");
                    } else {
                        students.forEach(s -> System.out.println("| " + s.getId() + " | " + s.getName() + " |"));
                    }
                    System.out.println(ConsoleColors.RESET);
                }
                case 3 -> {
                    System.out.print(ConsoleColors.PURPLE_BOLD);
                    System.out.println("Updating student name");
                    List<Student> students = studentService.selectFromStudent();
                    if (students.size() == 0) {
                        System.out.println("Table student is empty...");
                    } else {
                        students.forEach(s -> System.out.println("| " + s.getId() + " | " + s.getName() + " |"));
                        System.out.println("Select student: ");
                        Long studentId = scanner.nextLong();

                        Student student = studentService.selectFromStudentById(studentId);
                        if (student != null) {
                            System.out.println("Write new name");
                            String updatedName = scanner.next();

                            Student updatedStudent = studentService.updateStudentName(studentId, updatedName);
                            if (updatedStudent != null) {
                                System.out.println("Successful!");
                            } else {
                                System.out.println("Something wrong...");
                            }
                        } else {
                            System.out.println("Could now find student with id: " + studentId);
                        }
                    }
                }
                case 4 -> {
                    System.out.print(ConsoleColors.RED);
                    System.out.println("Delete from student");
                    List<Student> students = studentService.selectFromStudent();
                    if (students.size() == 0) {
                        System.out.println("Table student is empty...");
                    } else {
                        students.forEach(s -> System.out.println("| " + s.getId() + " | " + s.getName() + " |"));
                        System.out.print("Write student id: ");
                        long id = scanner.nextLong();

                        Student student = studentService.selectFromStudentById(id);

                        if (student != null) {
                            boolean deleteFromStudent = studentService.deleteFromStudent(id);
                            if (deleteFromStudent) {
                                System.out.println("Successful!");
                            } else {
                                System.out.println("Something wrong...");
                            }
                        } else {
                            System.out.println("Could now find student with id: " + id);
                        }
                    }
                    System.out.println(ConsoleColors.RESET);
                }
            }
        }
        while (choice != -1);

        System.out.print(ConsoleColors.RESET + ConsoleColors.RED);
        System.out.println("Exit");
    }
}
