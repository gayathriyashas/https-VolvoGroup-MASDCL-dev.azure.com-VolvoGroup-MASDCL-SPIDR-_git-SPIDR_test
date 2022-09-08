package com.volvo.project.tests.file;

import com.volvo.project.base.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import com.volvo.project.components.fileoperations.PdfComparator;

@Epic("comparison feature in the main system")
@Story("compare files in pdf format")
public class PdfTests extends TestBase {

	@Test(groups = {"xml", "pass"})
	public void comparePdfFilesEqualTest() {
		String expectedPdfFile = "src/test/resources/testdata/pdf/test1.pdf";
		String actualPdfFile = "src/test/resources/testdata/pdf/test2.pdf";
		
		PdfComparator pdfComparator = new PdfComparator();
		pdfComparator.comparePdfFiles(expectedPdfFile, actualPdfFile);
	}
	
	@Test(groups = "pdf")
	public void comparePdfFilesNotEqualTest() {
		String expectedPdfFile = "src/test/resources/testdata/pdf/test1.pdf";
		String actualPdfFile = "src/test/resources/testdata/pdf/test3.pdf";
		
		PdfComparator pdfComparator = new PdfComparator();
		pdfComparator.comparePdfFiles(expectedPdfFile, actualPdfFile);
	}
}
