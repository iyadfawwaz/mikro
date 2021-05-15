package sy.iyad.mikrotik.Models;


import android.os.AsyncTask;
import java.util.List;
import java.util.Map;
import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.mikrotik.Utils.MikrotikApiException;

@Deprecated
public class Executor extends AsyncTask<Api,Integer, List<Map<String,String>>> {

    public String cmd;
    private Exception externalExceptionFromExecutor;

    public Executor(String cmd){

        this.cmd = cmd;
    }

    @Override
    protected List<Map<String, String>> doInBackground(Api... apis) {
        List<Map<String, String>> mapList;
        try {

            mapList = apis[0].execute(cmd);
            return mapList;

        } catch (MikrotikApiException e) {
            externalExceptionFromExecutor = e;
        }

        return null;
    }

    public Exception getExternalExceptionFromExecutor() {
        return externalExceptionFromExecutor;
    }
}
