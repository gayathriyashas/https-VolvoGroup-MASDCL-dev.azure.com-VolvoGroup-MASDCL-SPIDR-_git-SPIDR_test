package com.volvo.project.tests.file;

import com.volvo.project.base.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import com.volvo.project.components.fileoperations.UserSettings;
import com.volvo.project.components.fileoperations.XmlComparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Epic("comparison feature in the main system")
@Story("compare files in xml format")
public class XmlTests extends TestBase {
	
	@Test(groups = {"xml", "pass"})
	public void compareMatchingXmlFiles() {
		String file1 = "books1.xml";
        String file2 = "books2.xml";

        XmlComparator xml = new XmlComparator();
        xml.compareXmlFiles(file1, file2);
	}
	
	@Test(groups = "xml")
	public void compareNonMatchingXmlFiles() {
		String file1 = "books1.xml";
        String file2 = "books3.xml";

        XmlComparator xml = new XmlComparator();
        xml.compareXmlFiles(file1, file2);
	}
	
	@Test(groups = "xml")
	public void compareNonMatchingXmlStringDiffBuilder() {
		final String control = "<a><b attr=\"abc\"></b></a>";
		final String test = "<a><b attr=\"xyz\"></b></a>";
		Diff myDiff = DiffBuilder.compare(Input.fromString(control))
	              .withTest(Input.fromString(test))
	              .build();
		AssertJUnit.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test(groups = {"xml", "pass"})
	public void serializeXml() {
		UserSettings settings = new UserSettings();
		settings.fieldOne = 10000;
		settings.fieldTwo = "Test Automation Academy";
        XmlComparator xml = new XmlComparator();
        xml.serializeToXML(settings, "output_settings.xml");
	}
	
	@Test(groups = {"xml", "pass"})
	public void deSerializeXml() {
		XmlComparator xml = new XmlComparator();
		UserSettings settings = xml.deserializeFromXML("settings.xml");
		System.out.println("UserSettings [fieldOne=" + settings.fieldOne + ", fieldTwo=" + settings.fieldTwo+ "]");
	}

	@Test(groups = {"smoke","regression", "passAPI"})
	public void simpleXmlTest() {
		File xmlFile = new File("src/test/resources/testdata/loginValues.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList nodeList = doc.getElementsByTagName("Test");
			for (int itr = 0; itr < nodeList.getLength(); itr++)
			{
				Node node = nodeList.item(itr);
				System.out.println("\nNode Name :" + node.getNodeName());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					System.out.println("UserName: "+ eElement.getElementsByTagName("UserName").item(0).getTextContent());
					System.out.println("Password: "+ eElement.getElementsByTagName("Password").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
