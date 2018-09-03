package prueba.android.prueba;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(!nfcAdapter.isEnabled())
        {
            Toast.makeText(this,"NFC desactivado",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
    public void Salir(View view)
    {
        finish();
    }
    public void Registrar(View view)
    {
        Intent intent = new Intent(this,RegistrarClase.class);
        startActivity(intent);
    }
    public void Leer(View view)
    {
        Intent intent = new Intent(this,LeerClase.class);
        startActivity(intent);
    }
    public void Creditos(View view)
    {
        Intent intent = new Intent(this,CreditosClase.class);
        startActivity(intent);
    }
}
