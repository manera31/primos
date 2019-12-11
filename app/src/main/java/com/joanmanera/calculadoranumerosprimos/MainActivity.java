package com.joanmanera.calculadoranumerosprimos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView encontrados1, encontrados2;
    private TextView ultimo1, ultimo2;
    private TextView cores;
    private Button boton;
    private TareaAsincrona tareaAsincrona1, tareaAsincrona2;
    private boolean pulsado = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        encontrados1 = findViewById(R.id.tvEncontrados1);
        ultimo1 = findViewById(R.id.tvUltimo1);
        encontrados2 = findViewById(R.id.tvEncontrados2);
        ultimo2 = findViewById(R.id.tvUltimo2);

        boton = findViewById(R.id.boton);
        cores = findViewById(R.id.tvCores);

        cores.setText(String.valueOf(Runtime.getRuntime().availableProcessors()));
        tareaAsincrona1 = new TareaAsincrona(2, encontrados1, ultimo1);
        tareaAsincrona2 = new TareaAsincrona(100000000, encontrados2, ultimo2);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pulsado){
                    tareaAsincrona1.execute();
                    tareaAsincrona2.execute();
                    boton.setText("Cancelar");
                    pulsado = true;
                } else {
                    tareaAsincrona1.cancel(true);
                    tareaAsincrona2.cancel(true);
                    boton.setText("Iniciar");
                    boton.setEnabled(false);
                    pulsado = false;
                }

            }
        });
    }

    public static class TareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

        private int contador;
        private int numero;
        private TextView encontrados;
        private TextView ultimo;

        public TareaAsincrona(int posicionInicial, TextView encontrados, TextView ultimo){
            this.contador = 0;
            this.numero = posicionInicial;
            this.encontrados = encontrados;
            this.ultimo = ultimo;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            while (!isCancelled()){
                if(esPrimo(numero)){
                    contador++;
                    publishProgress(contador, numero);
                }
                numero++;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            encontrados.setText(String.valueOf(values[0]));
            ultimo.setText(String.valueOf(values[1]));
        }

        public boolean esPrimo(int numero){
            int contador = 2;
            boolean primo=true;
            while ((primo) && (contador!=numero)){
                if (numero % contador == 0)
                    primo = false;
                contador++;
            }
            return primo;
        }
    }

}


