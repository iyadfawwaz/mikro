package sy.iyad.mikrotik.Models;

import java.util.List;
import java.util.Map;


public class ExecuteResult {

    private List<Map<String,String>> mapList;
    private Exception exception;

    public ExecuteResult(){

    }

    public void setMapList( List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }

    public List<Map<String, String>> getMapList() {
        return mapList;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    public void addExecutionEventListener(ExecutionEventListener listener){

        if (getMapList() != null){
            listener.onExecutionSuccess(mapList);
        }else {
            listener.onExecutionFailed(exception);
        }
    }
}
