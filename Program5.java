import java.io.*;
import java.net.*;
public class Program5 {

public void run(int port, String rootFolder) throws Exception {
	ServerSocket SVRSOCK = new ServerSocket(port);
	Socket SOCK = SVRSOCK.accept();
	InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
	BufferedReader BR = new BufferedReader(IR);
	
	
	String message = BR.readLine();
	System.out.println(message);
	SVRSOCK.close();
}
	
	public static void main(String[] args) throws Exception {

		Program5 svr = new Program5();
		svr.run(2112, "Documents");

	}

}
