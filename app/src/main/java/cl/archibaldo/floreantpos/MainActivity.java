package cl.archibaldo.floreantpos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String ipDireccion;
    private String ipPuerto;
    private String chkSave;
    private String url;
    private EditText ipText, ipPort;
    private Button btn_conectar;
    private CheckBox chb_guardar;
    private TextView mensaje;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipText = (EditText) findViewById(R.id.ipAddress);
        ipPort = (EditText) findViewById(R.id.ipPort);

        btn_conectar = (Button) findViewById(R.id.btn_conectar);

        chb_guardar = (CheckBox) findViewById(R.id.chboxNoPreguntar);

        mensaje = (TextView) findViewById(R.id.mensajes);


        settings = getSharedPreferences("config", 0);
        ipDireccion = settings.getString("IP", "");
        ipPuerto = settings.getString("PORT","");
        chkSave = settings.getString("SAVE","");
        url = "http://" + ipDireccion + ":" + ipPuerto + "/?app=FloreantPos&anonym=true";

        if(chkSave.equals("NO") || chkSave.equals("")){
            ipText.setText(ipDireccion.trim());
            ipPort.setText(ipPuerto.trim());
        }else{
            iniciarWebView(url);
        }

        btn_conectar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (ipText.getText().toString().trim().equals("") || ipPort.getText().toString().trim().equals("")) {
                    mensaje.setText("Debe ingresar la ip y el puerto");
                } else {
                    if (chb_guardar.isChecked()) {
                        SharedPreferences guardaDatos = getSharedPreferences("config", Context.MODE_PRIVATE);
                        final SharedPreferences.Editor prefs = guardaDatos.edit();
                        prefs.putString("IP", ipText.getText().toString().trim());
                        prefs.putString("PORT", ipPort.getText().toString().trim());
                        prefs.putString("SAVE", "YES");
                        prefs.commit();
                    } else {
                        SharedPreferences guardaDatos = getSharedPreferences("config", Context.MODE_PRIVATE);
                        final SharedPreferences.Editor prefs = guardaDatos.edit();
                        prefs.putString("IP", ipText.getText().toString().trim());
                        prefs.putString("PORT", ipPort.getText().toString().trim());
                        prefs.putString("SAVE", "NO");
                        prefs.commit();
                    }
                    url = "http://" + ipText.getText().toString().trim() + ":" + ipPort.getText().toString().trim() + "/?app=FloreantPos&anonym=true";
                    mensaje.setText("");
                    iniciarWebView(url);
                }

            }
        });
   }

   public void iniciarWebView(String url){
       Intent intent = new Intent(this,ActivityWebView.class);
       intent.putExtra("URL",url);
       startActivity(intent);
   }
}




