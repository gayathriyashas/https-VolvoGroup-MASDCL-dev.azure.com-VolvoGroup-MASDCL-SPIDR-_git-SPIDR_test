package com.volvo.project.components.datatdriventesting;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 

public class DataProviderXmlParams {
	
	@XmlRootElement(name = "TestCases")
	@XmlAccessorType(XmlAccessType.PROPERTY)
	public static class TestCases implements Serializable 
	{
		private static final long serialVersionUID = 1L;
		
		List<Test> Tests;
		
		public TestCases() {
			super();
		}
		
		public TestCases(List<Test> tests) {
			super();
			this.Tests = tests;
		}
		
		@Override
	    public String toString() {
			String output = "";
	         for(int i = 0; i < Tests.size(); i++) {
	        	 output += Tests.get(i).toString();
	         }
	        return output;
	    }

	}
	
	@XmlRootElement(name = "Test")
	@XmlAccessorType(XmlAccessType.PROPERTY)
	public static class Test implements Serializable 
	{
		private static final long serialVersionUID = 1L;
		
		String UserName;
		String Password;
		
		public Test() {
			super();
		}
		
		public Test(String user, String pass) {
			super();
			this.UserName = user;
			this.Password = pass;
		}
		
		@Override
	    public String toString() {
	        return "Test [username=" + UserName + ", password=" + Password + "]";
	    }
	}
}	