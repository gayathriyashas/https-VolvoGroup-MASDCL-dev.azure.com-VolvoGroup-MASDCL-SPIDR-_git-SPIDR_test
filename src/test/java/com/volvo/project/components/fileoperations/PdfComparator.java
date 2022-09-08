package com.volvo.project.components.fileoperations;

import io.qameta.allure.Step;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.testng.Assert.assertEquals;

public class PdfComparator {
	private static final Logger log = LoggerFactory.getLogger(PdfComparator.class);

	@Step("Compare pdf files")
	public void comparePdfFiles(String expectedPDF, String actualPDF) {
		List<String> expListData = new ArrayList<String>();
		List<String> actListData = new ArrayList<String>();
		PDDocument expPdfDocument = null;
		PDDocument actPdfDocument = null;
		String actPdfData = "";
		String expPdfData = "";

		try {
			File file = new File(expectedPDF);
			File file2 = new File(actualPDF);
			expPdfDocument = Loader.loadPDF(file);
			actPdfDocument = Loader.loadPDF(file2);
//			expPdfDocument = PDDocument.load(new File(expectedPDF));
//			actPdfDocument = PDDocument.load(new File(actualPDF));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(!(expPdfDocument.getNumberOfPages() == actPdfDocument.getNumberOfPages())){
			log.info("Both the files pages are not equal, "
					+ "first file page count is " + expPdfDocument.getNumberOfPages()
					+ " and second file is "+actPdfDocument.getNumberOfPages());
		}
		assertEquals(expPdfDocument.getNumberOfPages(), actPdfDocument.getNumberOfPages());

		if (!expPdfDocument.isEncrypted() && !actPdfDocument.isEncrypted()) {
			PDFTextStripperByArea stripper;
			try {
				stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper Tstripper = new PDFTextStripper();
				expPdfData = Tstripper.getText(expPdfDocument);
				actPdfData = Tstripper.getText(actPdfDocument);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(expPdfData.equals(actPdfData)){
			log.info("Both the files are having same data");
		}

		else{
			log.info("Data in the files are not same");
			StringTokenizer stExpPdf1 = new StringTokenizer(expPdfData,"\n");
			StringTokenizer stActPdf1 = new StringTokenizer(actPdfData,"\n");

			while(stExpPdf1.hasMoreTokens()){
				expListData.add(stExpPdf1.nextToken());
			}

			while(stActPdf1.hasMoreTokens()){
				actListData.add(stActPdf1.nextToken());
			}
			for(int i=0;i<actListData.size();i++){
				if (!actListData.get(i).equals(expListData.get(i))) {
					log.info("PDF FILE ACT "+actListData.get(i));
					log.info("PDF FILE EXP "+expListData.get(i));
				}
			}
		}
		try {
			expPdfDocument.close();
			actPdfDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(expPdfData, actPdfData);
	}
}
