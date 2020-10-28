package sy.iyad.mikrotik;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import sy.iyad.mikrotik.Models.ConnectResult;
import sy.iyad.mikrotik.Models.ExecuteResult;
import sy.iyad.mikrotik.Models.Connector;
import sy.iyad.mikrotik.Models.Executor;
import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.mikrotik.Utils.ApiException;


public class MikrotikServer {


    public static final int DEFAULT_PORT=8728;
    public static final int DEFAULT_IMEOUT=3000;

    private static Api api;

    private static ConnectResult connectResult;
    private static ExecuteResult executeResult;

    private Exception externaleXCEEPTION;


    private void setupConnect( String ip, String username, String password,int port,int timeout) {

        String[] strings = new String[]{ip, username, password};

        Connector connector = new Connector(port, timeout);

            try {
                api = connector.execute(strings).get();
                connectResult = new ConnectResult();
                connectResult.setApi(api);

            } catch (InterruptedException e) {

               connectResult.setException(e);

            } catch (ExecutionException e) {

                connectResult.setException(e);
            }
            if (connector.getExternalExceptionFromConnector() != null){
                this.externaleXCEEPTION = connector.getExternalExceptionFromConnector();
                connectResult.setException(externaleXCEEPTION);
            }
    }

    public static ConnectResult connect(String ip, String username, String password, int port, int timeout){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,username,password,port,timeout);
        return connectResult;
    }

    public static ConnectResult connect(String ip, String admin, String password, int port){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,port,DEFAULT_IMEOUT);
        return connectResult;
    }

    public static ConnectResult connect(String ip, String admin, String password){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupConnect(ip,admin,password,DEFAULT_PORT,DEFAULT_IMEOUT);
        return connectResult;
    }

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
                this.externaleXCEEPTION = executor.getExternalExceptionFromExecutor();
                executeResult.setException(externaleXCEEPTION);
            }
    }

    public static ExecuteResult execute(Api apiLocal, String cmd){
        MikrotikServer mikrotikServer = new MikrotikServer();
        mikrotikServer.setupExecute(apiLocal,cmd);
        return executeResult;
    }

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
