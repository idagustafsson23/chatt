package se.chatt.DAO;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

@Entity
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findByUsername", query="SELECT u FROM User u WHERE u.userName = :userName"),
	@NamedQuery(name="User.findById", query="SELECT u FROM User u WHERE u.id = :userId")
})
public class User implements Serializable, HttpSessionBindingListener {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="key_id")
	private int keyId;

	private String password;

	@Column(name="user_name")
	private String userName;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKeyId() {
		return this.keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		Set<User> logins = (Set<User>) event.getSession().getServletContext().getAttribute("logins");		
		logins.add(this);
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		 Set<User> logins = (Set<User>) event.getSession().getServletContext().getAttribute("logins");
	     logins.remove(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	

}