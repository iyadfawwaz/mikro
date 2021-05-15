package sy.iyad.mikrotik.Models;


import sy.iyad.mikrotik.Utils.ApiImp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


public class Executor2021 implements Callable<List<Map<String,String>>> {

    private ApiImp apiImp;
    private String cmd;

    public Executor2021(ApiImp apiImp, String cmd) {
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
