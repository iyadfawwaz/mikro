package sy.iyad.mikrotik.Models;

import sy.iyad.mikrotik.Utils.Api;

public interface ConnectionEventListener {

    void onConnectionSuccess(Api api);
    void onConnectionFailed(Exception exception);

}
