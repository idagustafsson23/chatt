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

	public User getUserByUserName(String userName) {
		try {
			return entityManager.createNamedQuery("User.findByUsername", User.class)
					.setParameter("userName", userName).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
