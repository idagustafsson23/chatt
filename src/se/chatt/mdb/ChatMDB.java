package se.chatt.mdb;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import se.chatt.EJB.remote.MessageEJBRemote;

/**
 * Message-Driven Bean implementation class for: ChatMDB
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "ChatTopic"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms/chatTopic")
public class ChatMDB implements MessageListener {
	private List<String> MDBmessages;
	
    /**
     * Default constructor. 
     */
	
	@EJB
	private MessageEJBRemote messageEJB;
	
    public ChatMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    	try {
    		messageEJB.addMessage(message.getBody(String.class));
			System.out.println("Mottaget meddelande:" + message.getBody(String.class));
			System.out.println("mottagit i listan: "+messageEJB.getMessages().get(0));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

	public List<String> getMDBmessages() {
		return MDBmessages;
	}

	public void setMDBmessages(List<String> mDBmessages) {
		MDBmessages = mDBmessages;
	}
    
    

}
