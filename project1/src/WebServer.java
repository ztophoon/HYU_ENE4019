import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSock = new ServerSocket(7777);
			System.out.println("Running Server...");

			while(true) {				
				Socket connectionSock = serverSock.accept();
				
				// Construct an object to process the HTTP request message.
				HttpRequest request = new HttpRequest(connectionSock);
				
				// Create a new thread to process the request.
				Thread thread = new Thread(request);
				thread.start();
			}
		} catch(IOException e){
			System.out.println(e.getMessage());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}