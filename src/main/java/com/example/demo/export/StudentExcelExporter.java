package com.example.demo.export;

import java.io.IOException;
import java.util.List;  // ✅ Fix for List

import jakarta.servlet.http.HttpServletResponse;

import com.example.demo.model.Student;  // Make sure Student is in this package


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StudentExcelExporter {

    private List<Student> studentList;

    public StudentExcelExporter(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void export(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // Header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Email");

        // Data rows
        int rowIndex = 1;
        for (Student s : studentList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(s.getId());
            row.createCell(1).setCellValue(s.getName());
            row.createCell(2).setCellValue(s.getEmail());
        }

        // Auto-size columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        // Write to response
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
