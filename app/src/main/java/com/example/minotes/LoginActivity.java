package com.example.minotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Verificar si se puede autenticar
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG
                | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Toast.makeText(this, "enrta aqui", Toast.LENGTH_SHORT).show();
                mostrarBiometricPrompt();
                break;
            default:
                Toast.makeText(this, "No se puede autenticar", Toast.LENGTH_SHORT).show();
//                finish(); // Cierra la app si no puede autenticarse
                break;
        }
    }
    private void mostrarBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                         Usuario autenticado, continuar con la app
                        finishAffinity(); // Cierra todas las actividades
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(LoginActivity.this, "llega al fail", Toast.LENGTH_SHORT).show();
//                        finish(); // Cierra la app si la autenticación falla
                    }
                });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación requerida")
                .setSubtitle("Usa tu huella, PIN o reconocimiento facial")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG
                        | BiometricManager.Authenticators.DEVICE_CREDENTIAL) // Permite huella, PIN o cara
                .build();

        biometricPrompt.authenticate(promptInfo);
    }


}