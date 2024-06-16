package br.edu.fateczl.saudementalapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.fateczl.saudementalapp.control.authentication.CadastroAuthentication;

public class CadastroActivity extends AppCompatActivity implements UIDataMapping {

    private EditText inNewLogin, inNewNome, inNewSenha, inEmail_Espec;
    private RadioButton rdYes, rdNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnConfirm, btnCancel;

        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);
        inNewLogin = findViewById(R.id.inNewLogin);
        inNewNome = findViewById(R.id.inNewNome);
        inNewSenha = findViewById(R.id.inNewSenha);
        inEmail_Espec = findViewById(R.id.inEmail_Espec);

        rdYes = findViewById(R.id.rbYes);
        rdNo = findViewById(R.id.rbNo);
        rdYes.setOnClickListener(op -> inEmail_Espec.setHint(R.string.espec));
        rdNo.setOnClickListener(op -> inEmail_Espec.setHint(R.string.email));

        btnConfirm.setOnClickListener(op -> operacaoCadastro());
        btnCancel.setOnClickListener(op -> goToLogin());
    }

    @Override
    public Bundle getInputData() {
        Bundle inputData = new Bundle();
        inputData.putString("login", inNewLogin.getText().toString());
        inputData.putString("senha", inNewSenha.getText().toString());
        inputData.putString("nome", inNewNome.getText().toString());
        if (rdYes.isChecked()) {
            inputData.putString("espec", inEmail_Espec.getText().toString());
            inputData.putString("isCollab", "true");
        } if (rdNo.isChecked()) {
            inputData.putString("email", inEmail_Espec.getText().toString());
            inputData.putString("isCollab", "false");
        }
        return inputData;
    }

    private void operacaoCadastro() {
        try {
            CadastroAuthentication aut = new CadastroAuthentication(this);
            aut.sendUser(getInputData());
            Toast.makeText(this, getString(R.string.sucessCadastro), Toast.LENGTH_LONG).show();
            goToLogin();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("err", e.getMessage());
        }
    }

    private void goToLogin() {
        this.startActivity(new Intent(this, LoginActivity.class),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}