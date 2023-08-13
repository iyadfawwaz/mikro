package sy.iyad.mikrotik;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sy.iyad.mikrotik.Models.*;
import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.mikrotik.Utils.ApiException;
import sy.iyad.mikrotik.Utils.ApiImp;


public class MikrotikServer {


    public static final int DEFAULT_PORT=8728;
    public static final int DEFAULT_TIMEOUT=3000;

    public static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static ApiImp api;

    private static ConnectResult connectResult;
    private static ExecuteResult executeResult;

    private Exception exception;

    public static ConnectResult connect(String ip,String admin,String password,int port,int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,port,timeout);
        return connectResult;
    }

    public static ConnectResult connect(String ip,String admin,String password,int port){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,port,DEFAULT_TIMEOUT);
        return connectResult;
    }

    public static ConnectResult connect(String ip,String admin,String password){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,DEFAULT_PORT,DEFAULT_TIMEOUT);
        return connectResult;
    }

    public static ConnectResult connectWithDefaultPort(String ip,String admin,String password,int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,DEFAULT_PORT,timeout);
        return connectResult;
    }

    public static ExecuteResult execute(ApiImp apiImp,String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute(apiImp,cmd);
        return executeResult;
    }

    public static ExecuteResult execute(String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute(api,cmd);
        return executeResult;
    }

    private void setupConnect(String ip,String admin,String password,int port,int timeout){
        Connector connector = new Connector(new String[]{ip,admin,password},new int[]{port,timeout});
        connectResult = new ConnectResult();
        Future<ApiImp> apiImpFuture = executorService.submit(connector);

        try {
            api = apiImpFuture.get();
            connectResult.setApi(api);
        } catch (ExecutionException | InterruptedException e) {
            connectResult.setException(e);
        }

    }

    private void setupExecute(ApiImp apiImp,String cmd){

        Executor executor = new Executor(apiImp,cmd);

        executeResult = new ExecuteResult();

        Future<List<Map<String, String>>> listFuture = executorService.submit(executor);

        try {
            executeResult.setMapList(listFuture.get());
        } catch (ExecutionException | InterruptedException e) {
            executeResult.setException(e);
        }

    }

    public static Api getApi() {
        return api;
    }


    public static String disconnect(){
        try {
            api.close();
            return "disconnected";
        } catch (ApiException e) {

            return e.getMessage();
        }
    }

}
