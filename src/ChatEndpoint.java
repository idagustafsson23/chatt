import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/chat/", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());
	
	@OnOpen
	public void open(final Session session) {
		log.info("Session opened");
	}
	
	@OnMessage
	public void onMessage (final Session session, final ChatMessage chatMessage) {
		try{
			for(Session s : session.getOpenSessions()) {
				if(s.isOpen()) {
					s.getBasicRemote().sendObject(chatMessage);
				}
			}
		} catch (IOException | EncodeException e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}
}
