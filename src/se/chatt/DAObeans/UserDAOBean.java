package se.chatt.DAObeans;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import se.chatt.DAO.User;

@Stateful
public class UserDAOBean {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void saveUser(User user){
		entityManager.merge(user);
		entityManager.flush();
	}

	public User getUserByUserNamePassword(String userName, String password) {
		return entityManager.createNamedQuery("User.findByUsernameAndPassword", User.class)
				.setParameter("userName", userName).setParameter("password", password).getSingleResult();
	}
}
