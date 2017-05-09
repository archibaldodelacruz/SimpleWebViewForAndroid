package cl.archibaldo.floreantpos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.app.AlertDialog;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.content.DialogInterface;
import android.content.Context;

public class MainActivity extends Activity {

    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        this.setContentView(R.layout.activity_main);
        myWebView = (WebView) this.findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setAllowContentAccess(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setDatabaseEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(false);

        final SharedPreferences settings = getSharedPreferences("config", 0);
        final String ipPuerto = settings.getString("IP", "");

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Ingrese la IP");
        alert.setMessage("Ingrese la IP de Floreant Pos en formato: ip:puerto");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        if(!ipPuerto.equals("")){
            input.setText(ipPuerto);
        }

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String url = "http://" + input.getText().toString();
                myWebView.loadUrl(url);
            }
        });

        alert.show();

        if(ipPuerto.equals("")){
            SharedPreferences guardaIP = getSharedPreferences("config", Context.MODE_PRIVATE);
            final SharedPreferences.Editor prefs = guardaIP.edit();
            prefs.putString("IP", input.getText().toString());
            prefs.commit();
        }




    }


}
