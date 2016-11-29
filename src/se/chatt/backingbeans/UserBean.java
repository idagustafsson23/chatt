package se.chatt.backingbeans;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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

	@EJB
	private UserEJBLocal userEJB;

	public String addUser() {
		user = new User();
		try {
			if (YubicoClient.isValidOTPFormat(otp)) {
				VerificationResponse response = Yubico.getClient().verify(otp);
				if (response.isOk()) {
					String yubikeyId = YubicoClient.getPublicId(otp);
					user.setKeyId(yubikeyId);
					System.out.println("Nyckel registrerad!");
				}
			}
			user = PasswordHashing.generatePasswordHash(password, user);
			user.setUserName(userName);
			userEJB.saveUser(user);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException
				| YubicoVerificationException | YubicoValidationFailure e) {
			e.printStackTrace();
		}

		return "/login.xhtml?faces-redirect=true";
	}
	
	public String goToLogin(){
		return "/login.xhtml?faces-redirect=true";
	}

	public void validateUserName(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String userName = (String) value;
		if(!userName.matches("[A-Za-z0-9]+")){
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Username can only containe letters and numbers!");
			context.addMessage(component.getClientId(context), message);
		}else if (userEJB.getUserByUserName(userName) != null) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Username already exsists!");
			context.addMessage(component.getClientId(context), message);
		} 
	}

	public void validateKey(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String otp = (String) value;
		if (!YubicoClient.isValidOTPFormat(otp) && !otp.equals("")) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Invalid key!");
			context.addMessage(component.getClientId(context), message);
		}
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

}
