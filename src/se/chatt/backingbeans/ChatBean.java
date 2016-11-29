package se.chatt.backingbeans;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import se.chatt.DAO.User;

@Named(value = "chatBean")
@RequestScoped
public class ChatBean {

	private Set<User> logins;

	private List<String> messages;
	private List<User> members;
	private String message;
	
	@Inject
	private LoginBean loginBean;
	
	@PostConstruct
	private void setup() {
		
	}
	public String sendMessage() {

		return "";
	}

	public String logout(){
		loginBean.setLoggedIn(false);
		loginBean.setLoggedInUser(null);
		return "/login.xhtml?faces-redirect=true";
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
	
	public Set<User> getLogins() {
		return logins;
	}

	public void setLogins(Set<User> logins) {
		this.logins = logins;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

}
