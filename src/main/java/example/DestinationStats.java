/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import java.util.Enumeration;

import javax.jms.*;

class DestinationStats {

    public static void main(String []args) throws JMSException {
	
	    String user = env("ACTIVEMQ_USER", "admin");
        String password = env("ACTIVEMQ_PASSWORD", "admin");
        String host = env("ACTIVEMQ_HOST", "localhost");
        int port = Integer.parseInt(env("ACTIVEMQ_PORT", "61616"));
        String dest = env("ACTIVEMQ_DESTINATION", "FOO");
	
	    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);

        Connection connection = factory.createConnection(user, password);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
       
        Queue replyTo = session.createTemporaryQueue();
		MessageConsumer consumer = session.createConsumer(replyTo);

		/* 
		//  Problem with the the query using a "."
		//  Issue tracked by https://issues.apache.org/jira/browse/AMQ-5165
		//
		*/
		//String queueName = "ActiveMQ.Statistics.Destination." + dest;
		String queueName = "ActiveMQ.Statistics.Destination" + dest;
		System.out.println("Queue Set to: " + queueName);
		Queue testQueue = session.createQueue(queueName);
		MessageProducer producer = session.createProducer(testQueue);
		Message msg = session.createMessage();
		msg.setJMSReplyTo(replyTo);
		producer.send(msg);		

        MapMessage reply = (MapMessage) consumer.receive();
		
		if(reply != null){

		   for (Enumeration e = reply.getMapNames();e.hasMoreElements();) {
		     String name = e.nextElement().toString();
		     System.out.println(name + "=" + reply.getObject(name));
		   }
		}else{
			System.out.println("Reply was null!");
		}
		
		connection.close();
    }

   	private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if( rc== null )
            return defaultValue;
        return rc;
    }

    private static String arg(String []args, int index, String defaultValue) {
        if( index < args.length )
            return args[index];
        else
            return defaultValue;
    }
}