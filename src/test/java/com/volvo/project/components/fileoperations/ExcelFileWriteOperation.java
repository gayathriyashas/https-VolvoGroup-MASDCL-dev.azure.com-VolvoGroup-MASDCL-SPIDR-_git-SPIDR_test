package com.volvo.project.components.fileoperations;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.poi.ss.usermodel.Cell.*;


public class ExcelFileWriteOperation {
	private static final Logger log = LoggerFactory.getLogger(ExcelFileWriteOperation.class);
	public FileInputStream fis = null;
	public FileOutputStream fos = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;
	String xlFilePath;

	public ExcelFileWriteOperation(String xlFilePath) throws Exception {
		this.xlFilePath = xlFilePath;
		fis = new FileInputStream(xlFilePath);
		workbook = new XSSFWorkbook(fis);
		//workbook = new HSSFWorkbook(fis);
		fis.close();
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String value) throws InterruptedException, IOException {
		//Thread.sleep(10000);
		try {
			int col_Num = -1;
			sheet = workbook.getSheet(sheetName);
			System.out.println("Sheet:" + sheet);
			System.out.println("sheetName:" + sheetName);

			row = sheet.getRow(0);
			System.out.println("row:" + row);

			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
					System.out.println("colName: " + colName);
					col_Num = i;
					System.out.println("col_Num: " + col_Num);
				}
			}

			sheet.autoSizeColumn(col_Num);
			row = sheet.getRow(rowNum - 1);
			System.out.println("row: " + row);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(col_Num);
			System.out.println("cell: " + cell);

			if (cell == null) {
				cell = row.createCell(col_Num);
			}
			String cellContents = cell.getStringCellValue();
			System.out.println("cellContents: " + cellContents);
			cell.setCellValue(cellContents);
			value = String.valueOf(cell.getStringCellValue().toString());
			cell.setCellValue(value);
			System.out.println("value: " + value);

			System.out.println("xlFilePath: " + xlFilePath);
			fos = new FileOutputStream(xlFilePath);
			//fos = new FileOutputStream(new File(xlFilePath));
			//fos = new FileOutputStream(new File("C:\\Java Framework\\output\\2.xlsx"));
			System.out.println("fos: " + fos);
			workbook.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		System.out.println("xlFilePath:"+xlFilePath);
		saveExcelFile(xlFilePath);
		return true;
	}

	public void updateExistingRow(String filePath, String sheetName, String[] dataToWrite) throws IOException, InterruptedException {

		//Create an object of File class to open xlsx file

		File file = new File(filePath);
		//String fileExtensionName = filePath.substring(filePath.indexOf("."));

		//Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = null;

		//Find the file extension by splitting  file name in substring and getting only extension name

		String fileExtensionName = filePath.substring(filePath.indexOf("."));

		//Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			//If it is xlsx file then create object of XSSFWorkbook class

			wb = new XSSFWorkbook(inputStream);

		}

		//Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			//If it is xls file then create object of XSSFWorkbook class

			wb = new HSSFWorkbook(inputStream);

		}

		//Read excel sheet by sheet name

		Sheet sheet = wb.getSheet(sheetName);

		//Get the current count of rows in excel file

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		//Get the first row from the sheet

		Row row = sheet.getRow(0);

		//Create a new row and append it at last of sheet

		Row newRow = sheet.createRow(rowCount - 100);

		//Create a loop over the cell of newly created Row

		for (int j = 0; j < row.getLastCellNum(); j++) {

			//Fill data in row
			//String cellContents = cell.getStringCellValue();
			//System.out.println("cellContents: "+cellContents);
			//cell.setCellValue(cellContents);

			Cell cell = newRow.createCell(j);
			//Cell cell = sheet.getRow(4).getCell(2);
			cell.setCellValue(cell.getStringCellValue());
			cell.setCellValue(dataToWrite[j].toString());

			//Cell cell= sheet.getRow(10).getCell(10);
			//cell.setCellValue(value);
			break;
		}

		//Close input stream

		inputStream.close();

		//Create an object of FileOutputStream class to create write data in excel file

		FileOutputStream outputStream = new FileOutputStream(file);

		//write data in the excel file

		wb.write(outputStream);

		//close output stream

		outputStream.close();
		saveExcelFile(xlFilePath);
	}

	public boolean updateExistingCellData(String sheetName, String colName, int rowNum, String value) throws InterruptedException {
		//Thread.sleep(10000);
		try {
			int col_Num = -1;
			sheet = workbook.getSheet(sheetName);
			//FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			System.out.println("Sheet:" + sheet);
			System.out.println("sheetName:" + sheetName);

			row = sheet.getRow(0);
			System.out.println("row:" + row);

			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
					System.out.println("colName: " + colName);
					col_Num = i;
					System.out.println("col_Num: " + col_Num);

				}
			}

			sheet.autoSizeColumn(col_Num);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(col_Num);
			System.out.println("cell: " + cell);

			if (cell == null) {
				cell = row.createCell(col_Num);
			}
			/*
			String cellContents = cell.getStringCellValue();
			System.out.println("cellContents: "+cellContents);
			cell.setCellValue(cellContents);
			//cell.setCellType(CellType.STRING);
			cell.setCellValue(value.toString());
			System.out.println("value: "+value);
			 */
			//if(cell!=null)
			//{

			DataValidation dataValidation = null;
			DataValidationConstraint constraint = null;
			DataValidationHelper validationHelper = null;

			validationHelper = new XSSFDataValidationHelper(sheet);
			CellRangeAddressList addressList = new CellRangeAddressList(0, 0, 0, 0);
			constraint = validationHelper.createExplicitListConstraint(new String[]{"PDC2", "PDC1", "MAYBE"});
			dataValidation = validationHelper.createValidation(constraint, addressList);
			dataValidation.setSuppressDropDownArrow(true);
			sheet.addValidationData(dataValidation);


				/*if (cell != null)
					//cell.setCellType(CellType.STRING);
					//cell.setCellValue(value.toString());
					System.out.println("value: "+value);
					cell.setCellValue(cell.getStringCellValue().toString());
					cell.setCellValue(value);
				*/
			cell.setCellValue(value.toString());
			System.out.println("value: " + value);
				/* Not working
				DataFormatter formatter = new DataFormatter();
				value = formatter.formatCellValue(cell);
				cell.setCellValue(value.toString());
				*/
			//}

			fos = new FileOutputStream(xlFilePath);
			System.out.println("fos: " + fos);
			workbook.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void saveExcelFile(String filePath) throws IOException,InterruptedException{
		System.out.println("filePath is : "+filePath);
		try {
			//open file
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(filePath)));

			//FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);

			//save file
			System.out.println("Saved filePath : "+filePath);
			FileOutputStream out = new FileOutputStream(new File("C:\\Java Framework\\output\\2.xlsx"));
			wb.write(out);
			out.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
