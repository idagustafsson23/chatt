import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.primefaces.json.JSONArray;

import se.chatt.backingbeans.LoginBean;

@ServerEndpoint(value = "/chat/", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());

	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	 private PublicKey publicKey;
	 private PrivateKey privateKey;

	@Inject
	private LoginBean loginBean;

	@OnOpen
	public void open(final Session session) {
		log.info("Session opened");
		generateKey();
		session.getUserProperties().put("userName", loginBean.getLoggedInUser().getUserName());
		session.getUserProperties().put("publicKey", publicKey);
		session.getUserProperties().put("privateKey", privateKey);
		sessions.add(session);
		ChatMessage msg = new ChatMessage();
		msg.setMessage(session.getUserProperties().get("userName").toString() + " har loggat in!");
		msg.setSender("SYSTEMS");
		msg.setReceived(new Date());
		sendMessageToAll(msg);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		sessions.remove(session);
		log.info("Session closed");
		ChatMessage msg = new ChatMessage();
		msg.setMessage(session.getUserProperties().get("userName").toString() + " har loggat ut!");
		msg.setSender("SYSTEM");
		msg.setReceived(new Date());
		sendMessageToAll(msg);

	}

	@OnMessage
	public void onMessage(final Session session, final ChatMessage chatMessage) {
		sendMessageToAll(chatMessage);
	}

	private void sendMessageToAll(ChatMessage message) {
		List<String> users = new ArrayList<String>();
		for (Session s : sessions) {
			users.add((String) s.getUserProperties().get("userName"));
		}
		JSONArray array = new JSONArray(users);
		String json = array.toString();
		message.setUsers(json);
		ChatMessage chatMessage = new ChatMessage();
		for(Session s : sessions) {
			if(message.getDecodeKey() != null && message.getDecodeKey().equals(s.getUserProperties().get("publicKey"))){
				String msg = decrypt(fromHex(message.getMessage()), (PrivateKey) s.getUserProperties().get("privateKey"));
				chatMessage.setReceived(message.getReceived());
				chatMessage.setSender(message.getSender());
				chatMessage.setUsers(message.getUsers());
				chatMessage.setMessage(msg);
			}
		}
		for (Session s : sessions) {
			try {
				if(message.getDecodeKey() != null && (message.getDecodeKey().equals(s.getUserProperties().get("publicKey")) || message.getSender().equals(s.getUserProperties().get("userName")))){
					s.getBasicRemote().sendObject(chatMessage);
				}else{
					s.getBasicRemote().sendObject(message);
				}
			} catch (IOException | EncodeException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	private void generateKey(){
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair keyPair = keyGen.generateKeyPair();
			publicKey =  keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static Set<Session> getSessions() {
		return sessions;
	}
	
	public static String decrypt(byte[] text, PrivateKey key) {
	    byte[] dectyptedText = null;
	    try {
	      Cipher cipher = Cipher.getInstance("RSA");
	      cipher.init(Cipher.DECRYPT_MODE, key);
	      dectyptedText = cipher.doFinal(text);
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }

	    return new String(dectyptedText);
	  }
	
	private static byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
