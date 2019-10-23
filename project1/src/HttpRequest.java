import java.io.*;
import java.net.*;
import java.util.*;

final class HttpRequest implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;
	
	// Constructor
	public HttpRequest(Socket socket) throws Exception{
		this.socket = socket;
	}
	
	// Implement the run() method of the Runnable interface.
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void processRequest() throws Exception{
		// Get a reference to the socket's input and output streams.
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		
		// Set up input stream filters.
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		// Get the request line of the HTTP request message.
		String requestLine = br.readLine();
		
		// Part A
		// Display the request line.
		// System.out.println();
		System.out.println(requestLine);
		
		
		if(requestLine != null) {
			
			// Extract the filename from the request line.
			StringTokenizer tokens = new StringTokenizer(requestLine);
			System.out.println("request line: " + requestLine.toString());
			tokens.nextToken();
			
			// Skip over the method, which should be "GET"
			String fileName = tokens.nextToken();
			// Prepend a "." so that file request is within the current directory.
			fileName = "." + fileName;
			
			// Open the requested file.
			FileInputStream fis = null;
			boolean fileExists = true;
			
			try {
				fis = new FileInputStream(fileName);
			} catch (FileNotFoundException e){
				fileExists = false;
			}
			
			System.out.println("Incoming!!!");
			
			// Construct the response message.
			String statusLine = null;
			String contentTypeLine = null;
			String entityBody = null;
			
			
			if(fileExists) {
				statusLine = "HTTP/1.1 200 OK" + CRLF;
				contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
			}
			else {
				statusLine = "HTTP/1.1 404 Not found" + CRLF;
				contentTypeLine = "Content-type: text/html" + CRLF;
				entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" 
				+ "<BODY>Not Found</BODY></HTML>";
			}
			
			// Compare the version of HTTP request and server.
			String version = tokens.nextToken();
			if(version.equals("HTTP/1.0")) {
				statusLine = "HTTP/1.1 400 Bad Request" + CRLF;
			}
			
			// Send the status line.
			os.writeBytes(statusLine);
			
			// Send the content type line.
			os.writeBytes(contentTypeLine);
			
			// Send a blank line to indicate the end of the header lines.
			os.writeBytes(CRLF);
			
			// Send the entity body.
			if(fileExists) {
				sendBytes(fis, os);
			}
			else {
				os.writeBytes(entityBody);
			}
			
			// Close streams and socket.
			if(fis != null) {
				fis.close();
			}
		}
		
		os.close();
		br.close();
		socket.close();
	}

	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception{
		// Construct a 1K buffer to hold bytes on their way to the socket.
		byte[] buffer = new byte[1024];
		int bytes = 0;
		
		// Copy requested file into the socket's output stream.
		while((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		if(fileName.endsWith(".gif") || fileName.endsWith(".GIF")) {
			return "image/gif";
		}
		if(fileName.endsWith(".jpeg")|| fileName.endsWith(".jpg")) {
			return "image/jpg";
		}
		if(fileName.endsWith(".java")) {
			return "java file";
		}
		return "application/octet-stream";
	}
	
}