package it.denv.languagetool.languageserver;

import akka.io.TcpListener;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class App {
    public static void main(String[] args) {
        System.out.println("App.main");

        int port = 8081;
        if(args.length != 0){
            port = Integer.parseInt(args[0]);
        }

        String host = "localhost";

        if(args.length == 2){
            host = args[1];
        }

        try {
            ServerSocket s = new ServerSocket(port);
            while(true){
                Socket client = s.accept();
                System.out.println("New client: " + client.getInetAddress().getHostAddress() + ":" +
                        client.getPort());
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();

                LanguageToolLanguageServer server = new LanguageToolLanguageServer();
                Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, in, out);

                LanguageClient languageClient = launcher.getRemoteProxy();
                server.connect(languageClient);
                launcher.startListening();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
