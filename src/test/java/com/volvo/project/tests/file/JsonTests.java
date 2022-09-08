package com.volvo.project.tests.file;

import com.volvo.project.base.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import com.volvo.project.components.fileoperations.JsonBook;
import com.volvo.project.components.fileoperations.JsonComparator;

@Epic("comparison feature in the main system")
@Story("compare files in json format")
public class JsonTests extends TestBase {

	@Test(groups = "json")
	public void compareJsonStringsTest() {
		String expected = "{ \"Name\": \"20181004164456\", \"objectId\": \"4ea9b00b-d601-44af-a990-3034af18fdb1%>\" }";
		String actual = "{ \"Name\": \"AAAAAAAAAAAA\", \"objectId\": \"4ea9b00b-d601-44af-a990-3034af18fdb1%>\" }";
		
		JsonComparator jsonComparator = new JsonComparator();
		jsonComparator.compareJsonStrings(expected, actual);
	}
	
	@Test(groups = "json")
	public void compareJsonFilesTest() {
		String expected = "src/test/resources/testdata/json/books1.json";
		String actual = "src/test/resources/testdata/json/books3.json";
		
		JsonComparator jsonComparator = new JsonComparator();
		jsonComparator.compareJsonFiles(expected, actual);
	}
	
	@Test(groups = {"json", "pass"})
	public void serializeJsonTest() {
		JsonBook book = new JsonBook();
		book.Author = "James Bond";
        book.Title = "Casino Royale";
        book.Id = "bk007";
        book.Genre = "Action";
        book.Description = "My name is Bond, James Bond";
        book.Price = "5.95";
        book.Publish_Date = "2000-12-16";
        
        JsonComparator jsonComparator = new JsonComparator();
        jsonComparator.serializeToJson(book);
	}
	
	@Test(groups = {"json", "pass"})
	//TODO change jsonString to use dataProvider like in RestApiTest.java
	public void deserializeJsonTest() {
		String jsonString = 
				"{\"Id\":\"bk007\","
				+ "\"Author\":\"James Bond\","
				+ "\"Title\":\"Casino Royale\","
				+ "\"Genre\":\"Action\","
				+ "\"Price\":\"5.95\","
				+ "\"Publish_Date\":\"2000-12-16\","
				+ "\"Description\":\"My name is Bond, James Bond\"}";
		
		JsonComparator jsonComparator = new JsonComparator();
        jsonComparator.deserializeJson(jsonString);
	}
}
