package se.chatt.backingbeans;

import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import se.chatt.EJB.interfaces.MessageEJBRemote;
import se.chatt.EJB.interfaces.TestRemote;

@Named(value="chatBean")
@RequestScoped
public class ChatBean {

	@EJB
	private TestRemote testEJB;
	
	
	private List<String> messages;
	private String message;
	
	public String sendMessage() {
		
		try {
			Context context = getInitialContext();
			Topic topic = (Topic)context.lookup("jms/chatTopic");
			JMSContext jmsContext = ((ConnectionFactory)context.lookup("jms/chatCF")).createContext();
			JMSProducer jmsProducer = jmsContext.createProducer();
			jmsProducer.send(topic, message);
			jmsContext.close();
			
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return "";
	}

	
	
	public Context getInitialContext() throws NamingException{
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", 
				"com.sun.enterprise.naming.SerialInitContextFactory");
		properties.setProperty("java.naming.factory.url.pkgs", 
				"com.sun.enterprise.naming");
		properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
		return new InitialContext(properties);
	}
	

	public void updateMessages() {
		messages = testEJB.getTest();
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	

}
