import java.io.*;
import java.util.*;

public class main {
	public static void main (String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Select Auto Marking Server version (1 or 2) : ");
		int v = scanner.nextInt();
		if(v == 1) {
			WebClient_v1 v1 = new WebClient_v1();
			try {
				v1.v1_main();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(v == 2) {
			WebClient_v2 v2 = new WebClient_v2();
			try {
				v2.v2_main();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
}
