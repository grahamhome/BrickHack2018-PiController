import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class starts up a couple of threads to:
 * 1. Listen for and manage incoming TCP connections and accept data from them
 * 2. Take movement instructions as they become available and send them to an Arduino via I2C.
 * @author Graham Home
 *
 */
public class PiController {
	
	private static final int PORT = 6789;

	public static void main() {
		Thread server;
		(server = new Thread(new Server(PORT))).start();
		try {
			System.out.println("Successfully created a server at " + InetAddress.getLocalHost().getHostAddress() + " on port " + PORT);
			server.join();
		} catch (InterruptedException e) {
			System.out.println("Error joining with server thread");
		} catch (UnknownHostException e) {
			System.out.println("Error identifying localhost");
		}
	}
}
