package com.samodishola.studentmanagementweb.controller;

import com.samodishola.studentmanagementweb.entity.Student;
import com.samodishola.studentmanagementweb.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final StudentService studentService;

    public HomeController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("totalStudents", studentService.getTotalStudents());
        model.addAttribute("maleStudents", studentService.getMaleStudents());
        model.addAttribute("femaleStudents", studentService.getFemaleStudents());

        return "index";
    }

    @GetMapping("/add-student")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/save-student")
    public String saveStudent(@ModelAttribute Student student, Model model) {

        if (studentService.matricNumberExists(student.getMatricNumber(), student.getId())) {

            model.addAttribute("student", student);

            model.addAttribute("errorMessage",
                    "Matric Number " + student.getMatricNumber()
                            + " already exists. Please enter a different matric number.");

            return "add-student";
        }

        studentService.saveStudent(student);

        return "redirect:/students";
    }
    @GetMapping("/students")
    public String showStudents(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("students", studentService.searchStudents(keyword));
        } else {
            model.addAttribute("students", studentService.getAllStudents());
        }

        model.addAttribute("keyword", keyword);

        return "students";
    }

    @GetMapping("/edit-student/{id}")
    public String editStudent(@PathVariable Long id, Model model) {

        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);

        return "add-student";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return "redirect:/students";
    }

    @GetMapping("/view-student/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {

        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);

        return "view-student";
    }

}