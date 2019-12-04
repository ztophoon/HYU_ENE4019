import java.io.*;

public class HttpResponse {
	
	final static String CRLF = "\r\n";
	final static int MAX_BUFFER_SIZE = 8192;
	final static int MAX_OBJECT_SIZE = 100000;
	String version;
	int status;
	String statusLine = "";
	String headers = "";
	byte body[] = new byte[MAX_OBJECT_SIZE];

	@Deprecated
	public HttpResponse(DataInputStream fromServer, int cnt) {
		System.out.println("<< HttpResponse " + cnt + " >>");

		int length = -1;
		boolean gotStatusLine = false;

		try {
			String line = fromServer.readLine();
			while (line.length() != 0) {

				if (!gotStatusLine) {
					statusLine = line;
					gotStatusLine = true;
				} else {
					headers += line + CRLF;
				}
				
				if (line.startsWith("Content-Length:") || line.startsWith("Content-length:")) {
					String[] tmp = line.split(" ");
					length = Integer.parseInt(tmp[1]);
				}

				line = fromServer.readLine();
			}

		} catch (IOException e) {
			System.out.println("Error reading headers from server: " + e);
			System.out.println();
			return;
		}

		try {
			int bytesRead = 0;
			byte buf[] = new byte[MAX_BUFFER_SIZE];
			boolean loop = false;

			if (length == -1) {
				loop = true;
			}

			while (bytesRead < length || loop) {
				int res = fromServer.read(buf, 0, MAX_BUFFER_SIZE);
				if (res == -1) {
					break;
				}

				for (int i = 0; i < res && (i + bytesRead) < MAX_OBJECT_SIZE; i++) {
					body[bytesRead + i] = buf[i];
				}

				bytesRead += res;
			}
		} catch (IOException e) {
			System.out.println("Error reading response body: " + e);
			System.out.println();
			return;
		}
	}

	public String toString() {
		String res = "";
		res = statusLine + CRLF;
		res += headers;
		res += CRLF;
		return res;
	}
}