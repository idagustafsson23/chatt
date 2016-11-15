package se.chatt.backingbeans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;


@Named(value="loginBean")
@RequestScoped
public class LoginBean {
	private String textField;
	
	public String login() {
		YubicoClient client = YubicoClient.getClient(30750, "OpEjOSsfeX7l1fg9hdJKViWL6xo=");

		VerificationResponse response;
		try {
			response = client.verify(textField);
			if(response.isOk()){
				System.out.println("FINE");
			}else{
				System.out.println("BLÄÄÄÄÄ");
			}
		} catch (YubicoVerificationException | YubicoValidationFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	public String getTextField() {
		return textField;
	}
	
	public void setTextField(String textField) {
		this.textField = textField;
	}
}
