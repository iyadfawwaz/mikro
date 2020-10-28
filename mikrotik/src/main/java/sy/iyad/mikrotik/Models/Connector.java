package sy.iyad.mikrotik.Models;

import android.os.AsyncTask;
import javax.net.SocketFactory;
import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.mikrotik.Utils.MikrotikApiException;


public class Connector extends AsyncTask<String,Integer, Api> {

    private Exception externalExceptionFromConnector;

    public int CUSTOMER_PORT;
    public int CUSTOMER_TIMEOUT;

    @SuppressWarnings("deprecation")
    public Connector(int port, int timeout)  {

        this.CUSTOMER_PORT=port;
        this.CUSTOMER_TIMEOUT=timeout;

    }

    @Override
    protected Api doInBackground(String... strings) {

        Api api;
        try {

            api = Api.connect(SocketFactory.getDefault(),strings[0],CUSTOMER_PORT,CUSTOMER_TIMEOUT);
            api.login(strings[1],strings[2]);
            return api;

        } catch (MikrotikApiException e) {
            externalExceptionFromConnector = e;
        }

        return null;
    }

    public Exception getExternalExceptionFromConnector() {
        return externalExceptionFromConnector;
    }
}
