package com.volvo.project.components.fileoperations;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlComparator {
	@Step("Compare xml files")
	public void compareXmlFiles(String fileName1, String fileName2) {
		String path = System.getProperty("user.dir");
		File file1 = new File(path + "/src/test/resources/testdata/xml/" + fileName1);
		File file2 = new File(path + "/src/test/resources/testdata/xml/" + fileName2);
		
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc1 = dBuilder.parse(file1);
			Document doc2 = dBuilder.parse(file2);		
			
			if (doc1.hasChildNodes()) {
				compareNodes(doc1.getChildNodes(), doc2.getChildNodes());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void compareNodes(NodeList nodeList1, NodeList nodeList2) {
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(nodeList1.getLength()).isEqualTo(nodeList1.getLength());
		
		for (int i = 0; i < nodeList1.getLength(); i++) {
			Node tempNode1 = nodeList1.item(i);
			Node tempNode2 = nodeList2.item(i);
			
			if (tempNode1.getNodeType() == Node.ELEMENT_NODE && tempNode2.getNodeType() == Node.ELEMENT_NODE) {
				softly.assertThat(tempNode1.getNodeName()).isEqualTo(tempNode2.getNodeName());
				softly.assertThat(tempNode1.getTextContent()).isEqualTo(tempNode2.getTextContent());
				
				if (tempNode1.hasAttributes()) {
					NamedNodeMap nodeMap1 = tempNode1.getAttributes();
					NamedNodeMap nodeMap2 = tempNode2.getAttributes();
					for (int j = 0; j < nodeMap1.getLength(); j++) {
						softly.assertThat(nodeMap1.getLength()).isEqualTo(nodeMap2.getLength());
						Node node1 = nodeMap1.item(j);
						Node node2 = nodeMap2.item(j);
						softly.assertThat(node1.getNodeName()).isEqualTo(node2.getNodeName());
						softly.assertThat(node1.getNodeValue()).isEqualTo(node2.getNodeValue());
					}
				}
				
				if (tempNode1.hasChildNodes()) {
					compareNodes(tempNode1.getChildNodes(), tempNode2.getChildNodes());
				}
			}
		}
		softly.assertAll();
	}

	@Step("Serialize to xml")
	public void serializeToXML(UserSettings settings, String outputFileName) 
    {
        String path = System.getProperty("user.dir");
		try {
			File outputFile = new File(path + "/test-output/" + outputFileName);
			outputFile.getParentFile().mkdirs(); // if dirs already exists will do nothing
			FileOutputStream fos = new FileOutputStream(outputFile);
			XMLEncoder encoder = new XMLEncoder(fos);
	        encoder.writeObject(settings);
	        encoder.close();
	        fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}        
    }

	@Step("Deserialize from xml")
	public UserSettings deserializeFromXML(String inputFileName)
	{
		String path = System.getProperty("user.dir");
        File inputFile = new File(path + "/src/test/resources/testdata/xml/" + inputFileName);
        UserSettings decodedSettings = null;
        try {
        	FileInputStream fis = new FileInputStream(inputFile);
    	    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(fis));
    	    decodedSettings = (UserSettings) decoder.readObject();
    	    decoder.close();
    	    fis.close();
        }
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }	    
	    return decodedSettings;
	}
	
}
