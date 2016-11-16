package se.chatt.EJB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

import se.chatt.EJB.remote.MessageEJBRemote;

@Stateful
public class MessageEJB implements MessageEJBRemote{
	
	
	private List<String> messages;
	
	@PostConstruct
	private void initializeBean() {
		messages = new ArrayList<String>();
	}
	
	@Override
	public void addMessage(String message) {
		messages.add(message);
		System.out.println("i messageEJB.addmessage: " + message);
		System.out.println("från lista: " + messages.get(0));
	}
	
	@Override
	public List<String> getMessages() {
		System.out.println("getMessages   messages.size: " + messages.size());
		return messages;
	}
}
