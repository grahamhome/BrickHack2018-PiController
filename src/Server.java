import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread to create a ServerSocket and create a new thread to handle each client which connects to it.
 * @author Graham Home
 *
 */
public class Server implements Runnable {
	
	public AtomicBoolean running = new AtomicBoolean(true);
	
	private int port;
	private ServerSocket serverSocket = null;
	private ArrayList<ClientCommunicator> connectedClients = new ArrayList<>();
	
	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Error when opening server socket");
			return;
		}
		while (running.get()) {
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
		closeEverything();
	}
	
	private void closeEverything() {
		for (ClientCommunicator client : connectedClients) {
			client.running.set(false);
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Error closing server socket");
		}
	}
}
