package com.samodishola.studentmanagementweb.controller;

import com.samodishola.studentmanagementweb.entity.Student;
import com.samodishola.studentmanagementweb.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class ReportsController {

    private final StudentService studentService;

    public ReportsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/admin/reports")
    public String reports(Model model) {

        // Get all students
        List<Student> students = studentService.getAllStudents();

        // Total students
        long totalStudents = students.size();

        // Male students
        long maleStudents = students.stream()
                .filter(student ->
                        student.getGender() != null &&
                                student.getGender().equalsIgnoreCase("Male"))
                .count();

        // Female students
        long femaleStudents = students.stream()
                .filter(student ->
                        student.getGender() != null &&
                                student.getGender().equalsIgnoreCase("Female"))
                .count();

        // Calculate average GPA
        double averageGpa = students.stream()
                .filter(student -> student.getGpa() != null)
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);

        // Students by level
        long nd1Students = students.stream()
                .filter(student ->
                        "ND1".equalsIgnoreCase(student.getLevel()))
                .count();

        long nd2Students = students.stream()
                .filter(student ->
                        "ND2".equalsIgnoreCase(student.getLevel()))
                .count();

        long hnd1Students = students.stream()
                .filter(student ->
                        "HND1".equalsIgnoreCase(student.getLevel()))
                .count();

        long hnd2Students = students.stream()
                .filter(student ->
                        "HND2".equalsIgnoreCase(student.getLevel()))
                .count();

        // GPA categories
        long excellentStudents = students.stream()
                .filter(student ->
                        student.getGpa() != null &&
                                student.getGpa() >= 4.5)
                .count();

        long goodStudents = students.stream()
                .filter(student ->
                        student.getGpa() != null &&
                                student.getGpa() >= 3.5 &&
                                student.getGpa() < 4.5)
                .count();

        long averageStudents = students.stream()
                .filter(student ->
                        student.getGpa() != null &&
                                student.getGpa() >= 2.5 &&
                                student.getGpa() < 3.5)
                .count();

        long needsImprovementStudents = students.stream()
                .filter(student ->
                        student.getGpa() != null &&
                                student.getGpa() < 2.5)
                .count();

        // Get top 5 students by GPA
        List<Student> topStudents = students.stream()
                .filter(student -> student.getGpa() != null)
                .sorted(Comparator.comparing(
                        Student::getGpa,
                        Comparator.reverseOrder()))
                .limit(5)
                .toList();

        // Send data to the HTML page
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("maleStudents", maleStudents);
        model.addAttribute("femaleStudents", femaleStudents);
        model.addAttribute("averageGpa", String.format("%.2f", averageGpa));

        model.addAttribute("nd1Students", nd1Students);
        model.addAttribute("nd2Students", nd2Students);
        model.addAttribute("hnd1Students", hnd1Students);
        model.addAttribute("hnd2Students", hnd2Students);

        model.addAttribute("excellentStudents", excellentStudents);
        model.addAttribute("goodStudents", goodStudents);
        model.addAttribute("averageStudents", averageStudents);
        model.addAttribute("needsImprovementStudents", needsImprovementStudents);

        model.addAttribute("topStudents", topStudents);

        return "reports";
    }
}