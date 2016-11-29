import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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

	@Inject
	private LoginBean loginBean;

	@OnOpen
	public void open(final Session session) {
		log.info("Session opened");
		session.getUserProperties().put("userName", loginBean.getLoggedInUser().getUserName());
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
		for (Session s : sessions) {
			try {
				s.getBasicRemote().sendObject(message);

			} catch (IOException | EncodeException ex) {
				ex.printStackTrace();
			}
		}

	}
}
