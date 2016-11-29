import java.io.StringReader;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Date;
import java.util.Set;

import javax.crypto.Cipher;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;






public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChatMessage decode(final String textMessage) throws DecodeException {
		
		ChatMessage chatMessage = new ChatMessage();
		JsonObject obj = Json.createReader(new StringReader(textMessage)).readObject();
		String user = obj.getString("decodeKey");
		String message = obj.getString("message");
		PublicKey key = getPublicKey(user);
		if(key != null && !message.equals("")){
			message = toHex(encrypt(message, key));
			chatMessage.setDecodeKey(key);
		}else if(!user.equals("") || message.equals("")){
			return null;
		}
		chatMessage.setMessage(message);
		chatMessage.setSender(obj.getString("sender"));
		chatMessage.setReceived(new Date());
		
		return chatMessage;
	}

	private PublicKey getPublicKey(String user) {
		Set<Session> sessions = ChatEndpoint.getSessions();
		for(Session s : sessions){
			String sUserName = (String) s.getUserProperties().get("userName");
			if(user.equals(sUserName)){
				return (PublicKey) s.getUserProperties().get("publicKey");
			}
		}
		return null;
	}

	@Override
	public boolean willDecode(final String s) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static byte[] encrypt(String text, PublicKey key) {
	    byte[] cipherText = null;
	    try {
	      final Cipher cipher = Cipher.getInstance("RSA");
	      cipher.init(Cipher.ENCRYPT_MODE, key);
	      cipherText = cipher.doFinal(text.getBytes());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return cipherText;
	  }
	
	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

}
