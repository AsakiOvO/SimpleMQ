package Test;

import MQ.MQClient;

public class ConsumeClient {
	public static void main(String[] args) throws Exception {
		MQClient client = new MQClient();
		String msg = client.consume();
		System.out.println("获取的消息为:" + msg);
	}
}
