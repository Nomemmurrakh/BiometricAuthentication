package com.nomemmurrakh.biometricauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;

import com.nomemmurrakh.biometricauthentication.databinding.ActivityMainBinding;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static final String STATUS_SUCCESS = "Authenticated Successfully.";
    public static final String STATUS_FAILED = "Authentication Failed.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                binding.txtStatus.setText(STATUS_SUCCESS);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                binding.txtStatus.setText(STATUS_FAILED);
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authenticate to use the app.")
                .setSubtitle("This is a subtitle.")
                .setNegativeButtonText("Use another method.")
                .build();

        binding.btnAuthenticate.setOnClickListener(v -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }
}