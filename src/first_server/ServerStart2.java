package first_server;

public class ServerStart2 {

    public static void main(String[] args) {

        Server server2 = new Server("ExternalRegister2.csv", 61614);
        server2.serverHandler();
    }
}
