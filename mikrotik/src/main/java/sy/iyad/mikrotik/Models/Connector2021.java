package sy.iyad.mikrotik.Models;


import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.mikrotik.Utils.ApiImp;
import javax.net.SocketFactory;
import java.util.concurrent.Callable;


public class Connector2021 implements Callable<ApiImp> {

    private final String[] informations;
    private final int[] ints;

    public Connector2021(String[] informations,int[] ints){
        this.informations = informations;
        this.ints = ints;
    }
    @Override
    public ApiImp call() throws Exception {
        ApiImp apiImp;
        apiImp = (ApiImp) Api.connect(SocketFactory.getDefault(),informations[0],ints[0],ints[1]);
        apiImp.login(informations[1],informations[2]);
        return apiImp;
    }
}
