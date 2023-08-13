package sy.iyad.server;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import sy.iyad.mikrotik.MikrotikServer;
import sy.iyad.mikrotik.Models.ConnectionEventListener;
import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.mikrotik.Utils.ApiImp;


public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(v -> connExe());
    }
    private void connExe() {
        MikrotikServer.connect("2.2.2.2", "admin", "995x")
              .addConnectionEventListener(new ConnectionEventListener() {
                  @Override
                  public void onConnectionSuccess(Api result) {
                      button.setText(result.toString());
                      startActivity(new Intent(MainActivity.this,SecActivity.class));
                  }

                  @Override
                  public void onConnectionFailed(Exception exception) {

                      button.setText(exception.getMessage());
                      MikrotikServer.connectWithDefaultPort("10.1.0.1","admin","amisa",1000)
                              .addConnectionEventListener(new ConnectionEventListener() {
                                  @Override
                                  public void onConnectionSuccess(Api api) {
                                      Toast.makeText(MainActivity.this,api.toString(),Toast.LENGTH_LONG).show();
                                      MikrotikServer.execute((ApiImp) api,"/ip/address/print");

                                  }

                                  @Override
                                  public void onConnectionFailed(Exception exception) {

                                  }
                              });
                  }
              });

    }
}