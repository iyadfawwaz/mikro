package sy.iyad.server.share;


import static sy.iyad.server.LoginActivity.ACTION_LOGIN;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sy.iyad.server.R;


public class SharingActivity extends AppCompatActivity {

    TextView textView;
    Intent intent;
    VideoView videoView;
    ArrayAdapter<String> arrayAdapter;

    FloatingActionButton actionButton;
    BroadcastReceiver onComplete,onNotificationClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        textView = findViewById(R.id.shareView);
        videoView = findViewById(R.id.videoView);
        actionButton = findViewById(R.id.floatingActionButton2);
        intent = getIntent();

        String string = intent.getStringExtra(Intent.EXTRA_TEXT);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,pullLinks(string));
        arrayAdapter.notifyDataSetChanged();


            textView.setText(string);


        if (getIntent().getAction().equals(ACTION_LOGIN)){

            String str = intent.getStringExtra("message");

            if (str != null) {
                textView.setText(str);
            }
        }
        BroadcastReceiver onComplete=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,intent.getStringExtra("message"),Toast.LENGTH_LONG).show();
            }


        };
        onNotificationClick=new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Toast.makeText(ctxt, "Ummmm...hi!", Toast.LENGTH_LONG).show();
            }
        };
        actionButton.setOnClickListener(v -> download(pullLinks(string).get(0)));
    }


    public ArrayList<String> pullLinks(String text) {
        ArrayList<String> links = new ArrayList<>();

        //String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        String regex = "\\(?\\b(https?://|www[.]|ftp://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);

        while(m.find())
        {
            String urlStr = m.group();

            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }

            links.add(urlStr);
        }

        return links;
    }

    private void download(String path) {
        Downloader downloader = new Downloader();
        downloader.alertMeStart();
        downloader.doInBackground(path);
        downloader.onPostExecute(path);
        downloader.onProgressUpdate(100);
    }

    @SuppressLint("StaticFieldLeak")
    class Downloader {

        public void alertMeStart() {
            Toast.makeText(getApplicationContext(), "downloading...", Toast.LENGTH_LONG).show();
        }



        protected void doInBackground(String... strings) {
            final String vidurl = strings[0];
            downloadHelper(vidurl);
        }


        protected void onProgressUpdate(Integer... values) {
            textView.setText("loading..."+values[0]+" downloaded..%");
        }


        protected void onPostExecute(String s) {
           Toast.makeText(getApplicationContext(),"video downloaded from : "+s,Toast.LENGTH_LONG).show();
        }
    }

    public static List<String> extractUrls(String input) {
        List<String> result = new ArrayList<>();

        String[] words = input.split("\\s+");


        Pattern pattern = Patterns.WEB_URL;
        for(String word : words)
        {
            if(pattern.matcher(word).find())
            {
                if(!word.toLowerCase().contains("http://") && !word.toLowerCase().contains("https://"))
                {
                    word = "http://" + word;
                }
                result.add(word);
            }
        }

        return result;
    }

    @Override
    protected void onDestroy() {


        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
        super.onDestroy();
    }

    private void downloadHelper(String vidurl) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sd = new SimpleDateFormat("yymmhh");
        String date = sd.format(new Date());
        String name = "video" + date + ".mp4";

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        registerReceiver(onNotificationClick,
                new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(vidurl))
                .setTitle(name)
                        .setDescription("start download")
                                .setAllowedOverMetered(true)
                                        .setAllowedOverRoaming(true)
                                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                                                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                                                                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,name);

        downloadManager.enqueue(request);

        try {
            String rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    + File.separator + "My_Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();

            URL url = new URL(vidurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    name));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }


    }
}
