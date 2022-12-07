package sample;

import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket clientSocket;
    private final BufferedReader in; // поток чтения из сокета
    private final PrintWriter out; // поток записи в сокет


    public Client(String ip, int port) throws IOException {
        this.clientSocket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
    }

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
    }

    public String getLineFromServer() throws IOException {
        if(in.ready())
            return in.readLine();
        else
            return "";
    }

    public void printlnToServer(String line){
        try {
            out.println(line);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }
}
