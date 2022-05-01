package Test;

import MQ.MQClient;

public class ProduceClient {
	public static void main(String[] args) throws Exception {
		MQClient client = new MQClient();
		client.produce("Hello world!!");
	}
}
