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
    public static final int DEFAULT_IMEOUT=3000;

    public static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static ApiImp api;

    private static ConnectResult connectResult;
    private static ExecuteResult executeResult;

    private Exception externalEXCEPTION;

    public static ConnectResult newConnect(String ip,String admin,String password,int port,int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect2021(ip,admin,password,port,timeout);
        return connectResult;
    }

    public static ConnectResult newConnect(String ip,String admin,String password,int port){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect2021(ip,admin,password,port,DEFAULT_IMEOUT);
        return connectResult;
    }

    public static ConnectResult newConnect(String ip,String admin,String password){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect2021(ip,admin,password,DEFAULT_PORT,DEFAULT_IMEOUT);
        return connectResult;
    }

    public static ConnectResult newConnectDefaultPort(String ip,String admin,String password,int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect2021(ip,admin,password,DEFAULT_PORT,timeout);
        return connectResult;
    }

    public static ExecuteResult newExecute(ApiImp apiImp,String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute2021(apiImp,cmd);
        return executeResult;
    }

    public static ExecuteResult newExecute(String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute2021(api,cmd);
        return executeResult;
    }

    private void setupConnect2021(String ip,String admin,String password,int port,int timeout){
        Connector2021 connector2021 = new Connector2021(new String[]{ip,admin,password},new int[]{port,timeout});
        connectResult = new ConnectResult();
        Future<ApiImp> apiImpFuture = executorService.submit(connector2021);

        try {
            api = apiImpFuture.get();
            connectResult.setApi(api);
        } catch (ExecutionException e) {
            connectResult.setException(e);
        } catch (InterruptedException e) {
            connectResult.setException(e);
        }

    }

    private void setupExecute2021(ApiImp apiImp,String cmd){

        Executor2021 executor2021 = new Executor2021(apiImp,cmd);

        executeResult = new ExecuteResult();

        Future<List<Map<String, String>>> listFuture = executorService.submit(executor2021);

        try {
            executeResult.setMapList(listFuture.get());
        } catch (ExecutionException e) {
            executeResult.setException(e);
        } catch (InterruptedException e) {
            executeResult.setException(e);
        }

    }
    @Deprecated
    private void setupConnect( String ip, String username, String password,int port,int timeout) {

        String[] strings = new String[]{ip, username, password};

        Connector connector = new Connector(port, timeout);

            try {
                api = (ApiImp) connector.execute(strings).get();
                connectResult = new ConnectResult();
                connectResult.setApi(api);

            } catch (InterruptedException e) {

               connectResult.setException(e);

            } catch (ExecutionException e) {

                connectResult.setException(e);
            }
            if (connector.getExternalExceptionFromConnector() != null){
                externalEXCEPTION = connector.getExternalExceptionFromConnector();
                connectResult.setException(externalEXCEPTION);
            }
    }

    @Deprecated
    public static ConnectResult connect(String ip, String username, String password, int port, int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,username,password,port,timeout);
        return connectResult;
    }

    @Deprecated
    public static ConnectResult connect(String ip, String admin, String password, int port){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,port,DEFAULT_IMEOUT);
        return connectResult;
    }

    @Deprecated
    public static ConnectResult connect(String ip, String admin, String password){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,DEFAULT_PORT,DEFAULT_IMEOUT);
        return connectResult;
    }

    @Deprecated
    private void setupExecute( Api readyApi, String cmd) {

        Executor executor = new Executor(cmd);

            try {

                List<Map<String, String>> mapList = executor.execute(readyApi).get();
                executeResult = new ExecuteResult();
                executeResult.setMapList(mapList);

            } catch (ExecutionException e) {

                executeResult.setException(e);

            } catch (InterruptedException e) {

               executeResult.setException(e);

            }
            if (executor.getExternalExceptionFromExecutor()!= null) {
                externalEXCEPTION = executor.getExternalExceptionFromExecutor();
                executeResult.setException(externalEXCEPTION);
            }
    }

    @Deprecated
    public static ExecuteResult execute(Api apiLocal, String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute(apiLocal,cmd);
        return executeResult;
    }

    @Deprecated
    public static ExecuteResult execute(String cmd){

        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute(api,cmd);
        return executeResult;

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
