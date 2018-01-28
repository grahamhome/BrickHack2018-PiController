import java.util.concurrent.ConcurrentLinkedQueue;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import java.io.IOException;
import java.util.Random;
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
		//(server = new Thread(new Server(PORT))).start();
		System.out.println("Successfully created a server");
		//while (true) {				
			try {

		            System.out.println("Creating I2C bus");
		            I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		            System.out.println("Creating I2C device");
		            I2CDevice device = bus.getDevice(0x04);

		            byte[] writeData = new byte[1];
		            long waitTimeSent = 5000;
		            long waitTimeRead = 5000;

		            while (true) {
		                //negative values don't work
		                new Random().nextBytes(writeData);
		                System.out.println("Writing " + writeData[0] + " via I2C");
		                device.write(writeData[0]);
		                System.out.println("Waiting 5 seconds");
		                Thread.sleep(waitTimeSent);
		                System.out.println("Reading data via I2C");
		                int dataRead = device.read();
		                System.out.println("Read " + dataRead + " via I2C");
		                System.out.println("Waiting 5 seconds");
		                Thread.sleep(waitTimeRead);
		            }
		        } catch (IOException ex) {
		        	System.out.println("error 1 occured");
		            ex.printStackTrace();
		        } catch (InterruptedException ex) {
		        	System.out.println("error 2 occured");
		            ex.printStackTrace();
		        } catch (UnsupportedBusNumberException e) {
		        	System.out.println("error 3 occured");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//nano}
			/*String command = commandQueue.poll();
			if (command != null) {
				int x = Integer.parseInt(command.split(",")[0]);
				int y = Integer.parseInt(command.split(",")[1]);*/
				// Now you can send these coordinates to the Arduino with I2C if you want, 
				// or use them to figure out a direction & distance command to send to the Arduino over I2C.
			

		//}
	}
}
