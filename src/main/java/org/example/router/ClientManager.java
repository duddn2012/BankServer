package org.example.router;

import org.example.account.domain.Account;

import java.util.ArrayList;

/**
 * 접속 중인 Client 리스트 저장
 */
public class ClientManager {
    private static final ClientManager instance = new ClientManager();
    private ArrayList<Client> connectedClients = new ArrayList<>();
    private Object connectedClientsLock = new Object();

    private ClientManager() {}

    public static ClientManager getInstance() {
        if(instance == null){
            return new ClientManager();
        }
        return instance;
    }

    public void addClient(Client client) {
        synchronized (connectedClientsLock) {
            connectedClients.add(client);
        }
    }

    public void deleteClient(Client client) {
        synchronized (connectedClientsLock) {
            connectedClients.remove(client);
        }
    }

    public Account getAccount(String userId){
        for(Client client: connectedClients){
            if(client.getAccount().getUserId().equals(userId)){
                return client.getAccount();
            }
        }
        return null;
    }

    public Client getClientByUserId(String userId){
        for(Client client: connectedClients){
            if(client.getUser().getUserId().equals(userId)){
                return client;
            }
        }
        return null;
    }
}
