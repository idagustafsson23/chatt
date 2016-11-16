package se.chatt.EJB.remote;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface MessageEJBRemote {

	void addMessage(String message);
	List getMessages();
}
