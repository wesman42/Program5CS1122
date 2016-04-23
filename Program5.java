import java.io.*;
import java.net.*;
public class Program5 {
//test comment for git change
	private static ServerSocket SVRSOCK;
	
public void run(String docDir) throws IOException {
 
	Socket SOCK = SVRSOCK.accept();
	InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
	BufferedReader BR = new BufferedReader(IR);
	
	
	String message = BR.readLine();
	System.out.println(message);
	
    while (message != null) {
        if (message.contains("GET / HTTP/1.1")) {
        	
        }
        if (message.isEmpty()) {
            break;
        }
    }
}

public void setNewOrder() {
	
}

public void getFulfilledOrders(){
	
}

public void getInventory() {
	
}

	
	public static void main(String[] args) throws IOException {
		SVRSOCK =  new ServerSocket(Integer.parseInt(args[0]));
		Program5 svr = new Program5();
		svr.run(args[1]);

	}

}
