package by.mbicycle.EchoServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		int port = 8020;
		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Waiting for a client...");

			Socket socket = ss.accept();
			System.out.println("Got a client...");
			System.out.println();

			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();

			DataInputStream in = new DataInputStream(sin);
			DataOutputStream out = new DataOutputStream(sout);

			String line = null;
			while (true) {
				line = in.readUTF();
				System.out.println("Client sent this line : " + line);
				System.out.println("Sending it back...");
				out.writeUTF(line);
				out.flush();
				System.out.println("Waiting for the next line...");
				System.out.println();
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}