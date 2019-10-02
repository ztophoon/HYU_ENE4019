import java.io.*;
import java.net.*;

public class P2P_Server {
	public static void main(String args[]) throws Exception{
		BufferedReader breader_User = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Enter your name : ");
		String userName = breader_User.readLine();
		System.out.print("Your Port Number: ");
		int portNum_1 = Integer.parseInt(breader_User.readLine());
		System.out.print("Other Port Number : ");
		int portNum_2 = Integer.parseInt(breader_User.readLine());
		
		Start_Server server = new Start_Server(portNum_1);
		server.start();
		
		System.out.println("[Server building complete!]");
		
		String IP = "127.0.0.1";
		TCP_Client Client = new TCP_Client(new InetSocketAddress(IP,portNum_2), userName);
		Client.start();
		
		System.out.println("[Client building complete!]");
	}
}
