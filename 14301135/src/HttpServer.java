/**
 * Created by lenovo on 2016/10/7.
 */

import java.net.*;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpServer {
    public boolean shutDown = false;
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";


    public static void main(String[] args) {
        JSPEngine jspEngine = new JSPEngine();
        jspEngine.compile();
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        while (shutDown != true) {
            Socket socket;
            InputStream inputStream;
            OutputStream outputStream;
            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                Request request = new Request(inputStream);
                request.parse();
                if (request.getUri().equals(SHUTDOWN_COMMAND)) {
                    break;
                }
                Response response = new Response(outputStream);
                response.setRequst(request);

                if (request.getUri().startsWith("/Login")) {
                    Container container = new Container();
                    container.process(request, response);
                } else if (request.getUri().endsWith(".jsp")) {
                    JSPEngine jspEngine = new JSPEngine();
                    jspEngine.process(request, response);
                } else {
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request, response);
                }

                socket.close();
                shutDown = request.getUri().equals(SHUTDOWN_COMMAND);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
