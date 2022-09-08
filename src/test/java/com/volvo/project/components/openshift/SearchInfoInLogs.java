package com.volvo.project.components.openshift;

import io.qameta.allure.Step;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SearchInfoInLogs {

    @Step("Search information in logs")
    public void searchingInformationInLogs(String path, String searchKeyword, String startTimestamp) throws IOException, ParseException {
//        String pathFile = '//segotx623//got623v002$//segot2ardhn010//wins//VLDGOT02_NODE01_WINS//serverlogs//SystemOut.log'
        String pathFile = path;
        BufferedReader br = new BufferedReader(new FileReader(new File(pathFile)));
        String line;
        String messageXml = "";
        String messageStr = "";
        String xmlStr = "";
        int counterXML = 0;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("2")) { //for wins '['
                String lineDateStr = line.substring(0, line.indexOf("[")); //dla wins ' CET]' i od '1'
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH); //dla wins 'M/d/yy H:mm:ss:SSS'
                Date lineDate = format1.parse(lineDateStr);
                Date startDateFormatted = format1.parse(startTimestamp);
                if (lineDate.before(startDateFormatted)) {
//                    String afterReplaceText1 = line.replaceAll("[^\\x20-\\x7e]", "")
                    String afterReplaceText1 = line.replaceAll("", "");
                    messageStr += (afterReplaceText1 + "\n");
                }
            }
        }
        assert messageStr.contains(searchKeyword);
    }
}
