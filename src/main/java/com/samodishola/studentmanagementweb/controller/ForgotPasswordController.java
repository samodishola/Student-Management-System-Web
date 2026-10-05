package com.samodishola.studentmanagementweb.controller;

import com.samodishola.studentmanagementweb.entity.Student;
import com.samodishola.studentmanagementweb.entity.User;
import com.samodishola.studentmanagementweb.repository.StudentRepository;
import com.samodishola.studentmanagementweb.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForgotPasswordController {

    private final StudentRepository studentRepository;
    private final UserService userService;

    public ForgotPasswordController(StudentRepository studentRepository,
                                    UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String verifyAccount(
            @RequestParam String matricNumber,
            @RequestParam String email,
            HttpSession session,
            Model model) {

        Student student = studentRepository
                .findByMatricNumber(matricNumber)
                .orElse(null);

        User user = userService.findByUsername(matricNumber);

        if (student == null || user == null) {

            model.addAttribute("error",
                    "Student account not found.");

            return "forgot-password";
        }

        if (!student.getEmail().equalsIgnoreCase(email)) {

            model.addAttribute("error",
                    "The matric number and email address do not match.");

            return "forgot-password";
        }

        session.setAttribute("resetMatricNumber", matricNumber);

        return "redirect:/reset-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(HttpSession session) {

        if (session.getAttribute("resetMatricNumber") == null) {
            return "redirect:/forgot-password";
        }

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String password,
            @RequestParam String confirmPassword,
            HttpSession session,
            Model model) {

        String matricNumber =
                (String) session.getAttribute("resetMatricNumber");

        if (matricNumber == null) {
            return "redirect:/forgot-password";
        }

        if (!password.equals(confirmPassword)) {

            model.addAttribute("error",
                    "Passwords do not match.");

            return "reset-password";
        }

        User user = userService.findByUsername(matricNumber);

        if (user == null) {

            model.addAttribute("error",
                    "Student account could not be found.");

            return "reset-password";
        }

        userService.updatePassword(matricNumber, password);

        session.removeAttribute("resetMatricNumber");

        return "redirect:/login?reset";
    }
}