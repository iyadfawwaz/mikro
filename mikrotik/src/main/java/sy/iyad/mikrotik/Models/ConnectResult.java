package sy.iyad.mikrotik.Models;



import sy.iyad.mikrotik.Utils.Api;


public class ConnectResult {

    private Api api;
    private Exception exception;

    public ConnectResult(){

    }

    public Api getApi() {
        return api;
    }

    public void setApi(  Api api) {
        this.api = api;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    public void addConnectionEventListener(ConnectionEventListener listener){

        if (getApi() != null){
            listener.onConnectionSuccess(api);
        }else {
            listener.onConnectionFailed(exception);
        }
    }
}
