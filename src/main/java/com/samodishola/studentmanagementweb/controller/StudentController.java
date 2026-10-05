package com.samodishola.studentmanagementweb.controller;

import com.samodishola.studentmanagementweb.entity.Student;
import com.samodishola.studentmanagementweb.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(Authentication authentication, Model model) {

        String matricNumber = authentication.getName();

        Student student = studentService.getStudentByMatricNumber(matricNumber);

        model.addAttribute("student", student);

        return "student-dashboard";
    }
}