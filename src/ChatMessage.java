import java.security.PublicKey;
import java.util.Date;

public class ChatMessage {
	private String message;
	private String sender;
	private Date received;
	private String users;
	private PublicKey decodeKey;	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getReceived() {
		return received;
	}
	public void setReceived(Date received) {
		this.received = received;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public PublicKey getDecodeKey() {
		return decodeKey;
	}
	public void setDecodeKey(PublicKey decodeKey) {
		this.decodeKey = decodeKey;
	}
	@Override
	public String toString(){
		return "ChatMessage [message=" + message + ", sender=" + sender + ", recieved=" + received + ", users=" + users + "]";
	}

}
