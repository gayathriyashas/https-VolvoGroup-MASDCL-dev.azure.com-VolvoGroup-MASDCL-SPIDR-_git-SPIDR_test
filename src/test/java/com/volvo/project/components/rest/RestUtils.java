package com.volvo.project.components.rest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

public class RestUtils{

  public static Object convertLongObjectToIntObject(Object longValue) {
    if(longValue != null){

      return (Long.valueOf((String) longValue).intValue());
    }
    return null;
  }

  @SuppressWarnings("unchecked")
public static Object convertLongArrayToIntArray(Object jsonArrayObject){
    JSONArray jsonArray = (JSONArray) jsonArrayObject;
    if(jsonArray != null){
      for(int i = 0; i < jsonArray.size(); i++){
        jsonArray.set(i, convertLongObjectToIntObject(jsonArray.get(i)));
      }
    }
    return jsonArrayObject;
  }

//
// TEMPORARY COMMENTED OUT TO AVOID DOWNLOADING JAVAFX
//
//  public static Map<String, Object> createMap(@NotNull Pair<String, Object>... argsToPut){
//    Map<String, Object> map = new HashMap<>();
//    for(int i = 0; i < argsToPut.length; i++) {
//      Object valueToPut = argsToPut[i].getValue();
//      String keyToPut = argsToPut[i].getKey();
//      if (valueToPut != null && !valueToPut.equals(RestParameters.NULL_STRING)) {
//        map.put(keyToPut, valueToPut);
//      }
//    }
//    return map;
//  }


  public static String getValuesFromMap(Map<String, String> objectMap) {
    String mapString = "";
    for(Map.Entry<String, String> entry: objectMap.entrySet()){
      mapString += entry.getValue() + ", ";
    }
    mapString = mapString.substring(0, mapString.length() - 2);
    return mapString;
  }

  @SuppressWarnings("rawtypes")
public static String getValuesFromJson(JSONObject json){
    String jsonString = "";
    for(Object e: json.entrySet()){
      Map.Entry entry = (Map.Entry) e;
      jsonString += entry.getValue() + ", ";
    }
    jsonString = jsonString.substring(0, jsonString.length() - 2);
    return jsonString;
  }
}
