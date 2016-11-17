package se.chatt.EJB.interfaces;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface MessageEJBRemote {

	public void addMessage(String message);
	public List<String> getMessages();
}
