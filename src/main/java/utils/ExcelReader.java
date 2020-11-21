package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public String path;
    public FileInputStream fis;
    public FileOutputStream fileOut;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;

    protected static final int NUM2 = 2;

    public ExcelReader(String path) {

        this.path = path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    //Pdfs flow
    public String getFirstCellData(String sheetName, String rowValue) {

        try {
            
            int index = workbook.getSheetIndex(sheetName);
            int rowNum = 0;
            int colNum = 0;
            if (index == -1) {
                return "";
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum);
            for(colNum = 0 ; colNum < 200 ; colNum++){
            if (row.getCell(colNum).getStringCellValue().contains(rowValue)){
                return row.getCell(colNum).getStringCellValue();
            }
            }
            if (row == null) {
                return "";
            }
            
            if (cell == null) {
                return "";
            }
            return row.getCell(colNum).getStringCellValue();
        } catch (Exception e) {

            return "column "+rowValue+" does not exist  in xls";
        }
        
    }
    /**
     * Returns true if column is created successfully
     */
    public boolean addColumn(String sheetName, String colName) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return false;
            }
            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);
            if (row == null) {
                row = sheet.createRow(0);
            }
            if (row.getLastCellNum() == -1) {
                cell = row.createCell(0);
            } else {
                cell = row.createCell(row.getLastCellNum());
            }
            cell.setCellValue(colName);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @String sheetName, String testCaseName,String keyword ,String URL,String message
     */
    public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
            String message) {

        url = url.replace('\\', '/');
        if (!isSheetExist(sheetName)) {
            return false;
        }

        sheet = workbook.getSheet(sheetName);

        for (int i = NUM2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
                setCellData(sheetName, screenShotColName, i + index, message, url);
                break;
            }
        }
        return true;
    }

    /**
     * Returns true if sheet is created successfully else false
     */
    public boolean addSheet(String sheetname) {

        try {
            workbook.createSheet(sheetname);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * returns the data from a cell
     */
    public String getCellData(String sheetName, int colNum, int rowNum) {

        try {
            if (rowNum <= 0) {
                return "";
            }
            int index = workbook.getSheetIndex(sheetName);

            if (index == -1) {
                return "";
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                return "";
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                return "";
            }
            if (cell.getCellTypeEnum() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(NUM2);
                    cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

                }

                return cellText;
            } else if (cell.getCellTypeEnum() == CellType.BLANK) {
                return "";
            } else {
                return String.valueOf(cell.getBooleanCellValue());
            }
        } catch (Exception e) {

            return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
        }
    }

    /**
     * returns the data from a cell
     */
    public String getCellData(String sheetName, String colName, int rowNum) {

        try {
            if (rowNum <= 0) {
                return "";
            }
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return "";
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            int colNum = -1;
            for (int i = 0; i < row.getLastCellNum(); i++) {

                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                    colNum = i;
                }
            }
            if (colNum == -1) {
                return "";
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                return "";
            }
            cell = row.getCell(colNum);

            if (cell == null) {
                return "";
            }
            if (cell.getCellTypeEnum() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(NUM2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

                }

                return cellText;
            } else if (cell.getCellTypeEnum() == CellType.BLANK) {
                return "";
            } else {
                return String.valueOf(cell.getBooleanCellValue());
            }
        } catch (Exception e) {

            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }

    }

    /**
     * This method will return the content of a cell
     */
    public int findRow(String sheetName, String cellContent) {
        for (int i = 1; i <= getRowCount(sheetName); i++) {
            int colNum = 0;
            String value = getCellData(sheetName, colNum, i);
            if (value.equals(cellContent)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Returns the cell value with cell value
     */
    public String getCellDataValue(String sheetName, String colName, String cellValue) {
        int rowNum = findRow(sheetName, cellValue);
        try {
            if (rowNum <= 0) {
                return "";
            }
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return "";
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            int colNum = -1;
            for (int i = 0; i < row.getLastCellNum(); i++) {

                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                    colNum = i;
                }
            }
            if (colNum == -1) {
                return "";
            }

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                return "";
            }
            cell = row.getCell(colNum);

            if (cell == null) {
                return "";
            }
            if (cell.getCellTypeEnum() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(NUM2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

                }

                return cellText;
            } else if (cell.getCellTypeEnum() == CellType.BLANK) {
                return "";
            } else {
                return String.valueOf(cell.getBooleanCellValue());
            }
        } catch (Exception e) {

            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }

    /**
     * This method will return the row number of a cell
     */
    public int getCellRowNum(String sheetName, String colName, String cellValue) {

        for (int i = NUM2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                return i;
            }
        }
        return -1;

    }

    /**
     * Returns number of columns in a sheet
     */
    public int getColumnCount(String sheetName) {

        // check if sheet exists
        if (!isSheetExist(sheetName)) {
            return -1;
        }
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);

        if (row == null) {
            return -1;
        }
        return row.getLastCellNum();

    }

    /**
     * returns the row count in a sheet
     */
    public int getRowCount(String sheetName) {

        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            return 0;
        } else {
            sheet = workbook.getSheetAt(index);
            return sheet.getLastRowNum() + 1;
        }

    }

    // find whether sheets exists
    public boolean isSheetExist(String sheetName) {

        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workbook.getSheetIndex(sheetName.toUpperCase(Locale.ENGLISH));
            if (index == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * removes a column and all the contents
     */
    public boolean removeColumn(String sheetName, int colNum) {

        try {
            if (!isSheetExist(sheetName)) {
                return false;
            }
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            for (int i = 0; i < getRowCount(sheetName); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    cell = row.getCell(colNum);
                    if (cell != null) {
                        row.removeCell(cell);
                    }
                }
            }
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    /**
     * returns true if sheet is removed successfully else false if sheet does not exist
     */
    public boolean removeSheet(String sheetName) {

        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            return false;
        }

        try {
            workbook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Return the cell data
     */
    public boolean setCellData(String sheetName, int colNum, int rowNum, String data) {

        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0) {
                return false;
            }

            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return false;
            }

            sheet = workbook.getSheetAt(index);

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                row = sheet.createRow(rowNum - 1);
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * returns true if data is set successfully else false
     */
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {

        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0) {
                return false;
            }
            int index = workbook.getSheetIndex(sheetName);

            if (index == -1) {
                return false;
            }
            sheet = workbook.getSheetAt(index);
            int colNum = -1;
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
                    colNum = i;
                }
            }
            if (colNum == -1) {
                return false;
            }
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                row = sheet.createRow(rowNum - 1);
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * returns true if data is set successfully else false
     */
    public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {

        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0) {
                return false;
            }
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return false;
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            int colNum = -1;
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName)) {
                    colNum = i;
                }
            }

            if (colNum == -1) {
                return false;
            }
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                row = sheet.createRow(rowNum - 1);
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(data);

            // cell style for hyperlinks
            // by default hypelrinks are blue and underlined
            CellStyle hlinkStyle = workbook.createCellStyle();
            XSSFFont hlinkFont = workbook.createFont();
            hlinkFont.setUnderline(Font.U_SINGLE);
            hlinkFont.setColor(IndexedColors.BLUE.getIndex());
            hlinkStyle.setFont(hlinkFont);

            cell.setCellStyle(hlinkStyle);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public String getCellData(String sheetName, String columnName, Row row) {
        String cellText = null;
        if (cell.getCellTypeEnum() == CellType.STRING) {
            cellText = cell.getStringCellValue();
            return cellText;
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
            cellText = String.valueOf(cell.getNumericCellValue());
            if (DateUtil.isCellDateFormatted(cell)) {
                // format in form of M/D/YY
                double d = cell.getNumericCellValue();

                Calendar cal = Calendar.getInstance();
                cal.setTime(DateUtil.getJavaDate(d));
                cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(NUM2);
                cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
            }
            return cellText;
        } else if (cell.getCellTypeEnum() == CellType.BLANK) {
            return "";
        } else {
            return String.valueOf(cell.getBooleanCellValue());
        }
    }


}
