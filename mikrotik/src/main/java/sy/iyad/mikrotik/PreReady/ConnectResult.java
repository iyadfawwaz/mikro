package sy.iyad.mikrotik.PreReady;


import androidx.annotation.NonNull;

import sy.iyad.mikrotik.Ready.Api;

public class ConnectResult {
    private Api api;
    public ConnectResult(){

    }

    public Api getApi() {
        return api;
    }

    public void setApi( @NonNull Api api) {
        this.api = api;
    }
}
