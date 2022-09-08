package com.volvo.project.components.azureconfiguration;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AzureDevOpsApi {
    private final String azureDevOpsBasicUrl;
    private final String base64Auth;
    private final JSONParser parser;
    private final String url;
    private final String project;
    private final static char NEW_LINE = '\n';

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s ");
    }

    private final OkHttpClient httpClient;

    public final static AzureDevOpsApi azureDevOpsApiClient = new AzureDevOpsApi(AzureParams.AzureUrl
            , AzureParams.AzureProjectName
            , AzureParams.AzureUserName
            , AzureParams.PersonalAccessToken);

    private AzureDevOpsApi(String url, String project, String username, String personalAccessToken) {
        this.azureDevOpsBasicUrl = url + "/" + project + "/_apis";
        this.base64Auth = Base64.getEncoder().encodeToString((username + ":" + personalAccessToken).getBytes());
        parser = new JSONParser();
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        this.url = url;
        this.project = project;
    }

    public String getSharedParameterSetId(String testCaseId) {
        String url = "/wit/workitems/"+testCaseId+"?fields=Microsoft.VSTS.TCM.LocalDataSource&api-version=5.1";
        Request request = getRequestBuilder(url)
                .addHeader("Content-Type", "text/plain")
                .get()
                .build();

        try  {
            Response response = httpClient.newCall(request).execute();
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject responseJSON = (JSONObject) parser.parse(responseBody);
            JSONObject fieldsJSON = (JSONObject) responseJSON.get("fields");
            String localDataJSON = (String) fieldsJSON.get("Microsoft.VSTS.TCM.LocalDataSource");
            JSONObject responseJSONw = (JSONObject) parser.parse(localDataJSON);
            JSONArray parameterMapJSON = (JSONArray)  responseJSONw.get("parameterMap");
            Iterator<JSONObject> idsIterator = parameterMapJSON.iterator();
            while (idsIterator.hasNext()) {
                JSONObject dataSetIdJSON = idsIterator.next();
                Long dataSetId = (Long) dataSetIdJSON.get("sharedParameterDataSetId");
                return dataSetId.toString();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        finally {
            httpClient.connectionPool().evictAll();
        }
        return null;
    }

    public String getSharedParameterSetAsXml(String sharedParameterId) {
        String url = "/wit/workitems/"+sharedParameterId+"?api-version=5.0";
        Request request = getRequestBuilder(url)
                .addHeader("Content-Type", "text/plain")
                .get()
                .build();

        try  {
            Response response = httpClient.newCall(request).execute();
            String responseBody = Objects.requireNonNull(response.body()).string();
            JSONObject responseJSON = (JSONObject) parser.parse(responseBody);
            JSONObject fieldsJSON = (JSONObject) responseJSON.get("fields");
            String parametersString = (String) fieldsJSON.get("Microsoft.VSTS.TCM.Parameters");
            String path = System.getProperty("user.dir");
            String filePath = path + "/test-output/" + sharedParameterId + "_params.xml";
            File outputFile = new File(filePath);
            try (PrintWriter out = new PrintWriter(outputFile)) {
                out.println(parametersString);
            }
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            httpClient.connectionPool().evictAll();
        }
        return null;
    }

    private Request.Builder getRequestBuilder(String url) {
        return (new Request.Builder()
                .url(azureDevOpsBasicUrl + url)
                .addHeader("Authorization", "Basic " + base64Auth)
        );
    }

    public Object[][] deserializeFromXML(String filePath)
    {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList paramList = doc.getElementsByTagName("param");
            int paramCounter = 0;
            for (int itr = 0; itr < paramList.getLength(); itr++)
            {
                Node node = paramList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    if (eElement.getTextContent() != null && !Objects.equals(eElement.getTextContent(), "")) {
                        paramCounter++;
                    }
                }
            }

            NodeList dataRowsList = doc.getElementsByTagName("dataRow");
            Object[][] array = new Object[dataRowsList.getLength()][paramCounter];
            for (int itr = 0; itr < dataRowsList.getLength(); itr++)
            {
                Node node = dataRowsList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    NodeList kvpList = eElement.getElementsByTagName("kvp");
                    if (kvpList.getLength() == paramCounter) {
                        for(int i = 0 ; i < kvpList.getLength(); i++){
                            array[itr][i] = kvpList.item(i).getAttributes().getNamedItem("value").getNodeValue().toString();
                        }
                    }

                }
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
