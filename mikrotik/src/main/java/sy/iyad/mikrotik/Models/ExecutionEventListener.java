package sy.iyad.mikrotik.Models;


import java.util.List;
import java.util.Map;

public interface ExecutionEventListener {

    void onExecutionSuccess(List<Map<String, String>> mapList);
    void onExecutionFailed(Exception exception);

}
