import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * Thread to handle communications with a single client.
 * @author Graham Home
 *
 */
public class ClientCommunicator implements Runnable {
	
	private Socket clientSocket;
	InputStream input;
	OutputStream output; 
	
	public ClientCommunicator(Socket client) {
		this.clientSocket = client;
	}

	@Override
	public void run() {
		try {
			input = clientSocket.getInputStream();
			output = clientSocket.getOutputStream();
			System.out.println("Opened connection with client " + clientSocket.getInetAddress());
			while (!isStopped()) {
				byte[] inputData = new byte[2];
				input.read(inputData);
				System.out.println("Got " + (int)inputData[0] + "," + (int)inputData[1] + " from " + clientSocket.getInetAddress());
			}
			closeEverything();
			return;
		} catch (Exception e) {
			System.out.println("Error reading from client " + clientSocket.getInetAddress());
			closeEverything();
			return;
		}

	}
	
	private boolean isStopped() {
		// TODO: Implement an atomicboolean to be set by server thread
		return false;
	}
	
	private void closeEverything() {
		try {
			input.close();
			output.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Error closing connection with client " + clientSocket.getInetAddress());
		}
	}

}
