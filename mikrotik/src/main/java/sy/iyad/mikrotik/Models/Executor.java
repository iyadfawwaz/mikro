package sy.iyad.mikrotik.Models;


import sy.iyad.mikrotik.Utils.ApiImp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


public class Executor implements Callable<List<Map<String,String>>> {

    private final ApiImp apiImp;
    private final String cmd;

    public Executor(ApiImp apiImp, String cmd) {
        this.apiImp = apiImp;
        this.cmd = cmd;
    }

    @Override
    public List<Map<String, String>> call() throws Exception {

        List<Map<String,String>> mapList;
        mapList = apiImp.execute(cmd);
        return mapList;
    }
}
