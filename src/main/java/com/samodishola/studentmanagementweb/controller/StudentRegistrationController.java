package com.samodishola.studentmanagementweb.controller;

import com.samodishola.studentmanagementweb.entity.Student;
import com.samodishola.studentmanagementweb.repository.StudentRepository;
import com.samodishola.studentmanagementweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentRegistrationController {

    private final StudentRepository studentRepository;
    private final UserService userService;

    public StudentRegistrationController(StudentRepository studentRepository,
                                         UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    @GetMapping("/student/register")
    public String showRegistrationPage() {
        return "student-register";
    }

    @PostMapping("/student/register")
    public String registerStudent(
            @RequestParam String matricNumber,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String gender,
            @RequestParam Integer age,
            @RequestParam String dateOfBirth,
            @RequestParam String department,
            @RequestParam String faculty,
            @RequestParam String level,
            @RequestParam(required = false) Double gpa,
            @RequestParam String phoneNumber,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        // Check password confirmation
        if (!password.equals(confirmPassword)) {

            model.addAttribute("error",
                    "Passwords do not match.");

            return "student-register";
        }

        // Check if a user account already exists
        if (userService.findByUsername(matricNumber) != null) {

            model.addAttribute("error",
                    "An account already exists for this matric number. Please login.");

            return "student-register";
        }

        // Check whether the student already exists
        Student student = studentRepository
                .findByMatricNumber(matricNumber)
                .orElse(null);

        // If student does not exist, create a new student record
        if (student == null) {

            student = new Student();

            student.setMatricNumber(matricNumber);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setGender(gender);
            student.setAge(age);
            student.setDateOfBirth(dateOfBirth);
            student.setDepartment(department);
            student.setFaculty(faculty);
            student.setLevel(level);
            student.setGpa(gpa);
            student.setPhoneNumber(phoneNumber);
            student.setEmail(email);
            student.setAddress(address);

            studentRepository.save(student);
        }

        // Create the login account
        userService.createUser(
                matricNumber,
                password,
                "STUDENT",
                matricNumber
        );

        return "redirect:/login?registered";
    }
}