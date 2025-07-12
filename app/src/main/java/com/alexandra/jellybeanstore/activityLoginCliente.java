package com.alexandra.jellybeanstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.ClienteApiService;
import com.alexandra.jellybeanstore.api.ClienteRequest;
import com.alexandra.jellybeanstore.api.ClienteResponse;
import com.alexandra.jellybeanstore.viewmodels.CrearPedidoViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activityLoginCliente extends AppCompatActivity {
    private EditText nombre, identificacion;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombre = findViewById(R.id.etNombre);
        identificacion = findViewById(R.id.etIdentificacion);
        btnLogin = findViewById(R.id.btnLogin);

        setupViews();
    }
    private void setupViews() {
        btnLogin.setOnClickListener(v -> {
            String email = nombre.getText().toString().trim();
            String password = identificacion.getText().toString().trim();

            if (validarInputs(email, password)) {
                realizarLogin(email, password);
            }else {
                Toast.makeText(this,"error en el boton", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean validarInputs(String email, String password) {
        if (email.isEmpty()) {
            nombre.setError("Email es requerido");
            return false;
        }

        if (password.isEmpty()) {
            identificacion.setError("Contraseña es requerida");
            return false;
        }

        return true;
    }

    private void realizarLogin(String email, String password) {

        btnLogin.setEnabled(false);

        ClienteRequest loginRequest = new ClienteRequest(email, password);
        ClienteApiService service = ApiClient.getClient().create(ClienteApiService.class);

        Call<ClienteResponse> call = service.login(loginRequest);
        call.enqueue(new Callback<ClienteResponse>() {
            @Override
            public void onResponse(Call<ClienteResponse> call, Response<ClienteResponse> response) {
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    ClienteResponse loginResponse = response.body();
                    //guardarSesion(loginResponse);
                    irAMainActivity();
                } else {
                    mostrarError("Credenciales incorrectas o error del servidor");
                }
            }

            @Override
            public void onFailure(Call<ClienteResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                mostrarError("Error de conexión: " + t.getMessage());
            }
        });
    }
    private void irAMainActivity() {
        Intent intent = new Intent(this, CrearPedidoViewModel.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}