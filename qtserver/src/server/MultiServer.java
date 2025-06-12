package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe MultiServer inizializza la porta al valore 8080 ed invoca run().
 */
@SuppressWarnings("ucd")
class MultiServer {

	/** L'attributo PORT indica la porta del server. In questo caso 8080. */
	private int PORT = 8080;

	/**
	 * Il metodo main() istanzia un oggetto di tipo Multiserver.
	 *
	 * @param args argomento
	 * @throws IOException eccezione I/O
	 */
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		MultiServer server = new MultiServer();
	}

	/**
	 * Costruttore della classe MultiServer che invoca run().
	 *
	 * @throws IOException eccezione I/O
	 */
	public MultiServer() throws IOException {
		run();
	}

	/**
	 * Il metodo run() istanzia un oggetto ServerSocket, il quale rimane in attesa
	 * di una nuova connessione entrante. Ad ogni nuova richiesta di connessione
	 * istanzia un oggetto socket per comunicare con il client, passandolo poi come
	 * parametro al costruttore di ServerOneClient ServerOneClient, che eseguirà il
	 * resto delle operazioni di scambio dati tramite quella specifica socket.
	 *
	 * 
	 * @throws IOException eccezione I/O
	 */
	private void run() throws IOException {
		ServerSocket serv = new ServerSocket(PORT);
		Socket socket = null;

		try {
			System.out.println("In attesa di un client...");
			while (true) {
				socket = serv.accept();
				System.out.println("Client connesso");

				try {
					new ServerOneClient(socket);
				} catch (IOException e) {
					socket.close();
				}
			}
		} catch (IOException e) {
			System.out.println("Errore di connessione");
		} finally {
			try {
				serv.close();
			} catch (IOException e) {
				System.out.println("Errore nella chiusura della connessione");
			}
		}

	}

}
