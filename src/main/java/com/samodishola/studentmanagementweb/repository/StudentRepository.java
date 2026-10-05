package com.samodishola.studentmanagementweb.repository;

import com.samodishola.studentmanagementweb.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByMatricNumberContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String matricNumber,
            String firstName,
            String lastName
    );

    boolean existsByMatricNumber(String matricNumber);
    Optional<Student> findByMatricNumber(String matricNumber);

    boolean existsByMatricNumberAndIdNot(String matricNumber, Long id);

    long countByGenderIgnoreCase(String gender);
}