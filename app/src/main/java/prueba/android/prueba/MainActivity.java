package prueba.android.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void Creditos(View view)
    {
        Intent intent = new Intent(this,CreditosClase.class);
        startActivity(intent);
    }
}
