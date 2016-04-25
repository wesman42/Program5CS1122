import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Program5 {

	private static String [ ] request = new String[3];
	public static String directory = "";
	public static LinkedList<Orders> outstandingOrders = new LinkedList<Orders>();
	public static LinkedList<Orders> fulfilledOrders = new LinkedList<Orders>();
	public static LinkedList<InventoryItem> inventoryList = new LinkedList<InventoryItem>();
	static File currentPage = null;

	public void server( int port ) {

		try ( ServerSocket server = new ServerSocket( port ); ) {
			String html = "<HTML><HEAD><TITLE>Generated Page</TITLE></HEAD><BODY><H1>Hello World!</H!></BODY></HTML>";
			boolean running = true;


			while ( running ) {

				try ( Socket client = server.accept( );
						BufferedReader input = new BufferedReader( new InputStreamReader ( client.getInputStream( ) ) );
						PrintWriter output = new PrintWriter( client.getOutputStream( ) ); 

						) {

					String inp = "";


					while ( ( inp = input.readLine( ) ) != null ) {
						System.out.println( ">> " + inp );

						if ( "" .equals( inp ) ) {
							break;

						} else if ( inp.contains( "GET" ) ) {
							request = inp.split( " " );
						}
					}              

					handleGET();


					File fin = new File( directory + "/" + request[ 1 ] );
					if ( !fin.exists( ) ) {
						output.println("HTTP/1.0 404 Not Found");
						output.println("Connection: close");
						output.println("");
						output.flush();

					} else {
						output.println("HTTP/1.0 200 OK");
						output.println("Content-Type: text/html");
						output.println("Content-Length: " + fin.length( ) );
						output.println("Connection: close");
						output.println("");

						if (currentPage != null) {
							fin = currentPage;
						}
						try( FileReader reader = new FileReader( fin ) ) {
							html = "";

							while( reader.ready( ) ) {
								html += (char) reader.read( );
							}


						} catch (FileNotFoundException e) {
							e.printStackTrace( );
						} catch ( IOException e ) {
							e.printStackTrace( );
						}
						output.println( html );
						System.out.println( ">" + html );
						output.println("");
						output.flush( );
						currentPage = null;
					}

				} catch ( Exception e ) {
					e.printStackTrace( );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	public void handleGET()throws FileNotFoundException {
		File dir = new File(directory);
		File[] directoryListing = dir.listFiles();

		if ( request[ 1 ].equals( "/" ) ) {
			request[ 1 ] += "index";
			request[ 1 ] = request[ 1 ].substring( 1 ) + ".html";
		} else { 

			for(File child : directoryListing) {
				
				if (request[1].contains("outstanding_orders") && child.getName().equals(request[1])) {
					
					FormProcessor processData = new FormProcessor(child,request[1]);
					request[1] = processData.getOutstanding().getName();
					currentPage = processData.getOutstanding();
					processData.checkFulfillmentStatus();
					
				} else if(request[1].contains("fulfilled_orders")&& child.getName().equals(request[1])) {
					
					FormProcessor processData = new FormProcessor(child,request[1]);
					request[1] = processData.getFulfillmentList().getName();
					currentPage = processData.getFulfillmentList();
					
				}else if ( request[1].length() > 30 && (request[1].contains("new_order") || request[1].contains("add_stock") ) ) {
					FormProcessor processData = new FormProcessor(child,request[1]);

					if(!request[1].contains("CostPer=")) {
						outstandingOrders.add(processData.getOrder());
						System.out.println(outstandingOrders.size());
						request[1] = processData.generateOrderUpdate().getName();
						currentPage = processData.generateOrderUpdate();
					} else {
						inventoryList.add(processData.getInventoryItem());
						request[1] = processData.generateInventoryUpdate().getName();
						currentPage = processData.generateInventoryUpdate();
					}
				} 
			}
		}






	}

	public void getFulfillment(File file ) {

	}

	public static void main( String [ ] args ) {
		directory = args[ 1 ];
		
		Program5 self = new Program5( );
		
		self.server( Integer.parseInt( args[ 0 ] ) );

	}


}
