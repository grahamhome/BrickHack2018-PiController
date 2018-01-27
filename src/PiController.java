
/**
 * This class starts up a couple of threads to:
 * 1. Listen for and manage incoming TCP connections and accept data from them
 * 2. Take movement instructions as they become available and send them to an Arduino via I2C.
 * @author Graham Home
 *
 */
public class PiController {

	public static void main() {
		Thread server;
		(server = new Thread(new Server(6789))).start();
		try {
			server.join();
		} catch (InterruptedException e) {
			System.out.println("Error joining with server thread");
		}
	}
}
