import java.net.*;

public class Start_Server extends Thread {
	int portNum;
	
	public Start_Server(int portNum) {
		this.portNum = portNum;
	}
	
	public void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket server_socket = new ServerSocket(this.portNum);
			while(true){
				Socket soc = server_socket.accept();
				TCP_Server server = new TCP_Server(soc);
				server.start();
			}
		}
		catch(Exception e) {
			System.out.println("Error occurred when building the server");
		}
	}
}
