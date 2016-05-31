package dhtml.com.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.africoders.phpsocket.io.SocketEvent;
import com.africoders.phpsocket.io.WebSocketClient;


public class MainActivity extends Activity {

    public static  WebSocketClient socket;
    public static String ChatData="";

    public void doLog(String log) {
        Log.i("webchat",log);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView webview = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");


        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result){
                Log.d("webchat","gerrit:"+message);
                return true;
            }
        });

        webview.loadUrl("file:///android_asset/index.html");

        socket=new WebSocketClient();

        socket.on("chat message", new SocketEvent() {
            @Override
            public void run() {

            }

            public void run(String params,String sender) {
                if(ChatData!="") {ChatData+="\n";}
                ChatData+=params;
            }
        });

        socket.on("connect", new SocketEvent() {
            @Override
            public void run() {

            }

            public void run(String user,String p) {
                if(ChatData!="") {ChatData+="\n";}
                ChatData+="You are welcome "+user;
            }
        });

        socket.connect("ws://162.144.68.201:2000");


        //new LongOperation().execute("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
