
package sy.iyad.mikrotik.Ready;

import java.util.Map;


public interface ResultListener {
    /** receive data from router
     * @param result The data received */
    void receive(Map<String, String> result);

    /** called if the command associated with this listener experiences an error
     * @param ex Exception encountered */
    void error(MikrotikApiException ex);

    /** called when the command associated with this listener is done */
    void completed();
}
