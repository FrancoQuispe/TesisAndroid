package prueba.android.prueba;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Franco on 9/01/2018.
 */

public class RegistrarClase extends Activity
{
    NfcAdapter adapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);
        context = this;
        Button btnWrite = (Button)findViewById(R.id.registrar);
        btnWrite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nombres = (EditText) findViewById(R.id.nombres);
                final EditText apellidos = (EditText) findViewById(R.id.apellidos);
                final EditText dni = (EditText) findViewById(R.id.dni);
                final EditText edad = (EditText) findViewById(R.id.edad);
                String mensaje = "Nombres: " + nombres.getText().toString() + "\n" + "Apellidos: " + apellidos.getText().toString() + "\n"+
                        "DNI: " + dni.getText().toString() + "\n" + "Edad: " + edad.getText().toString();
                final NdefMessage message = createNdefMessage(mensaje);
                try{
                    if(myTag == null){
                        Toast.makeText(context, "no existe tag", Toast.LENGTH_SHORT).show();
                    }else{

                        write(message,myTag);
                        nombres.setText(null);
                        apellidos.setText(null);
                        dni.setText(null);
                        edad.setText(null);
                        Toast.makeText(context, "Escritura completada", Toast.LENGTH_SHORT).show();
                    }
                }catch(IOException e){
                    Toast.makeText(context, "Acerque el tag",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }catch(FormatException e){
                    Toast.makeText(context, "acerque el tag", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        adapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};
    }
    private void write(NdefMessage message, Tag tag) throws IOException, FormatException{
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }
    private NdefRecord createRecord(String text) throws UnsupportedEncodingException{
        String lang = "us";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payLoad = new byte[1 + langLength + textLength];

        payLoad[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payLoad, 1, langLength);
        System.arraycopy(textBytes, 0, payLoad, 1+langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payLoad);

        return recordNFC;

    }

    @Override
    protected void onNewIntent(Intent intent){
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(this,"tag detectado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume(){
        super.onResume();
        WriteModeOn();
    }
    private NdefMessage createNdefMessage(String content)
    {
        NdefRecord ndefRecord = NdefRecord.createTextRecord("es",content);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});
        return ndefMessage;
    }
    private void WriteModeOn(){
        writeMode = true;
        adapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    private void WriteModeOff(){
        writeMode = false;
        adapter.disableForegroundDispatch(this);
    }
}
