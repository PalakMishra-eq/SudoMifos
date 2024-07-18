package com.example.sudomifos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class LinkAccountActivity extends AppCompatActivity {

    private Spinner spinnerBanks;
    private LinearLayout bankDetailsLayout;
    private EditText editTextAccountHolderName, editTextAccountNumber, editTextIFSC;
    private Button buttonLinkAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_account);

        spinnerBanks = findViewById(R.id.spinnerBanks);
        bankDetailsLayout = findViewById(R.id.bankDetailsLayout);
        editTextAccountHolderName = findViewById(R.id.editTextAccountHolderName);
        editTextAccountNumber = findViewById(R.id.editTextAccountNumber);
        editTextIFSC = findViewById(R.id.editTextIFSC);
        buttonLinkAccount = findViewById(R.id.buttonLinkAccount);

        // Populate the spinner with bank names
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.banks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBanks.setAdapter(adapter);

        spinnerBanks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != 0) { // Assuming first item is a placeholder
                    bankDetailsLayout.setVisibility(View.VISIBLE);
                    buttonLinkAccount.setVisibility(View.VISIBLE);
                } else {
                    bankDetailsLayout.setVisibility(View.GONE);
                    buttonLinkAccount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                bankDetailsLayout.setVisibility(View.GONE);
                buttonLinkAccount.setVisibility(View.GONE);
            }
        });

        buttonLinkAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capture the input and proceed to the next activity
                String accountHolderName = editTextAccountHolderName.getText().toString();
                String accountNumber = editTextAccountNumber.getText().toString();
                String ifscCode = editTextIFSC.getText().toString();

                Intent intent = new Intent(LinkAccountActivity.this, NextActivity.class);
                intent.putExtra("accountHolderName", accountHolderName);
                intent.putExtra("accountNumber", accountNumber);
                intent.putExtra("ifscCode", ifscCode);
                startActivity(intent);
            }
        });
    }
}
