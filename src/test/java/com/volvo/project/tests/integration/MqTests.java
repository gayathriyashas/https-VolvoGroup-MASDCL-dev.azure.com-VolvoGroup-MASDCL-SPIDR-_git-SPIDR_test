package com.volvo.project.tests.integration;

import com.volvo.project.base.TestBase;
import com.volvo.project.components.integration.MqConnection;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
@Epic("integrations with other systems")
@Story("integration with MQ")
public class MqTests extends TestBase {
	
	@Test(groups="mq")
	public void sendMqMsgTest() {
		new MqConnection("GIMMQ", "WMQI.VITFramework_Example1_in", "SYSTEM.ADMIN.SVRCONN", "127.0.0.1", 2414).sendMqMsg("Hello from java!");
	}
}
