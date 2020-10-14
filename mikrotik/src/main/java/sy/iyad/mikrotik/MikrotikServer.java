package sy.iyad.mikrotik;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import sy.iyad.mikrotik.PreReady.ConnectResult;
import sy.iyad.mikrotik.PreReady.ExecuteResult;
import sy.iyad.mikrotik.PreReady.ServerConnector;
import sy.iyad.mikrotik.PreReady.ServerExecutor;
import sy.iyad.mikrotik.PreReady.Task;
import sy.iyad.mikrotik.Ready.Api;
import sy.iyad.mikrotik.Ready.ApiException;


public class MikrotikServer {

    public static final int DEFAULT_PORT=8728;
    public static final int DEFAULT_IMEOUT=3000;
    private static Api api;
    private static Task<ConnectResult> connectResultTask;
    private static Task<ExecuteResult> executeResultTask;
    private static List<Map<String, String>> mapList;
    private static Exception internalException;

    private void setupConnect( String ip, String username, String password,int port,int timeout){
        String[] strings = new String[]{ip,username,password};
        connectResultTask = new Task<>();
        try {
            api = new ServerConnector(port,timeout).execute(strings).get();
            connectResultTask.setSuccessful(true);
            ConnectResult connectResult = new ConnectResult();
            connectResult.setApi(api);
            connectResultTask.setResult(connectResult);
        } catch (InterruptedException e) {
            connectResultTask.setSuccessful(false);
            connectResultTask.setException(e);
           internalException = e;
        } catch (ExecutionException e) {
            connectResultTask.setSuccessful(false);
            connectResultTask.setException(e);
            internalException = e;
        }
    }

    public static Task<ConnectResult> connect( String ip, String username, String password,int port,int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,username,password,port,timeout);
        return connectResultTask;
    }

    public static Task<ConnectResult> connect( String ip, String admin, String password,int port){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,port,DEFAULT_IMEOUT);
        return connectResultTask;
    }

    public static Task<ConnectResult> connect( String ip, String admin, String password){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,DEFAULT_PORT,DEFAULT_IMEOUT);
        return connectResultTask;
    }

    private void setupExecute(@NonNull Api readyApi,@NonNull String cmd){
        executeResultTask = new Task<>();
        try {
            mapList = new ServerExecutor(cmd).execute(readyApi).get();
            ExecuteResult executeResult = new ExecuteResult();
            executeResult.setMapList(mapList);
            executeResultTask.setSuccessful(true);
            executeResultTask.setResult(executeResult);
        } catch (ExecutionException e) {
            executeResultTask.setSuccessful(false);
            executeResultTask.setException(e);
            internalException = e;
        } catch (InterruptedException e) {
            executeResultTask.setSuccessful(false);
            executeResultTask.setException(e);
            internalException = e;
        }
    }

    public static Task<ExecuteResult> execute( Api api, String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute(api,cmd);
        return executeResultTask;
    }

    public static Task<ExecuteResult> execute( String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
      if(api!=null){
       mikrotikServer.setupExecute(api,cmd);
      }else{
        try{
        throw new Exception("لا يوجد اتصال مسبق يرجى طلب connect");
      }catch(Exception ex){
        internalException =ex;
      }
    }
      return executeResultTask;
    }

    public static String disconnect(){
        try {
            api.close();
            return "disconnected";
        } catch (ApiException e) {
            internalException = e;
            return e.getMessage();
        }
    }

    /*public void addOnCompleteListener(@NonNull OnCompleteListener<ConnectResult> listener){

        Task<ConnectResult> task = new Task<>();
        if (api!= null){
            ConnectResult result = new ConnectResult();
            task.setSuccessful(true);
            result.setApi(api);
            task.setResult(result);
        }else if (internalException != null){
            task.setSuccessful(false);
            task.setException(internalException);
        }else if (ServerConnector.externalExceptionFromConnector != null){
            task.setSuccessful(false);
            task.setException(ServerConnector.externalExceptionFromConnector);
        }else {
            task.setSuccessful(false);
            task.setException(new ApiException("unknown exception"));
        }
        listener.onComplete(task);
    }*/
/*
    public ExecuteResult addOnCompleteListener(@NonNull OnCompleteListener<ExecuteResult> listener){
        Task<ExecuteResult> task = new Task<>();
        if (mapList != null) {
            task.setSuccessful(true);
            ExecuteResult result = new ExecuteResult();
            result.setMapList(mapList);
            task.setResult(result);
        } else if (internalException!=null) {
            task.setSuccessful(false);
            task.setException(internalException);
        }else if (ServerExecutor.externalExceptionFromExecutor!=null) {
            task.setSuccessful(false);
            task.setException(ServerExecutor.externalExceptionFromExecutor);
        } else {
            task.setSuccessful(false);
            task.setException(new ApiException("unknown Error in ServerExecutor StriaLink"));
        }
        listener.onComplete(task);
    }*/

}
