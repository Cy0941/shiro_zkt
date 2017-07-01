package cn.cxy.shiro.ch17.service;


import cn.cxy.shiro.ch17.entity.Client;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface ClientService {

    public Client createClient(Client client);

    public Client updateClient(Client client);

    public void deleteClient(Long clientId);

    Client findOne(Long clientId);

    List<Client> findAll();

    Client findByClientId(String clientId);

    Client findByClientSecret(String clientSecret);

}
