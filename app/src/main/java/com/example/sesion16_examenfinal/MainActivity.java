package com.example.sesion16_examenfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sesion16_examenfinal.adapter.FeriadoAdapter;
import com.example.sesion16_examenfinal.db.DatabaseHelper;
import com.example.sesion16_examenfinal.model.Feriado;
import com.example.sesion16_examenfinal.service.PublicHolidayApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAnio, editTextPais;
    private Button btnRegistro, btnMuestra;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private FeriadoAdapter feriadoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAnio = findViewById(R.id.editTextAnio);
        editTextPais = findViewById(R.id.editTextCodPais);
        btnRegistro = findViewById(R.id.buttonRegistrar);
        btnMuestra = findViewById(R.id.buttonMostrar);
        recyclerView = findViewById(R.id.recyclerView);

        databaseHelper = new DatabaseHelper(this);

        btnRegistro.setOnClickListener(v -> {
            String anio = editTextAnio.getText().toString();
            String pais = editTextPais.getText().toString();

            if (!anio.isEmpty() && !pais.isEmpty()) {
                int anioReal = Integer.parseInt(anio);
                obtenerFeriados(anioReal, pais);
            } else {
                Toast.makeText(MainActivity.this, "Ingrese año y país", Toast.LENGTH_SHORT).show();
            }

            editTextAnio.setText("");
            editTextPais.setText("");
        });

        btnMuestra.setOnClickListener(v -> {
            cargarFeriados();
        });
    }

    private void obtenerFeriados(int anioFeriado, String paisFeriado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://date.nager.at/api/v3/PublicHolidays/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PublicHolidayApiService apiService = retrofit.create(PublicHolidayApiService.class);
        Call<List<Feriado>> call = apiService.getFeriados(anioFeriado, paisFeriado);
        call.enqueue(new Callback<List<Feriado>>() {
            @Override
            public void onResponse(Call<List<Feriado>> call, Response<List<Feriado>> response) {
                try {
                    if (!response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<Feriado> listaFeriados = response.body();
                    if (listaFeriados != null) {
                        for (Feriado feriado : listaFeriados) {
                            databaseHelper.registrarFeriado(feriado);
                        }
                        Toast.makeText(MainActivity.this, "Feriados registrados con éxito", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Error al obtener feriados", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Feriado>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la llamada al API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarFeriados() {
        List<Feriado> feriados = databaseHelper.listarFeriados();
        feriadoAdapter = new FeriadoAdapter(MainActivity.this, this, feriados);
        recyclerView.setAdapter(feriadoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}