import java.io.*;
import java.net.*;
import java.awt.Image;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.nio.charset.StandardCharsets;
@SuppressWarnings("unused")

public class WebClient_v2 {
	public  String getWebContentByGet(String urlString,   final String charset, int timeout) throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent","ComputerNetwork");
		conn.setRequestProperty("Accept", "test/html");
		conn.setConnectTimeout(timeout);
		
		try {
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (conn != null) {
			conn.disconnect();
		}
		
		return sb.toString();
	}
	
	public  String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		connection.setRequestProperty("User-Agent","ComputerNetwork");
		connection.setRequestProperty("Accept", "text/xml");
		connection.setConnectTimeout(timeout);
		connection.connect();
		
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		byte[] content = data.getBytes("UTF-8");
		System.out.println(content);
		out.write(content);
		out.flush();
		out.close();
		
		try {
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (connection != null) {
			connection.disconnect();
		}
		return sb.toString();  
	}
	
	public void v2_main () throws IOException {
		WebClient_v1 client = new WebClient_v1();
		Scanner scanner = new Scanner(System.in);
		String data = "2018009107";
		String urlstring, s;		
		
		System.out.println("[Step 2]");
		System.out.print("Input URL : ");
		urlstring = scanner.next();
		s = client.getWebContentByGet(urlstring, "UTF-8", 10000);
		System.out.println(s);
			
		System.out.println("[Step 3]");
		System.out.print("Input URL : ");
		urlstring = scanner.next();
		s = client.getWebContentByPost(urlstring, data, "UTF-8", 10000);
		System.out.println(s);
	}
}