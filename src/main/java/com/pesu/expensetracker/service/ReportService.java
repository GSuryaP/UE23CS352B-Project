package com.pesu.expensetracker.service;

import com.pesu.expensetracker.model.Expense;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    /**
     * Generate a monthly expense report PDF
     * Strategy Pattern: Different report generation strategies
     */
    public byte[] generateMonthlyReport(List<Expense> expenses, String userName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Title
            Paragraph title = new Paragraph("Monthly Expense Report")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();
            document.add(title);

            // User and Date Info
            Paragraph userInfo = new Paragraph("User: " + userName + " | Generated: " + LocalDate.now())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);
            document.add(userInfo);
            document.add(new Paragraph("\n"));

            // Group expenses by month
            Map<YearMonth, List<Expense>> expensesByMonth = expenses.stream()
                    .collect(Collectors.groupingBy(expense -> {
                        String[] parts = expense.getDate().split("-");
                        return YearMonth.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    }));

            // Create table for each month
            for (YearMonth month : expensesByMonth.keySet().stream()
                    .sorted()
                    .collect(Collectors.toList())) {
                
                Paragraph monthHeader = new Paragraph(month.toString())
                        .setFontSize(14)
                        .setBold();
                document.add(monthHeader);

                List<Expense> monthExpenses = expensesByMonth.get(month);
                double monthTotal = monthExpenses.stream()
                        .mapToDouble(Expense::getAmount)
                        .sum();

                Table table = new Table(UnitValue.createPercentArray(new float[]{2, 3, 2, 2, 2}));
                table.setWidth(UnitValue.createPercentValue(100));

                // Table headers
                table.addCell(createHeaderCell("Date"));
                table.addCell(createHeaderCell("Description"));
                table.addCell(createHeaderCell("Category"));
                table.addCell(createHeaderCell("Amount"));
                table.addCell(createHeaderCell("User"));

                // Add rows
                for (Expense expense : monthExpenses) {
                    table.addCell(new Cell().add(new Paragraph(expense.getDate())));
                    table.addCell(new Cell().add(new Paragraph(expense.getDescription())));
                    table.addCell(new Cell().add(new Paragraph(expense.getCategory())));
                    table.addCell(new Cell().add(new Paragraph(String.format("₹%.2f", expense.getAmount()))));
                    table.addCell(new Cell().add(new Paragraph(expense.getUser().getUsername())));
                }

                document.add(table);

                // Month total
                Paragraph monthTotalPara = new Paragraph("Month Total: ₹" + String.format("%.2f", monthTotal))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setBold()
                        .setFontSize(12);
                document.add(monthTotalPara);
                document.add(new Paragraph("\n"));
            }

            // Overall total
            double grandTotal = expenses.stream()
                    .mapToDouble(Expense::getAmount)
                    .sum();
            Paragraph grandTotalPara = new Paragraph("Grand Total: ₹" + String.format("%.2f", grandTotal))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold()
                    .setFontSize(14);
            document.add(grandTotalPara);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generate a category-wise expense report PDF
     * Strategy Pattern: Different report generation strategies
     */
    public byte[] generateCategoryReport(List<Expense> expenses, String userName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Title
            Paragraph title = new Paragraph("Category-wise Expense Report")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();
            document.add(title);

            // User and Date Info
            Paragraph userInfo = new Paragraph("User: " + userName + " | Generated: " + LocalDate.now())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);
            document.add(userInfo);
            document.add(new Paragraph("\n"));

            // Group expenses by category
            Map<String, List<Expense>> expensesByCategory = expenses.stream()
                    .collect(Collectors.groupingBy(Expense::getCategory));

            // Create summary table (category totals)
            Paragraph summaryHeader = new Paragraph("Category Summary")
                    .setFontSize(14)
                    .setBold();
            document.add(summaryHeader);

            Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2}));
            summaryTable.setWidth(UnitValue.createPercentValue(100));

            summaryTable.addCell(createHeaderCell("Category"));
            summaryTable.addCell(createHeaderCell("Count"));
            summaryTable.addCell(createHeaderCell("Total Amount"));

            Map<String, Double> categoryTotals = new TreeMap<>();
            for (String category : expensesByCategory.keySet()) {
                List<Expense> categoryExpenses = expensesByCategory.get(category);
                double categoryTotal = categoryExpenses.stream()
                        .mapToDouble(Expense::getAmount)
                        .sum();
                categoryTotals.put(category, categoryTotal);

                summaryTable.addCell(new Cell().add(new Paragraph(category)));
                summaryTable.addCell(new Cell().add(new Paragraph(String.valueOf(categoryExpenses.size()))));
                summaryTable.addCell(new Cell().add(new Paragraph(String.format("₹%.2f", categoryTotal))));
            }

            document.add(summaryTable);
            document.add(new Paragraph("\n"));

            // Detailed expenses per category
            Paragraph detailHeader = new Paragraph("Detailed Expenses by Category")
                    .setFontSize(14)
                    .setBold();
            document.add(detailHeader);

            for (String category : expensesByCategory.keySet().stream()
                    .sorted()
                    .collect(Collectors.toList())) {
                
                List<Expense> categoryExpenses = expensesByCategory.get(category);
                double categoryTotal = categoryExpenses.stream()
                        .mapToDouble(Expense::getAmount)
                        .sum();

                Paragraph categoryHeader = new Paragraph(category + " (₹" + String.format("%.2f", categoryTotal) + ")")
                        .setFontSize(12)
                        .setBold()
                        .setMarginLeft(10);
                document.add(categoryHeader);

                Table table = new Table(UnitValue.createPercentArray(new float[]{2, 3, 2, 2}));
                table.setWidth(UnitValue.createPercentValue(100));
                table.setMarginLeft(10);

                table.addCell(createHeaderCell("Date"));
                table.addCell(createHeaderCell("Description"));
                table.addCell(createHeaderCell("Amount"));
                table.addCell(createHeaderCell("User"));

                for (Expense expense : categoryExpenses) {
                    table.addCell(new Cell().add(new Paragraph(expense.getDate())));
                    table.addCell(new Cell().add(new Paragraph(expense.getDescription())));
                    table.addCell(new Cell().add(new Paragraph(String.format("₹%.2f", expense.getAmount()))));
                    table.addCell(new Cell().add(new Paragraph(expense.getUser().getUsername())));
                }

                document.add(table);
                document.add(new Paragraph("\n"));
            }

            // Overall total
            double grandTotal = expenses.stream()
                    .mapToDouble(Expense::getAmount)
                    .sum();
            Paragraph grandTotalPara = new Paragraph("Grand Total: ₹" + String.format("%.2f", grandTotal))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold()
                    .setFontSize(14);
            document.add(grandTotalPara);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Helper method to create header cells
     */
    private Cell createHeaderCell(String content) {
        Cell cell = new Cell();
        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        cell.add(new Paragraph(content).setBold());
        return cell;
    }
}
