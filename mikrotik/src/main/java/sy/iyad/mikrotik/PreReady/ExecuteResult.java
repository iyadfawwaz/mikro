package sy.iyad.mikrotik.PreReady;

import java.util.List;
import java.util.Map;

public class ExecuteResult {
    private List<Map<String,String>> mapList;
    public ExecuteResult(){

    }

    public void setMapList( List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }

    public List<Map<String, String>> getMapList() {
        return mapList;
    }
}
