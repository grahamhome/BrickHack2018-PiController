import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Thread to handle communications with a single client.
 * @author Graham Home
 *
 */
public class ClientCommunicator implements Runnable {
	
	public AtomicBoolean running = new AtomicBoolean(true);
	
	private Socket clientSocket;
	InputStream input;
	InputStreamReader inputStream;
	BufferedReader inputStreamReader;
	OutputStream output; 
	OutputStreamWriter outputStream;
	BufferedWriter outputStreamWriter;
	
	public ClientCommunicator(Socket client) {
		this.clientSocket = client;
	}

	@Override
	public void run() {
		try {
			inputStreamReader = new BufferedReader(inputStream = new InputStreamReader(input = clientSocket.getInputStream()));
			outputStreamWriter = new BufferedWriter(outputStream = new OutputStreamWriter(output = clientSocket.getOutputStream()));
			System.out.println("Opened connection with client " + clientSocket.getInetAddress().getHostAddress().toString());
			while (running.get()) {
				String input;
				if ((input = inputStreamReader.readLine()) != null) {
					System.out.println("Got " + input + " from " + clientSocket.getInetAddress().getHostAddress().toString());
				} else {
					System.out.println("Connection closed by client");
					running.set(false);
					break;
				}
			}
			closeEverything();
		} catch (Exception e) {
			System.out.println("Error interacting with client " + clientSocket.getInetAddress());
			closeEverything();
			return;
		}

	}
	
	private void closeEverything() {
		try {
			inputStreamReader.close();
			inputStream.close();
			input.close();
			outputStreamWriter.close();
			outputStream.close();
			output.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Error closing connection with client " + clientSocket.getInetAddress());
		}
	}

}
