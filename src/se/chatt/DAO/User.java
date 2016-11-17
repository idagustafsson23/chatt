package se.chatt.DAO;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findByUsernameAndPassword", query="SELECT u FROM User u WHERE u.userName = :userName and u.password = :password"),
	@NamedQuery(name="User.findById", query="SELECT u FROM User u WHERE u.id = :userId")
})
public class User implements Serializable {
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

}