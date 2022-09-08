package com.volvo.project.components.datatdriventesting;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.volvo.project.components.azureconfiguration.AzureDevOpsApi.azureDevOpsApiClient;

public class TestDataProvider {
    /*
        to achieve parallel testing , parallel parameter has to be set to true
     */
    @DataProvider(name = "staticExcelDataProvider", parallel = false)
    public Object[][] TestDataProviderReader(final Method testMethod) {
        String excel = "/testdata/";
        DataProviderArguments dataFile = testMethod.getAnnotation(DataProviderArguments.class);
        if (dataFile != null)
            excel += dataFile.file();
        else
            excel += "LoginWithIncorrectPasswords.xlsx";
        ExcelLibrary excelReader = new ExcelLibrary();
        int totalNumberOfRows = excelReader.getRowCount(excel);
        Object[][] array = new Object[totalNumberOfRows][];
        for (int row = 1; row <= totalNumberOfRows; row++) {
            array[row - 1][0] = excelReader.readFromExcel(row, 0, excel);
            array[row - 1][1] = excelReader.readFromExcel(row, 1, excel);
        }
        return array;
    }

    @DataProvider(name = "getDataFromFile", parallel = false)
    public static Object[][] getDataFromLocalFile(final Method testMethod) {
        DataProviderArguments dataFile = testMethod.getAnnotation(DataProviderArguments.class);
        ExcelLibrary excelReader = new ExcelLibrary();
        return excelReader.readFromExcel(dataFile.file());
    }

    @DataProvider(name = "getExcelDataFromFile", parallel = false)
    public static Object[][] getDataFromExcelTab(final Method testMethod) {
        ExcelDataProvider dataFile = testMethod.getAnnotation(ExcelDataProvider.class);
        ExcelLibrary excelReader = new ExcelLibrary();
        return excelReader.readFromExcelTab(dataFile.fileName(), dataFile.tab());
    }

    @DataProvider(name = "getDataFromExcelTabAsMap", parallel = false)
    public static Iterator<Object[]> getDataFromExcelTabAsMap(final Method testMethod) {
        ExcelDataProvider dataFile = testMethod.getAnnotation(ExcelDataProvider.class);
        ExcelLibrary excelReader = new ExcelLibrary();
        Object[][] excelResults = excelReader.readFromExcelTabWithHeaders(dataFile.fileName(), dataFile.tab());
        List<Object[]> excelResultsAsMap = new ArrayList<>();
        for (int i = 1; i < excelResults.length; i++) {
            Map<Object, Object> map = new HashMap<>();
            for (int j = 0; j < excelResults[0].length; j++) {
                map.put(excelResults[0][j], excelResults[i][j]);
            }
            excelResultsAsMap.add(new Object[]{map});
        }
        return excelResultsAsMap.iterator();
    }

    @DataProvider(name = "getDataFromJson", parallel = false)
    public static Iterator<Object[]> getDataFromJson(final Method testMethod) {
        DataProviderArguments dataFile = testMethod.getAnnotation(DataProviderArguments.class);
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader("src/test/resources/testdata/" + dataFile.file());
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj.get("dataSet");
            ArrayList<Object[]> jsonResultAsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonResultAsList.add(new Object[]{jsonArray.get(i)});
            }
            return jsonResultAsList.iterator();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @DataProvider(name = "getDataFromJsonFile", parallel = false)
    public static Iterator<Object[]> getDataFromJsonFile(final Method testMethod) {
        DataProviderArguments dataFile = testMethod.getAnnotation(DataProviderArguments.class);
        try {
            FileReader reader = new FileReader("src/test/resources/testdata/" + dataFile.file());
            JsonElement jsonData = new JsonParser().parse(reader);
            JsonArray jsonArray = jsonData.getAsJsonArray();
            ArrayList<Object[]> jsonResultAsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonResultAsList.add(new Object[]{jsonArray.get(i)});
            }
            return jsonResultAsList.iterator();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @DataProvider(name = "getSimpleData", parallel = false)
    public Object[][] getSimpleData() {
        return new Object[][]{{"Data 1", "User 1"}, {"Data 2", "User 2"}, {"blabla1", "dsadapok"}, {"tomsmith", "SuperSecretPassword!"}};
    }
    
    @DataProvider(name = "getDataFromXmlFile", parallel = false)
    public static Object[][] getDataFromXmlFile(final Method testMethod) {
        DataProviderArguments dataFile = testMethod.getAnnotation(DataProviderArguments.class);
        File xmlFile = new File("src/test/resources/testdata/" + dataFile.file());
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    	DocumentBuilder db;
        try {           
        	db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile); 
			doc.getDocumentElement().normalize(); 
			NodeList nodeList = doc.getElementsByTagName("Test");   
			Object[][] array = new Object[nodeList.getLength()][2];
	    	for (int itr = 0; itr < nodeList.getLength(); itr++)   
	    	{  
	    		Node node = nodeList.item(itr);  
	    		if (node.getNodeType() == Node.ELEMENT_NODE) {
	    			Element eElement = (Element) node;  
	    			array[itr][0] = eElement.getElementsByTagName("UserName").item(0).getTextContent();
	    			array[itr][1] = eElement.getElementsByTagName("Password").item(0).getTextContent();
	    		}
	    	}
	    	return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DataProvider(name = "getDataFromAzureTestCase", parallel = false)
    public Object[][] getDataFromAzureTestCase(final Method testMethod) {
        AzureDataProviderArguments azureArgument = testMethod.getAnnotation(AzureDataProviderArguments.class);
        String workItem = azureDevOpsApiClient.getSharedParameterSetId(azureArgument.testCaseId());
        String sharedParamsXml = azureDevOpsApiClient.getSharedParameterSetAsXml(workItem);
        Object[][] testCases = azureDevOpsApiClient.deserializeFromXML(sharedParamsXml);
        return testCases;
    }

}