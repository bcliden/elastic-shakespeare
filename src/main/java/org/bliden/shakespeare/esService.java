package org.bliden.shakespeare;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class esService {
	
	private TransportClient client;

	@SuppressWarnings("resource")
	public esService() {
		try {
			this.client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TransportClient getClient() {
		return this.client;
	}
	
	public void releaseClient() {
		this.client.close();
	}
}


