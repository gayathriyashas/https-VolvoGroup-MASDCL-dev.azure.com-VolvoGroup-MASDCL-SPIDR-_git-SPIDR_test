package com.volvo.project.components.fileoperations;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.*;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;


public class ExcelFileOperation {
	private static final Logger log = LoggerFactory.getLogger(ExcelFileOperation.class);

	public void readExcel(String filePath,String fileName,String sheetName) throws IOException {

		//Create an object of File class to open xlsx file

		File file = new File(filePath + "\\" + fileName);

		//File file = new File(filePath);

		//Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = null;

		//Find the file extension by splitting file name in substring  and getting only extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		//Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			//If it is xlsx file then create object of XSSFWorkbook class

			wb = new XSSFWorkbook(inputStream);

		}

		//Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			//If it is xls file then create object of HSSFWorkbook class

			wb = new HSSFWorkbook(inputStream);

		}

		//Read sheet inside the workbook by its name

		Sheet ws = wb.getSheet(sheetName);

		//Find number of rows in excel file

		int rowCount = ws.getLastRowNum() - ws.getFirstRowNum();

		//Create a loop over all the rows of excel file to read it

		for (int i = 0; i < rowCount + 1; i++) {

			Row row = ws.getRow(i);

			//Create a loop to print cell values in a row

			for (int j = 0; j < row.getLastCellNum(); j++) {

				//Print Excel data in console

				System.out.print(row.getCell(j).getStringCellValue() + "|| ");

			}
			System.out.println();
		}
	  }

	public void writeExcel_ByNewRow(String filePath,String sheetName,String[] dataToWrite) throws IOException{

		//Create an object of File class to open xlsx file

		File file =    new File(filePath);
		//String fileExtensionName = filePath.substring(filePath.indexOf("."));

		//Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = null;

		//Find the file extension by splitting  file name in substring and getting only extension name

		String fileExtensionName = filePath.substring(filePath.indexOf("."));

		//Check condition if the file is xlsx file

		if(fileExtensionName.equals(".xlsx")){

			//If it is xlsx file then create object of XSSFWorkbook class

			wb = new XSSFWorkbook(inputStream);

		}

		//Check condition if the file is xls file

		else if(fileExtensionName.equals(".xls")){

			//If it is xls file then create object of XSSFWorkbook class

			wb = new HSSFWorkbook(inputStream);

		}

		//Read excel sheet by sheet name

		Sheet sheet = wb.getSheet(sheetName);

		//Get the current count of rows in excel file

		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

		//Get the first row from the sheet

		Row row = sheet.getRow(0);

		//Create a new row and append it at last of sheet

		Row newRow = sheet.createRow(rowCount+1);

		//Create a loop over the cell of newly created Row

		for(int j = 0; j < row.getLastCellNum(); j++){

			//Fill data in row

			Cell cell = newRow.createCell(j);

			cell.setCellValue(dataToWrite[j]);
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
	}
}
