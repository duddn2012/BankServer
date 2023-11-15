package org.example.socket;

import org.example.account.domain.BankAccount;
import org.example.router.ClientManager;
import org.example.router.RequestRouter;
import org.example.user.User;

import java.io.*;
import java.net.Socket;

import static org.example.common.IOMessageUtil.printClientAction;

public class ClientThread implements Runnable{

    private Socket clientSocket;
    private RequestRouter router;
    private ClientManager clientManager = ClientManager.getInstance();

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
            router = new RequestRouter(out);

            while(true){
                //입력을 라우팅
                String message = in.readLine();
                printClientAction(String.valueOf(clientSocket.getInetAddress()),message);
                router.routing(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
