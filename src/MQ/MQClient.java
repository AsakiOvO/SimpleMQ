package MQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

//客户端访问
public class MQClient {
	//生产消息
	public static void produce(String msg) throws Exception {
		Socket socket = new Socket(InetAddress.getLocalHost(),BrokerServer.SERVICE_PORT);
		try(PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {
			printWriter.println(msg);
			printWriter.flush();
		}
	}

	//消费消息
	public static String consume() throws Exception {
		Socket socket = new Socket(InetAddress.getLocalHost(),BrokerServer.SERVICE_PORT);
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
		) {
			//先向消息队列发送CONSUME表示消费消息
			printWriter.println("CONSUME");
			printWriter.flush();
			//再从队列获取一条消息
			String msg = bufferedReader.readLine();
			return msg;
		}
	}
}
