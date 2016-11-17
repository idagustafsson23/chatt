package se.chatt.EJB.interfaces;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TestRemote {
	
	public void addTest(String test);
	public List<String> getTest();
}
