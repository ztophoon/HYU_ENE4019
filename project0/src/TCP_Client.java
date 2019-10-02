import java.io.*;
import java.net.*;

public class TCP_Client extends Thread{
	String userName;
	Socket clientSocket = new Socket();
	
	BufferedReader breader_User;
	DataOutputStream outputstream_Server;
	BufferedReader breader_Server;
	
	public TCP_Client(SocketAddress soc, String userName) {
		while(true) {
			try {
				this.clientSocket.connect(soc, 100000);
				this.userName = userName;
				break;
			}
			catch(Exception e) {
				clientSocket = new Socket();
			}
		}
	}
	
	public void run() {
		String Message;
		try {
			breader_User = new BufferedReader(new InputStreamReader(System.in));
			outputstream_Server = new DataOutputStream(clientSocket.getOutputStream());
			breader_Server = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while(true) {
				Message = breader_User.readLine();
				outputstream_Server.writeBytes(userName + " : " + Message + '\n');
			}
		}
		catch(Exception e) {
			System.out.println("Error occurred at Client");
		}
	}
}
