import java.io.StringReader;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

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
		chatMessage.setMessage(obj.getString("message"));
		chatMessage.setSender(obj.getString("sender"));
		chatMessage.setReceived(new Date());
		return chatMessage;
	}

	@Override
	public boolean willDecode(final String s) {
		// TODO Auto-generated method stub
		return true;
	}

}
