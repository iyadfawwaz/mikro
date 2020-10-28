package sy.iyad.server;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import sy.iyad.mikrotik.MikrotikServer;
import sy.iyad.mikrotik.Models.ConnectionEventListener;
import sy.iyad.mikrotik.Utils.Api;


public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connExe();
            }
        });
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
                  }
              });

    }
}