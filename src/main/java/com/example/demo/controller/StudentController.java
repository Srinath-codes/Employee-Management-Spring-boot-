package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.export.StudentExcelExporter;
import com.example.demo.export.StudentPdfExporter;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String viewHome(Model model) {
        model.addAttribute("students", service.getAllStudents());
        return "index";
    }

    @GetMapping("/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add_student";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        service.saveStudent(student);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Integer id, Model model) {
        Student student = service.getStudentById(id);
        model.addAttribute("student", student);
        return "edit_student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Integer id, @ModelAttribute Student student) {
        student.setId(id);
        service.saveStudent(student);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        service.deleteStudent(id);
        return "redirect:/";
    }
@GetMapping("/students/export/excel")
public void exportToExcel(HttpServletResponse response) throws IOException {

    response.setContentType("application/octet-stream");
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=students.xlsx";
    response.setHeader(headerKey, headerValue);

    List<Student> listStudents = service.getAllStudents(); // use 'service' here
    StudentExcelExporter exporter = new StudentExcelExporter(listStudents);
    exporter.export(response);
}
@GetMapping("/students/export/pdf")
public void exportToPdf(HttpServletResponse response) throws IOException, DocumentException {
    response.setContentType("application/pdf");
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=students.pdf";
    response.setHeader(headerKey, headerValue);

    List<Student> listStudents = service.getAllStudents();
    StudentPdfExporter exporter = new StudentPdfExporter(listStudents);
    exporter.export(response);
}
@PostMapping("/delete-multiple")
public String deleteMultiple(@RequestParam(value = "selectedIds", required = false) List<Integer> selectedIds) {
    if (selectedIds != null) {
        for (Integer id : selectedIds) {
            service.deleteStudent(id);
        }
    }
    return "redirect:/";
}


}

