import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Thread to create a ServerSocket and create a new thread to handle each client which connects to it.
 * @author Graham Home
 *
 */
public class Server implements Runnable {
	private int port;
	private ArrayList<ClientCommunicator> connectedClients = new ArrayList<>();
	
	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Error when opening server socket");
			return;
		}
		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Error when opening server socket");
				return;
			}
			ClientCommunicator client = new ClientCommunicator(clientSocket);
			connectedClients.add(client);
			new Thread(client).start();
		}
		
	}
	
	private boolean isStopped() {
		// TODO: Implement an atomicboolean to be set by main thread
		return false;
	}

}
