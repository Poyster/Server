package first_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {

    private String fileName;
    private int serverPort;


    public Server(String fileName, int serverPort) {
        this.fileName = fileName;
        this.serverPort = serverPort;
    }

    public String loadContacts() {
        String externalContacts = "";
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            externalContacts += scanner.nextLine().replace(',', ' ') + "\n";
        }
        scanner.close();
        return externalContacts;
    }

    public void serverHandler() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(
                        new Runnable() {

                            public void run() {
                                try {
                                    InputStream inputStream = clientSocket.getInputStream();
                                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                                    OutputStream outputStream = clientSocket.getOutputStream();
                                    PrintWriter writer = new PrintWriter(outputStream);
                                    BufferedReader reader = new BufferedReader(inputStreamReader);

                                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                                        if (line.equals("getall")) {
                                            writer.println(loadContacts());
                                            System.out.println("Contacts sent to Address Book.");
                                            writer.flush();
                                        }
                                        if (line.equals("exit")) {
                                            System.out.println("Server task finished, closing down connection.");
                                            reader.close();
                                            writer.close();
                                            clientSocket.close();
                                            break;
                                        }
                                    }
                                } catch (SocketException e) {
                                    System.out.println("Connection to Address Book lost.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
