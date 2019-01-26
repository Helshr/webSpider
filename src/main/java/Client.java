import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String get(String uri) {
        try {
            setSocket(socket);
            socket = getSocket();
            boolean autoFlush = false;
            OutputStream out = socket.getOutputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            String requestUrl = "GET " + uri + " HTTP/1.1\r\n" + "Host: " + host + ":" + port + "\r\n\r\n";
            out.write(requestUrl.getBytes());
            boolean loop = true;
            StringBuffer sb = new StringBuffer(1024);
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
                System.out.println(sb);
            }
            socket.close();
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    public static void main(String[] args) {
        Client ws = new Client("154.8.131.165", 80);
        String r = ws.get("/top250?start=");
        System.out.println(r);
    }
}
