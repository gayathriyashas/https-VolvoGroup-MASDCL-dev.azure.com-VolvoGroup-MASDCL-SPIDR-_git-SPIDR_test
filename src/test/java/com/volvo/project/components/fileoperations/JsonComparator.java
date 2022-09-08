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

import static org.testng.Assert.assertEquals;

public class JsonComparator {
	private static final Logger log = LoggerFactory.getLogger(JsonComparator.class);

	@Step("Compare json strings")
	public void compareJsonStrings(String expectedJsonString, String actualJsonString) {
		JsonParser jsonParser = new JsonParser();
		JsonObject expectedJsonObj = (JsonObject) jsonParser.parse(expectedJsonString);
		JsonObject actualJsonObj = (JsonObject) jsonParser.parse(actualJsonString);
		printJsonDiff(expectedJsonString, actualJsonString);
		assertEquals(expectedJsonObj, actualJsonObj);		
	}
	
	@SuppressWarnings("unchecked")
	public void printJsonDiff(String expected, String actual) {
		HashMap<String, Object> expectedHashMap = new Gson().fromJson(expected, HashMap.class);
		HashMap<String, Object> actualHashMap = new Gson().fromJson(actual, HashMap.class);
		MapDifference<String, Object> difference = Maps.difference(expectedHashMap, actualHashMap);
		log.info(String.valueOf(difference));
	}

	@Step("Compare json files")
	public void compareJsonFiles(String expectedFile, String actualFile) {
		JsonElement jsonExpectedData = null;
		JsonElement jsonActualData = null;
		try {
			FileReader expectedReader = new FileReader(expectedFile);
			FileReader actualReader = new FileReader(actualFile);
			jsonExpectedData = new JsonParser().parse(expectedReader);
			jsonActualData = new JsonParser().parse(actualReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonArray jsonExpectedArray = jsonExpectedData.getAsJsonArray();
		JsonArray jsonActualArray = jsonActualData.getAsJsonArray();
		assertEquals(jsonExpectedArray.size(), jsonActualArray.size());
		for (int i = 0; i < jsonExpectedArray.size(); i++) {
			printJsonDiff(jsonExpectedArray.get(i).toString(), jsonActualArray.get(i).toString());
        }
		assertEquals(jsonExpectedArray, jsonActualArray);
        
	}
	
	public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

	@Step("Serialize to json")
	public void serializeToJson(JsonBook book) {
		Gson gson = new Gson();
		String json = gson.toJson(book);
		log.info(json);
	}

	@Step("Deserialize json")
	public void deserializeJson(String jsonString) {
		Gson gson = new Gson();		 
		JsonBook bookObject = gson.fromJson(jsonString, JsonBook.class);		 
		log.info("Id: " + bookObject.Id + ", Author: " + bookObject.Author + ", Title: " + bookObject.Title
				+ ", Genre: " + bookObject.Genre + ", Price: " + bookObject.Price + ", Publish Date: " + bookObject.Publish_Date 
				+ ", Description: " + bookObject.Description);
	}
}
