import java.io.*;
import java.net.*;

public class ProxyCache {
	
	static int port;
	static int requestCnt = 0;
	static int responseCnt = 0;
	static ServerSocket socket;

	public static void main(String argv[]) {
		int myPort = 0;

		try {
			myPort = Integer.parseInt(argv[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Need port number as argument");
			System.exit(-1);
		} catch (NumberFormatException e) {
			System.out.println("Please give port number as interger");
			System.exit(-1);
		}

		init(myPort);
		
		Socket client = null;

		while (true) {
			try {
				client = socket.accept();
				handle(client);
			} catch (IOException e) {
				System.out.println("Error reading request from client " + e);
				System.out.println();
				continue;
			}
		}
	}

	public static void init(int p) {
		port = p;
		
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Error creating socket: " + e);
			System.out.println();
			System.exit(-1);
		}
	}

	@Deprecated
	public static void handle(Socket client) {
		Socket server = null;
		HttpRequest request = null;
		HttpResponse response = null;

		try {
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			request = new HttpRequest(fromClient, ++requestCnt);
		} catch (IOException e) {
			System.out.println("Error reading request from client " + e);
			System.out.println();
			return;
		}

		try {
			server = new Socket(request.getHost(), request.getPort());
			DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
			toServer.writeBytes(request.toString());
			System.out.println("-----------------------< Request Message >-----------------------");
			System.out.println(request.toString());
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + request.getHost());
			System.out.println(e);
			return;
		} catch (IOException e) {
			System.out.println("Error writing request to server: " + e);
			System.out.println();
			return;
		}

		try {
			DataInputStream fromServer = new DataInputStream(server.getInputStream());
			response = new HttpResponse(fromServer, ++responseCnt);

			DataOutputStream toClient = new DataOutputStream(client.getOutputStream());
			toClient.writeBytes(response.toString());
			toClient.write(response.body);

			client.close();
			server.close();
		} catch (IOException e) {
			System.out.println("Error writing response to client: " + e);
			System.out.println();
		}
	}
}