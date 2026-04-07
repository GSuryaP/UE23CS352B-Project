# Export Report Feature - User Guide

## Overview

The Expense Tracker now includes a powerful report export feature that allows you to generate and download your expense reports in PDF format. Two types of reports are available:

1. **Monthly Report** - Expenses grouped chronologically by month
2. **Category-wise Report** - Expenses grouped by spending category

---

## How to Access the Export Feature

### Step 1: Navigate to Summary Page
1. After logging in, click on **"Summary"** in the navigation bar
   - URL: `http://localhost:8080/summary`

### Step 2: View the Report Generation Section
You'll see a section titled **"Generate & Export Reports"** with two report type options

---

## Generating Reports

### Option A: Monthly Report

**What it shows:**
- Expenses organized by month/year (e.g., 2024-01, 2024-02)
- Detailed expense entries for each month
- Subtotal for each month
- Grand total at the bottom

**Steps:**
1. Click on the **"Monthly Report"** radio button
2. You'll see a message: "✓ Monthly Report selected. Click 'Export as PDF' to download..."
3. Click the **"Export as PDF"** button
4. The file `monthly_report.pdf` will be downloaded automatically

**Report includes:**
```
Monthly Expense Report
┌─────────────────────────────────────────┐
│ 2024-01 (Month)                         │
├─────────────────────────────────────────┤
│ Date   | Description | Category | Amount
│ 2024-01-15 | Groceries | Food | ₹500
│ 2024-01-20 | Gas | Transport | ₹300
│ Month Total: ₹800
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ 2024-02 (Month)                         │
├─────────────────────────────────────────┤
│ ...
│ Month Total: ₹1200
└─────────────────────────────────────────┘

Grand Total: ₹2000
```

### Option B: Category-wise Report

**What it shows:**
- Category summary table (count and total per category)
- Detailed expenses grouped by each category
- Total for each category
- Grand total at the bottom

**Steps:**
1. Click on the **"Category-wise Report"** radio button
2. You'll see a message: "✓ Category-wise Report selected. Click 'Export as PDF' to download..."
3. Click the **"Export as PDF"** button
4. The file `category_report.pdf` will be downloaded automatically

**Report includes:**
```
Category-wise Expense Report

Category Summary:
┌──────────────────────────────────┐
│ Category | Count | Total Amount  │
├──────────────────────────────────┤
│ Food     |   5   | ₹2500        │
│ Transport|   3   | ₹1200        │
│ Utilities|   2   | ₹800         │
└──────────────────────────────────┘

Detailed Expenses by Category:

Food (₹2500)
┌──────────────────────────────────┐
│ Date | Description | Amount      │
├──────────────────────────────────┤
│ 2024-01-15 | Groceries | ₹500   │
│ 2024-01-20 | Lunch | ₹300       │
│ ...
└──────────────────────────────────┘

Transport (₹1200)
...

Grand Total: ₹4500
```

---

## Changing Report Selection

If you want to change the report type:

1. Select a different report option (the UI will update automatically)
2. The message will change to reflect your new selection
3. All previous selections will be cleared when you refresh the page
4. Click **"Cancel"** button to hide the export options without saving

---

## PDF Report Contents

Each generated PDF report includes:

✅ **Header Information:**
- Report title (Monthly Report or Category-wise Report)
- Username of the logged-in user
- Generation date

✅ **Detailed Data:**
- Date of each transaction
- Description/purpose of expense
- Category of expense
- Amount spent (in ₹)
- Associated username

✅ **Summaries:**
- Subtotals (by month or category)
- Grand total of all expenses

✅ **Formatting:**
- Professional table layout
- Clear headers and separators
- Currency formatting (Indian Rupees ₹)

---

## Technical Details

### Report Generation Endpoints

| Report Type | Endpoint | Method | Response |
|------------|----------|--------|----------|
| Monthly | `/report/monthly` | GET | PDF file |
| Category-wise | `/report/category` | GET | PDF file |

### Requirements

- Must be logged in to access reports
- User will only see their own expenses (unless admin)
- Admin users can export all users' expenses
- Requires at least one expense entry to generate a report

### Supported Browsers

- Chrome ✅
- Firefox ✅
- Safari ✅
- Edge ✅

---

## Troubleshooting

### "Please select a report type first"
**Solution:** Click on one of the report type options before clicking Export

### PDF file doesn't download
**Solution:** 
1. Check browser's pop-up blocker settings
2. Allow downloads from localhost
3. Try a different browser

### Report shows no data
**Solution:**
1. Add at least one expense first (go to "Add Expense")
2. Ensure the expense date format is correct (YYYY-MM-DD)
3. Refresh the page and try again

### Slow PDF generation
**Solution:**
- This is normal for large reports (1000+ expenses)
- Please be patient while the PDF is being generated
- Check your internet connection stability

---

## Tips & Best Practices

💡 **Organize Your Expenses:**
- Use consistent category names for better reports
- Add meaningful descriptions to expenses
- Keep date entries accurate

💡 **Regular Exports:**
- Export monthly reports at the end of each month
- Use category reports to analyze spending patterns
- Keep PDF copies for records/tax purposes

💡 **Data Analysis:**
- Compare monthly reports to identify spending trends
- Use category reports to set budget limits
- Track which categories consume most of your budget

---

## Features of Report Generation

### Monthly Report Features
- ✅ Groups expenses by month automatically
- ✅ Shows chronological order (oldest to newest month)
- ✅ Calculates monthly subtotals
- ✅ Displays user information for shared accounts
- ✅ Professional PDF formatting

### Category-wise Report Features
- ✅ Summary table for quick overview
- ✅ Detailed breakdown per category
- ✅ Counts transactions per category
- ✅ Easy to identify high-spending categories
- ✅ Professional PDF formatting

---

## Implementation Details

### Technology Stack
- **PDF Generation**: iText 7 (Java PDF library)
- **Report Service**: Spring Service component
- **Data Source**: User's expense data from database
- **Format**: Standard PDF (compatible with all PDF readers)

### Architecture
```
Summary Page (UI)
    ↓
Select Report Type
    ↓
ReportService.generateMonthlyReport() OR generateCategoryReport()
    ↓
PDF Generation (iText)
    ↓
Downloaded as PDF File
```

---

## SOLID Principles in Report Implementation

The report export feature demonstrates key design principles:

🏗️ **Single Responsibility Principle (SRP)**
- ReportService handles only report generation
- ExpenseController handles only HTTP requests
- Each class has one reason to change

🔄 **Open/Closed Principle (OCP)**
- Easy to add new report types without modifying existing code
- Can extend with "Weekly Report" or "Yearly Report" later

🧩 **Dependency Inversion Principle (DIP)**
- Controller depends on ReportService abstraction
- Not directly on PDF generation implementation

---

## Future Enhancements

Potential features that could be added:

- 📊 Excel/CSV export format
- 📈 Graphical charts in PDF reports
- 📧 Email report directly
- 📱 Mobile-optimized reports
- 🎯 Custom date range filtering
- 💰 Budget comparison reports
- 📋 Saving report templates
- 📅 Scheduled automated reports

---

## Support

For issues or feature requests:
1. Check the troubleshooting section above
2. Verify all expenses are properly entered
3. Ensure you're logged in with correct user
4. Contact system administrator for technical support

---

**Last Updated:** April 2026
**Version:** 1.0
