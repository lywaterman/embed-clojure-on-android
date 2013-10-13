package com.example.icodereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.content.pm.*;
import clojure.lang.*;
import clojure.lang.Compiler;
import clojure.core$load_string;
import android.content.Context;




public class MainActivity extends Activity {

	public static InputStream getInputStreamFromUrl(String url) {
		  InputStream content = null;
		  try {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response = httpclient.execute(new HttpGet(url));
		    content = response.getEntity().getContent();
		  } catch (Exception e) {
		    System.out.println(e.toString());
		  }
		    return content;
		}
	
	public static String fromStream(InputStream in) throws IOException
	{
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder out = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        out.append(line);
	    }
	    return out.toString();
	}
		// see http://androidsnippets.com/executing-a-http-get-request-with-httpclient
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        Button button1 = (Button)this.findViewById(R.id.button1);

		Symbol CLOJURE_MAIN = Symbol.intern("clojure.main");
		Var REQUIRE = RT.var("clojure.core", "require");
		REQUIRE.invoke(CLOJURE_MAIN);
		
		Var dd = RT.var("clojure.core", "*default-data-reader-fn*");
		
		Var cp = RT.var("clojure.core", "*compile-path*");

		System.out.println(this.getCacheDir().toString());		
		
		clojure.lang.DalvikDynamicClassLoader.custom_dex_dir = this.getCacheDir().toString();
        Var load_string = RT.var("clojure.core", "println");
        
        load_string.invoke("111");
        
        final Var load_string1 = RT.var("clojure.core", "load-string");
        
        load_string1.invoke("(println 111)");
        
        new Thread(new Runnable() {
        	@Override
        	public void run() {
        		//Symbol CLOJURE_MAIN = Symbol.intern("neko.application");
                //Var REQUIRE = RT.var("clojure.core", "require");
                
                //REQUIRE.invoke(CLOJURE_MAIN);
                
                //Var INIT = RT.var("neko.application", "init-application");
                
               // INIT.invoke(MainActivity.this.getApplication());
        		
        		
        	}
        	
        }).start();
        
        
        button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("enter clojure world");
				
				InputStream stream = getInputStreamFromUrl("http://192.168.2.152:8080/clojure/tt.clj");
	
				try {
					String code = fromStream(stream);
					System.out.println(code);
					load_string1.invoke(code);
					
				}
				catch (Exception ex) {
					System.out.println(ex.toString());
				}
				
				
			}
		});
        
        
        
        System.out.println("sdfsdfsfdsdf");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
