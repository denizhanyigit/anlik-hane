package com.dyapp.anindaev.RestApi;

public class BaseManager {

    protected RestApi getRestApiClient()
    {
        RestApiClient restApiClient = new RestApiClient(BaseUrl.bilgi_Url);
        return restApiClient.getmRestApi();
    }
}
