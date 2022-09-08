package com.volvo.project.components.datatdriventesting;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Iterator;

public class ExcelLibrary {

    public String readFromExcel(final int rowNum, final int colNum, String excelName) {
        Workbook wb = null;
        Object value = null;

        //1. Open the file
        try {
            wb = WorkbookFactory.create(new File("src/test/resources/" + excelName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2. Open a sheet
        Sheet sheet = wb.getSheetAt(0);

        //3. Get each cell by row & column number
        Cell cell;
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);

            try {
                value = cell.getStringCellValue();
                if (value != null) {
                    return value.toString();
                }
            } catch (IllegalStateException e) {
            }
            try {
                value = cell.getNumericCellValue();
                String s=value.toString();
                s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                if (s != null) {
                    return s;
                }
            } catch (IllegalStateException e) {
            }

            try {
                return value.toString();
            } catch (NullPointerException e) {
            }
            //        TestBase.logger.info("Row: 0 - Column: 0 = "+value);
        } catch (NullPointerException e) {
            return null;
        }
        return value.toString();
    }

    public int getRowCount(String excelName) {
        int rowCnt = 0;
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/"+excelName);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheetAt(0);
            rowCnt = s.getLastRowNum();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowCnt;
    }

    public void writeToExcel(String fiePath, int rowNum,
                             int cellNum, String data) {
        try {
            FileInputStream fis = new FileInputStream(fiePath);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheetAt(0);
            Row r = s.getRow(rowNum);
            Cell c = r.createCell(cellNum);
            c.setCellValue(data);
            FileOutputStream fos = new FileOutputStream(fiePath);
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object[][] readFromExcelTab(String excelName, String tabName) {
        String path = "/testdata/"+excelName;
        Workbook wb = null;
        //1. Open the file
        try {
            wb = WorkbookFactory.create(new File("src/test/resources" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2. Open a sheet
        Sheet sheet = wb.getSheet(tabName);
        int rows = sheet.getLastRowNum();
//        int rows = getRowCount(path)
        int cols = sheet.getRow(rows).getPhysicalNumberOfCells();
        Object[][] array = new Object[rows][cols];
        int counter = 0;
        Iterator<Row> rowIterator = sheet.iterator();

        //skip headers in document
        Row row = rowIterator.next();
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
//            int y = 0;
            for (int y=0; y < cols;) {
                while (cellIterator.hasNext()) {
                    Object value = null;
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            value = Math.round(Float.parseFloat(String.valueOf(Double.valueOf(cell.getNumericCellValue()))));
                            value.toString();
                            //(digdecimal)
                            break;
                        case STRING:
                            value = String.valueOf(cell.getStringCellValue().toString());
                            break;
                        case BLANK:
                            value = String.valueOf(cell.getStringCellValue().toString());
                            break;
                    }
                    /*if(cellIterator.hasNext())
                        {
                            Cell cell2 = cellIterator.next();
                            switch(cell.getCellType())
                                {
                                    case Cell.CELL_TYPE_NUMERIC:
                                        value2 = Integer.valueOf(cell2.getStringCellValue()).toString();
                                        break;
                                    case Cell.CELL_TYPE_STRING:
                                        value2 = String.valueOf(cell2.getStringCellValue());
                                        break;
                                }
                        }
                        */


                    if (value != null) {
                        array[counter][y] = value;
                        y++;
                    }
                }
            }
            counter++;
        }
        return array;
    }

    public Object[][] readFromExcel(String excelName) {
        String path = "/testdata/"+excelName;
        Workbook wb = null;
        Object value = null;
        //1. Open the file
        try {
            wb = WorkbookFactory.create(new File("src/test/resources" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2. Open a sheet
        Sheet sheet = wb.getSheetAt(0);
//        int rows = sheet.getLastRowNum();
        int rows = getRowCount(path);
        int cols = sheet.getRow(rows).getPhysicalNumberOfCells();
        Object[][] array = new Object[rows][cols];
        int counter = 0;
        Iterator<Row> rowIterator = sheet.iterator();

        //skip headers in document
        Row row = rowIterator.next();
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            for (int y = 0; y < cols; ) {
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            value = Integer.valueOf(cell.getStringCellValue()).toString();
                            break;
                        case STRING:
                            value = String.valueOf(cell.getStringCellValue().toString());
                            break;
                    }
                    /*if(cellIterator.hasNext())
                        {
                            Cell cell2 = cellIterator.next();
                            switch(cell.getCellType())
                                {
                                    case Cell.CELL_TYPE_NUMERIC:
                                        value2 = Integer.valueOf(cell2.getStringCellValue()).toString();
                                        break;
                                    case Cell.CELL_TYPE_STRING:
                                        value2 = String.valueOf(cell2.getStringCellValue());
                                        break;
                                }
                        }
                        */


                    if (value != null) {
                        array[counter][y] = value;
                        y++;
                    } else {
                        y++;
                    }
                }
            }
            counter++;
        }
        return array;
    }
    public Object[][] readFromExcelTabWithHeaders(String excelName, String tabName) {
        String path = "/testdata/"+excelName;
        Workbook wb = null;
        //1. Open the file
        try {
            wb = WorkbookFactory.create(new File("src/test/resources" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2. Open a sheet
        Sheet sheet = wb.getSheet(tabName);
        int rows = sheet.getLastRowNum();
//            int rows = getRowCount(path)
        int cols = sheet.getRow(rows).getPhysicalNumberOfCells();
        Object[][] array = new Object[rows+1][cols];
        int counter = 0;
        Iterator<Row> rowIterator = sheet.iterator();

        //skip headers in document
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
//                int y = 0;
            for (int y=0; y < cols;) {
                while (cellIterator.hasNext()) {
                    Object value = null;
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            value = Math.round(Float.parseFloat(String.valueOf(Double.valueOf(cell.getNumericCellValue()))));
                            value.toString();
                            //(digdecimal)
                            break;
                        case STRING:
                            value = String.valueOf(cell.getStringCellValue().toString());
                            break;
                        case BLANK:
                            value = String.valueOf(cell.getStringCellValue().toString());
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                    }
                        /*if(cellIterator.hasNext())
                            {
                                Cell cell2 = cellIterator.next();
                                switch(cell.getCellType())
                                    {
                                        case Cell.CELL_TYPE_NUMERIC:
                                            value2 = Integer.valueOf(cell2.getStringCellValue()).toString();
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            value2 = String.valueOf(cell2.getStringCellValue());
                                            break;
                                    }
                            }
                            */


                    if (value != null) {
                        array[counter][y] = value;
                        y++;
                    }
                }
            }
            counter++;
        }
        return array;
    }


}