package com.alexandra.jellybeanstore.repositories;

import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.ClienteApiService;

public class ClienteRepository {

private ClienteApiService service;

public ClienteRepository(){
    service = ApiClient.getClient().create(ClienteApiService.class);
}

}
