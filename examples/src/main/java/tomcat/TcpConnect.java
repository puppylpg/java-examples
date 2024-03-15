package tomcat;

import java.io.IOException;
import java.net.Socket;

/**
 * https://github.com/elastic/elasticsearch/issues/105822
 *
 * @author puppylpg on 2024/02/28
 */
public class TcpConnect {

	public static void main(String[] args) {
		String serverAddress = "localhost";
		int serverPort = 12345;

		try {
			Socket socket = new Socket(serverAddress, serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
