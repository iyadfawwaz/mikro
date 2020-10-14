package sy.iyad.mikrotik.PreReady;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import java.util.List;
import java.util.Map;
import sy.iyad.mikrotik.Ready.Api;
import sy.iyad.mikrotik.Ready.MikrotikApiException;


public class ServerExecutor extends AsyncTask<Api,Integer, List<Map<String,String>>> {
    public String cmd;
    public static Exception externalExceptionFromExecutor;
    @SuppressWarnings("deprecation")
    public ServerExecutor(@NonNull String cmd){
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
}
