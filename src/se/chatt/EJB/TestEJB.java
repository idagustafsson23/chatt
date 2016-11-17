package se.chatt.EJB;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import se.chatt.EJB.interfaces.MessageEJBRemote;
import se.chatt.EJB.interfaces.TestRemote;

@Stateless
public class TestEJB implements TestRemote{
	
	@EJB
	private MessageEJBRemote messageEJB;

	@Override
	public void addTest(String test) {
		messageEJB.addMessage(test);
		
	}

	@Override
	public List<String> getTest() {
		return messageEJB.getMessages();
	}

}
