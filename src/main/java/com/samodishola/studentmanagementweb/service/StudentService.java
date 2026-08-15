package com.samodishola.studentmanagementweb.service;

import com.samodishola.studentmanagementweb.entity.Student;
import com.samodishola.studentmanagementweb.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Save Student
    public Student saveStudent(Student student) {

        if (studentRepository.existsByMatricNumberAndIdNot(
                student.getMatricNumber(),
                student.getId())) {

            throw new IllegalArgumentException(
                    "Matric number already exists!"
            );
        }

        return studentRepository.save(student);
    }

    public boolean matricNumberExists(String matricNumber, Long id) {

        if (id == null) {
            return studentRepository.existsByMatricNumber(matricNumber);
        }

        return studentRepository.existsByMatricNumberAndIdNot(matricNumber, id);
    }

    // Get All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }

    public long getMaleStudents() {
        return studentRepository.countByGenderIgnoreCase("Male");
    }

    public long getFemaleStudents() {
        return studentRepository.countByGenderIgnoreCase("Female");
    }

    // Search Students
    public List<Student> searchStudents(String keyword) {

        return studentRepository
                .findByMatricNumberContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                );
    }

    // Get Student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Delete Student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}