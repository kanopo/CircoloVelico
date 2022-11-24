package me.ollari.circolonauticogui;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpFunctions {

    public HttpResponse<String> Get(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public HttpResponse<String> Put(String url, String body) throws IOException, InterruptedException {
        // String body = "{\"name\":\"" + boatName + "\",\"length\":\"" + boatLenght + "\"}";
        HttpClient clientPut = HttpClient.newHttpClient();
        HttpRequest requestPut = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + url))
                .build();

        HttpResponse<String> responsePut = clientPut.send(requestPut, HttpResponse.BodyHandlers.ofString());

        return responsePut;
    }

    public boolean pingHost() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(Ip.getIp(), 8080), 1000);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}
