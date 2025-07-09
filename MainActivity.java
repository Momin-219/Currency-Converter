package com.example.currencyconvertor;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText amountInput;
    Spinner fromCurrency, toCurrency;
    Button convertButton;
    TextView resultText;
    Button historyButton;
    public static ArrayList<String> historyList = new ArrayList<>();

    HashMap<String, Double> rates = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountInput = findViewById(R.id.amountInput);
        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);
        historyButton = findViewById(R.id.historyButton);

        // Add currency rates
        rates.put("USD", 1.0);
        rates.put("EUR", 0.91);
        rates.put("PKR", 277.0);
        rates.put("INR", 83.2);
        rates.put("GBP", 0.76);

        String[] currencies = {"USD", "EUR", "PKR", "INR", "GBP"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountStr = amountInput.getText().toString();
                if (amountStr.isEmpty()) {
                    resultText.setText("Please enter an amount.");
                    return;
                }

                double amount = Double.parseDouble(amountStr);
                String from = fromCurrency.getSelectedItem().toString();
                String to = toCurrency.getSelectedItem().toString();

                double usdAmount = amount / rates.get(from);
                double converted = usdAmount * rates.get(to);

                resultText.setText(String.format("%.2f %s = %.2f %s", amount, from, converted, to));

                String historyEntry = String.format("%.2f %s = %.2f %s", amount, from, converted, to);
                historyList.add(historyEntry);
            }
        });

        // History button click
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}