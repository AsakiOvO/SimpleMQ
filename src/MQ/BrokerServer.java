package MQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//消息队列服务，对外提供Broker类的服务
public class BrokerServer implements Runnable {
	public static int SERVICE_PORT = 9999;
	private final Socket socket;

	public BrokerServer(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());) {
			while (true) {
				String str = bufferedReader.readLine();
				if (str == null) {
					continue;
				}
				System.out.println("接收到的原始数据为:" + str);
				if (str.equals("CONSUME")) {    //CONSUME表示要消费一条消息
					String msg = Broker.consume();
					printWriter.println(msg);
					printWriter.flush();
				} else {    //其他情况都表示要生产消息到消息队列中
					Broker.produce(str);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(BrokerServer.SERVICE_PORT);
		while (true) {
			BrokerServer brokerServer = new BrokerServer(serverSocket.accept());
			new Thread(brokerServer).start();
		}
	}
}
