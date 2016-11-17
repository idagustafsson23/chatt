package se.chatt.EJB;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import se.chatt.DAO.User;
import se.chatt.DAObeans.UserDAOBean;
import se.chatt.EJB.interfaces.UserEJBLocal;

@Stateless
public class UserEJB implements UserEJBLocal{

	@EJB
	private UserDAOBean userDAOBean;
	
	@Override
	public void saveUser(User user) {
		userDAOBean.saveUser(user);
		
	}

	@Override
	public User getUserByUserName(String userName) {
		return userDAOBean.getUserByUserName(userName);
	}

}
