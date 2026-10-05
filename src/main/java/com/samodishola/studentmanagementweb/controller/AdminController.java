package com.samodishola.studentmanagementweb.controller;

import com.samodishola.studentmanagementweb.entity.Student;
import com.samodishola.studentmanagementweb.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final StudentService studentService;

    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        // Get all students
        List<Student> students = studentService.getAllStudents();

        // Basic statistics
        long totalStudents = studentService.getTotalStudents();
        long maleStudents = studentService.getMaleStudents();
        long femaleStudents = studentService.getFemaleStudents();

        // Calculate average GPA
        double averageGpa = students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);

        // Add statistics to the dashboard
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("maleStudents", maleStudents);
        model.addAttribute("femaleStudents", femaleStudents);
        model.addAttribute("averageGpa", averageGpa);

        // Add student records
        model.addAttribute("students", students);

        return "admin-dashboard";
    }
}