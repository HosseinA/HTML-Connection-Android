package com.example.html_connection_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.net.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final TextView tv01 = (TextView) findViewById(R.id.textView001);
        final TextView tv02 = (TextView) findViewById(R.id.textView002);
        tv02.setMovementMethod(new ScrollingMovementMethod());
        
        final EditText et01 = (EditText) findViewById(R.id.editText001);
        
        final Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				tv02.append("thread");
				
				URL url = null;
				try {
					url = new URL(et01.getText().toString());
					Log.d("get url", "got the url");
					URLConnection conn = url.openConnection();
					Log.d("conn", "open conn");
					BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					Log.d("buff", "buffer");
					String line = "";
					while((line = read.readLine()) != null) {
						tv02.append(line);
						Log.d("readLine", line.toString());
					}
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("excep", "excep");
				}
			}
		});
        
        //th.destroy();
        
        Button bt01 = (Button) findViewById(R.id.button001);
        
        bt01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try {
					
					ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					
					tv02.append("\n wifi:"+ni.isAvailable());
					tv02.append("\n"+ni.isConnected());
					
					ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
					
					tv02.append("\n mob:"+ni.isAvailable());
					tv02.append("\n"+ni.isConnected());
					
					th.start();
					Log.d("end of thread", "thread");
					
					/*HttpURLConnection http = (HttpURLConnection) url.openConnection();
					//tv02.append("\n"+http.getContentType());
					tv02.append("\n"+http.getContentType());
					tv02.append("\n"+http.getDate());
					tv02.append("\n"+http.getExpiration());
					tv02.append("\n"+http.HTTP_ACCEPTED);
					tv02.append("\n"+http.getResponseCode());
					//tv02.append("\n"+http.getContent());
					tv02.append("\n");*/
					
				} catch (Exception e) {
					tv02.append(" ERROR");
				}
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
