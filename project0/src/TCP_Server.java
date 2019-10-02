import java.io.*;
import java.net.*;

public class TCP_Server extends Thread{
	Socket serverSocket;
	DataOutputStream outputstream_Client;
	
	public TCP_Server(Socket serverSocket) throws IOException{
		this.serverSocket = serverSocket;
	}
	
	public void run() {
		try {
			String Message;
			BufferedReader breader_Client = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			@SuppressWarnings("unused")
			DataOutputStream outputstream_Client = new DataOutputStream(serverSocket.getOutputStream());
			while(true) {
				Message = breader_Client.readLine();
				System.out.println(Message);
			}
		}
		catch(IOException e) {
			System.out.println("Excption Error Occurred at Server");
		}
	}
}
