package first_server;

public class ServerStart1 {

    public static void main(String[] args) {

        Server server = new Server("ExternalRegister.csv", 61616);
        server.serverHandler();

    }
}
