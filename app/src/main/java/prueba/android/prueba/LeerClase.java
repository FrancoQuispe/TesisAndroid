package prueba.android.prueba;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Franco on 10/01/2018.
 */

public class LeerClase extends Activity {

    TextView textView;
    NfcAdapter nfcAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leer);
        textView = (TextView) findViewById(R.id.lectura);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

    }
    @Override
    public void onResume()
    {
        super.onResume();

        enableForegroundDispatchSystem();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        disableForegroundDispatchSystem();
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent();
        if(intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            Toast.makeText(this, "NFC Intent", Toast.LENGTH_SHORT).show();
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            tag.toString();
        }
    }
}
