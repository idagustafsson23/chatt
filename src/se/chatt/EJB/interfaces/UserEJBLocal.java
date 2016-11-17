package se.chatt.EJB.interfaces;

import javax.ejb.Local;

import se.chatt.DAO.User;

@Local
public interface UserEJBLocal {
	
	public void saveUser(User user);
	public User getUserByUserNamePassword(String userName, String password);
}
