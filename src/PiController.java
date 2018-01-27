import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * This class starts up a couple of threads to:
 * 1. Listen for and manage incoming TCP connections and accept data from them
 * 2. Take movement instructions as they become available and send them to an Arduino via I2C.
 * @author Graham Home
 *
 */
public class PiController {
	
	private static final int PORT = 6789;

	public static void main(String[] args) {
		Thread server;
		(server = new Thread(new Server(PORT))).start();
		try {
			InetAddress reachableAddress = null;
			Enumeration<InetAddress> addresses = NetworkInterface.getByName("wlan0").getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress address = addresses.nextElement();
				if (!address.isLoopbackAddress()) {
					reachableAddress = address;
					break;
				}
			}
			if (reachableAddress == null) {
				System.out.println("Unable to get non-loopback IP address");
			} else {
				System.out.println("Successfully created a server at " + reachableAddress + " on port " + PORT);
				server.join();
			}
		} catch (InterruptedException e) {
			System.out.println("Error joining with server thread");
		} catch (SocketException e) {
			System.out.println("Error getting IP address");
		}
	}
}
