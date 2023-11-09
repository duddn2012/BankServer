package org.example.socket;

import org.example.account.domain.*;
import org.example.router.RequestRouter;
import org.example.user.User;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;

public class ClientThread implements Runnable{

    private Socket clientSocket;
    private RequestRouter router;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
        ){
            // router는 client별로 생성해야함
            router = new RequestRouter(clientSocket, out, new User());

            while(true){
                String msg = in.readLine();
                System.out.println(msg);
                router.routing(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
