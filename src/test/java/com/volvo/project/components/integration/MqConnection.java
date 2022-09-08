package com.volvo.project.components.integration;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import io.qameta.allure.Step;

import java.io.IOException;

public class MqConnection {
	
	MQQueueManager queueManager;
	MQQueue queue;
	MQMessage mqMessage;
    MQPutMessageOptions mqPMO;
	
    public MqConnection(String queueMangerName, String queueName, String channelName, String hostName, int port) {
    	try 
		{
			MQEnvironment.hostname = hostName;
	    	MQEnvironment.port = port;
	    	MQEnvironment.channel = channelName; 
	    	
	    	queueManager = new MQQueueManager(queueMangerName);	    	
	    	queue = queueManager.accessQueue(queueName, CMQC.MQOO_OUTPUT);
		}
		catch (MQException | NumberFormatException je) {
			je.printStackTrace(System.err);
		}		
    }

	@Step("Send MQ message: {msg}")
	public void sendMqMsg(String msg) {
		try 
		{
			mqMessage = new MQMessage();
	        mqMessage.encoding = new Integer("546");
	        mqMessage.characterSet = new Integer("1208");
	        mqMessage.format = "MQSTR";

	        mqMessage.writeString(msg);
	        mqPMO = new MQPutMessageOptions();
	        queue.put(mqMessage, mqPMO);
		}
		catch (MQException | NumberFormatException | IOException je)
        {
            je.printStackTrace(System.err);
        }
		finally {
			disconnectFromMq();
		}
	}
	
	public void disconnectFromMq() {
		try {
			queue.close();		
		} 
		catch (MQException | NumberFormatException je)
	    {
	        je.printStackTrace(System.err);
	    } finally
	    {
	        if (queueManager != null) try
	        {
	            queueManager.disconnect();
	        } 
	        catch (MQException ex) {
	            ex.printStackTrace(System.err);
	        } 
	    }
	}
}
