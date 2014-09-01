/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package first_http_server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

/**
 *
 * @author Seb
 */
public class First_HTTP_server {

    /**
     * @param args the command line arguments
     */
    static int port = 8080;
    static String ip = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        if (args.length >= 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        InetSocketAddress i = new InetSocketAddress(ip, port); //localhost - 127.0.0.1
        HttpServer server = HttpServer.create(i, 0);
        server.createContext("/welcome", new WelcomeHandler());
        server.createContext("/headers", new HeadersHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Started the server, listening on:");
        System.out.println("port: " + port);
        System.out.println("ip: " + ip);
    }

    static class WelcomeHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("<h1>Welcome to my very first home made Web Server :-)</h1>\n");
            sb.append("</body>\n");
            sb.append("</html>\n");
            String response = sb.toString();
            
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response);
            };
        }
    }
    
    static class HeadersHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>Headers</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("<table border ='1'>\n");
            sb.append("<tr>\n");
            sb.append("<th>Header</th>\n");
            sb.append("<th>Value</th>\n");
            sb.append("</tr>\n");

            for (String key : he.getRequestHeaders().keySet()) {
                sb.append("<tr>\n");
                sb.append("<td>" + key + "</td>\n");
                sb.append("<td>" + he.getRequestHeaders().get(key) + "</td>\n");
                sb.append("</tr>\n");
            }
            
            sb.append("</table>\n");
            sb.append("</body>\n");
            sb.append("</html>\n");
            String response = sb.toString();
            
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response);
            };
        }
    }
    
}
