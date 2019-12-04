import java.io.*;

public class HttpRequest {

	int port;
	final static String CRLF = "\r\n";
	final static int HTTP_PORT = 80;
	String URI = " ";
	String host = "";
	String method = "";
	String version = "";
	String headers = "";

	public HttpRequest(BufferedReader from, int cnt) {
		System.out.println("<< HttpRequest " + cnt + " >>");
		String firstLine = "";

		try {
			firstLine = from.readLine();
		} catch (IOException e) {
			System.out.println("Error reading request line: " + e);
			System.out.println();
		}
		
		String[] tmp = null;
		try {
			tmp = firstLine.split(" ");
		} catch (Exception e) {
			System.out.println(e);
		}

		method = tmp[0];
		URI = tmp[1];
		version = tmp[2];
		
		if (!method.equals("GET")) {
			System.out.println("Error: Method is not Get!");
		}

		System.out.println("method: " + method);
		System.out.println("URI: " + URI);

		if (!method.equals("GET")) {
			return;
		}

		try {
			String line = from.readLine();
			while (line != null && line.length() != 0) {
				headers += line + CRLF;
				if (line.startsWith("Host:")) {
					tmp = line.split(" ");
					if (tmp[1].indexOf(':') > 0) {
						String[] tmp2 = tmp[1].split(":");
						host = tmp2[0];
						port = Integer.parseInt(tmp2[1]);
					} 
					else {
						host = tmp[1];
						port = HTTP_PORT;
					}
				}
				line = from.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error reading from socket: " + e);
			System.out.println();
			return;
		}
		System.out.println("Host to cotact is: " + host + " at port " + port);
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String toString() {
		String req = "";
		req = method + " " + URI + " " + version + CRLF;
		req += headers;
		req += "Connection: close" + CRLF;
		req += CRLF;
		return req;
	}
}