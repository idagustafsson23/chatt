package se.chatt.DAO;

import com.yubico.client.v2.YubicoClient;

public class Yubico {
	private static final int CLIENT_ID = 30750;
    private static final String API_KEY = "OpEjOSsfeX7l1fg9hdJKViWL6xo=";

    private static final YubicoClient client = YubicoClient.getClient(CLIENT_ID, API_KEY);

	public static YubicoClient getClient() {
		return client;
	} 
}
