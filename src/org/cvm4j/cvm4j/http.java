package org.cvm4j.cvm4j;

// Imports needed for HTTP/WS server

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.java_websocket.server.WebSocketServer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.io.FileNotFoundException;


public class http {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.arch"));
        System.out.println("JVM: " + System.getProperty("java.runtime.version"));
        System.out.println("[i] Loading configuration file");
        String cd = System.getProperty("user.dir");
        File configfile = new File(cd + "/cvm4j.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document xmldoc = dBuilder.parse(configfile);
        xmldoc.getDocumentElement().normalize();
        int temp = 0;
        NodeList list = xmldoc.getElementsByTagName("cvm4j");
        Node node = list.item(temp);
        Element element = (Element) node;
        System.out.println("[i] Root element: " + xmldoc.getDocumentElement().getNodeName());
        String wsaddr = element.getElementsByTagName("wslisten").item(0).getTextContent();
        String listenport = element.getElementsByTagName("wsport").item(0).getTextContent();
        System.out.println("[i] Configuration file loaded");
        System.out.println("[i] cvm4j build 0.4.0 (built 4/23/2022)");
        System.out.println("[i] Thanks to MDMCK10 for helping with this release!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("cvm4j crash report");
            System.out.println("**************************");
            System.out.println("cvm4j has crashed, network configuration or JAR modification might be the problem.\n\n" + e.toString());
            //e.printStackTrace();
        }
        httpstart();
        WebSocketServer srv = new WebsocketServer(new InetSocketAddress("0.0.0.0", Integer.parseInt(listenport)));
        srv.run();
        System.out.println("[i] HTTP Server: localhost:3400");
        System.out.println("[i] WebSocket Server: localhost:" + listenport);
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        InputStream data = org.cvm4j.cvm4j.http.class.getResourceAsStream("/org/cvm4j/cvm4j/www/index.html");
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = data.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }

        String response = result.toString("UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes().length); // Return status and length.
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void httpstart() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(3400), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(http::handleRequest);
        server.start();
    }

    public static class GuacUtil {
    }
}
