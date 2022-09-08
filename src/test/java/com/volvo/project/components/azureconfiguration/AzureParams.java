package com.volvo.project.components.azureconfiguration;

public class AzureParams {
    public static String AzureUrl = "https://volvogroup.visualstudio.com";
    public static String AzureProjectName = "TestOps";
    public static String AzureUserName = System.getenv("AZURE_EMAIL");
    public static String PersonalAccessToken =  System.getenv("PAT");
}
