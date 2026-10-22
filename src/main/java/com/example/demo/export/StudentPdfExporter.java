package com.example.demo.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.example.demo.model.Student;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class StudentPdfExporter {

    private List<Student> studentList;

    public StudentPdfExporter(List<Student> studentList) {
        this.studentList = studentList;
    }

    private void writeTable(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        cell.setPhrase(new Phrase("ID"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Name"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email"));
        table.addCell(cell);

        for (Student student : studentList) {
            table.addCell(String.valueOf(student.getId()));
            table.addCell(student.getName());
            table.addCell(student.getEmail());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);


        Paragraph p = new Paragraph("List of Employee", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        writeTable(table);

        document.add(table);
        document.close();
    }
}
