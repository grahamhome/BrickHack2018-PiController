import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class starts up a couple of threads to:
 * 1. Listen for and manage incoming TCP connections and accept data from them
 * 2. Take movement instructions as they become available and send them to an Arduino via I2C.
 * @author Graham Home
 *
 */
public class PiController {
	
	private static final int PORT = 6789;
	
	public static ConcurrentLinkedQueue<String> commandQueue = new ConcurrentLinkedQueue<>();

	public static void main(String[] args) {
		Thread server;
		(server = new Thread(new Server(PORT))).start();
		System.out.println("Successfully created a server");
		while (true) {
			String command = commandQueue.poll();
			if (command != null) {
				int x = Integer.parseInt(command.split(",")[0]);
				int y = Integer.parseInt(command.split(",")[1]);
				// Now you can send these coordinates to the Arduino with I2C if you want, 
				// or use them to figure out a direction & distance command to send to the Arduino over I2C.
			}
		}
	}
}
