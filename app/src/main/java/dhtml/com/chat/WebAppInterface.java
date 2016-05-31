package dhtml.com.chat;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.africoders.phpsocket.io.WebSocketClient;

/**
 * Created by Dr. Anthony Ogundipe on 9/1/2015.
 */
public class WebAppInterface {
    Context mContext;

    public WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public String getValue() {
        String s=MainActivity.ChatData;
        MainActivity.ChatData="";
        return s;
    }

    @JavascriptInterface
    public void setValue(String value) {
        Log.i("webchat",value);
        WebSocketClient.Instance.emit("chat message",value);
    }


}