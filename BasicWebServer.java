import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicWebServer {


	private static String [ ] request = new String[3];
	static String directory = "";
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

					System.out.println(directory + request[1]);
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

						try( FileReader reader = new FileReader( fin) ) {
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
					}

				} catch ( Exception e ) {
					e.printStackTrace( );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	public void handleGET() {
		File dir = new File(directory);
		File[] directoryListing = dir.listFiles();

		if ( request[ 1 ].equals( "/" ) ) {
			request[ 1 ] += "index";
			request[ 1 ] = request[ 1 ].substring( 1 ) + ".html";
		} else { 

			for(File child : directoryListing) {
				child.getName();
				if (request[1].equals(child)) {
					request[1] = child.getName();	
				}
			} 

		}






	}

	public static void main( String [ ] args ) {
		BasicWebServer self = new BasicWebServer( );
		directory = args[ 1 ];
		self.server( Integer.parseInt( args[ 0 ] ) );

	}

}
