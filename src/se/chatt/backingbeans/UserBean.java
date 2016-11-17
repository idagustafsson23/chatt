package se.chatt.backingbeans;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import se.chatt.DAO.User;
import se.chatt.EJB.interfaces.UserEJBLocal;
import se.chatt.hashing.PasswordHashing;

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

		if(userEJB.getUserByUserName(userName) != null) {
			//användarnamnet upptaget
		}else{
			String hashedPassword;
			try {
				hashedPassword = PasswordHashing.generatePasswordHash(password);
				user.setUserName(userName);
				user.setPassword(hashedPassword);
				userEJB.saveUser(user);	
			} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
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
