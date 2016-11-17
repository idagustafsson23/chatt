package se.chatt.backingbeans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import se.chatt.DAO.User;
import se.chatt.EJB.interfaces.UserEJBLocal;

@Named(value = "userBean")
@RequestScoped
public class UserBean {
	private String userName;
	private String password;
	private User user;
	
	@EJB
	private UserEJBLocal userEJB;
	
	public String addUser(){
		user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		userEJB.saveUser(user);
		
		return "";
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
}
