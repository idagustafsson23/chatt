package se.chatt.backingbeans;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import se.chatt.DAO.User;
import se.chatt.DAO.Yubico;
import se.chatt.EJB.interfaces.UserEJBLocal;
import se.chatt.hashing.PasswordHashing;

@Named(value = "userBean")
@RequestScoped
public class UserBean {
	private String userName;
	private String password;
	private String otp;
	private User user;
	private String returnValue = "";

	@EJB
	private UserEJBLocal userEJB;

	public String addUser() {
		user = new User();
		if (userEJB.getUserByUserName(userName) != null) {
			returnValue = "Username already exsists!";
		} else {
			try {
				if(YubicoClient.isValidOTPFormat(otp)){
					VerificationResponse response = Yubico.getClient().verify(otp);
			        if (response.isOk()) {
			            String yubikeyId = YubicoClient.getPublicId(otp);
			            user.setKeyId(yubikeyId);
			            System.out.println("Nyckel registrerad!");
			        }
				}else if(!otp.equals("")){
					returnValue = "Invalid key!";
					System.out.println("Ogiltig nyckel!!!");
					return "";
				}
				user = PasswordHashing.generatePasswordHash(password, user);
				user.setUserName(userName);
				userEJB.saveUser(user);
				returnValue = "User has been registerd!";
			} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException | YubicoVerificationException | YubicoValidationFailure e) {
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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public String getReturnValue() {
		return returnValue;
	}
	
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

}
