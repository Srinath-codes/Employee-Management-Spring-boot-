package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void saveStudent(Student student) {
        repository.save(student);
    }

    public Student getStudentById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteStudent(Integer id) {
        repository.deleteById(id);
    }
}



