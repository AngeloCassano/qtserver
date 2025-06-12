package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

import data.Data;
import data.EmptyDatasetException;
import mining.ClusteringRadiusException;
import mining.QTMiner;

/**
 * La classe ServerOneClient estende la classe Thread per gestire le richieste
 * dal client.
 */
class ServerOneClient extends Thread {

	/** Socket. */
	private Socket socket;

	/** Stream di input. */
	private ObjectInputStream in;

	/** Stream di output. */
	private ObjectOutputStream out;

	/** kmeans oggetto di tipo QTMiner. */
	private QTMiner kmeans;

	/**
	 * Il costruttore di classe ServeOneClient() solleva l'eccezione IOException,
	 * inizializza gli attributi socket, gli stream in e out e avvia il thread.
	 *
	 * 
	 * @param s socket
	 * @throws IOException eccezione I/O
	 */
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}

	/**
	 * Il metodo getTableFromDb() controlla se la tabella indicata è presente nel
	 * database e ne acquisisce i dati, inviando poi al client una stringa di
	 * conferma in caso affermativo o sollevando l'eccezione EmptyDatasetException
	 * se il dataset è vuoto.
	 *
	 * @return tabella del database
	 */
	private Data getTableFromDb() throws EmptyDatasetException {
		String tabName;
		Data data = null;
		try {
			tabName = (String) in.readObject();
			data = new Data(tabName);
			out.writeObject("OK");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 
	 * Il metodo learningFromDbTable() riceve in input i dati acquisiti dalla
	 * tabella specificata in precedenza ed esegue le operazioni di clustering in
	 * base ai dati ricevuti dal client.
	 * 
	 * @param data dati letti dalla tabella
	 */
	private void learningFromDbTable(Data data) {
		try {
			double r = (double) in.readObject();
			kmeans = new QTMiner(r);
			int numC = kmeans.compute(data);
			out.writeObject("OK");
			out.writeObject(numC);
			out.writeObject(kmeans.toString());
		} catch (ClusteringRadiusException e) {
			try {
				out.writeObject("Errore 1: un solo cluster");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Il metodo storeClusterInFile() memorizza i cluster in un file il cui nome è
	 * stato ricevuto dal client. Invia al client una stringa di conferma oppure di
	 * errore a seconda della terminazione del salvataggio.
	 */
	private void storeClusterInFile() {
		try {
			String fileName = ((String) in.readObject());
			kmeans.salva(fileName);
			out.writeObject("OK");
		} catch (IOException e) {
			try {
				out.writeObject("Errore 2 nel salvataggio");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Il metodo learningFromFile() acquisisce da file.
	 */
	private void learningFromFile() {
		try {
			String fileName = ((String) in.readObject());
			kmeans = new QTMiner(fileName);
			out.writeObject("OK");
			out.writeObject(kmeans.toString());
		} catch (FileNotFoundException e) {
			try {
				out.writeObject("Errore 3: file non trovato");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Il metodo run(), riscrive il metodo run della superclasse Thread, per gestire
	 * le richieste del client.
	 */
	@Override
	public void run() {
		Integer res = null;
		Data data = null;
		while (true) {
			try {
				res = (Integer) in.readObject();
			} catch (SocketException e) {
				try {
					socket.close();
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			while (true) {
				switch (res) {
				case 0:
					try {
						data = getTableFromDb();
					} catch (EmptyDatasetException e1) {
						try {
							out.writeObject("Errore 0: tabella non trovata");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					res = 4;
					break;
				case 1:
					learningFromDbTable(data);
					res = 4;
					break;
				case 2:
					storeClusterInFile();
					res = 4;
					break;
				case 3:
					learningFromFile();
					res = 4;
					break;
				case 5:
					try {
						out.writeObject(5);
						socket.close();
						System.out.println("Chiusa connessione con client");
						return;
					} catch (IOException e) {
					}
				}
				if (res == 4) {
					break;
				}
			}
		}
	}

}
