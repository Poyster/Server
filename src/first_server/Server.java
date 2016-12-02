package first_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {


    public String loadContacts() {
        String externalContacts = "";
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File("ExternalRegister.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            externalContacts += scanner.nextLine().replace(',', ' ') + "\n";
        }
          scanner.close();
        return externalContacts;

    }


    public void serverTest() {
        try {

            ServerSocket serverSocket = new ServerSocket(61616);

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

                                        if (line.equals("getAll")) {

                                            writer.println(loadContacts());

                                            writer.flush();
                                            System.out.println("contacts sent to addressbook");
                                        }
                                        if (line.equals("exit")) {
                                            break;
                                        }
                                    }

                                    reader.close();
                                    writer.close();
                                    clientSocket.close();
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
