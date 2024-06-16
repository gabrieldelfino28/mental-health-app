package br.edu.fateczl.saudementalapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.fateczl.saudementalapp.control.authentication.LoginAuthentication;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.ui.HomeCollabActivity;
import br.edu.fateczl.saudementalapp.ui.HomePessoaActivity;

public class LoginActivity extends AppCompatActivity implements UIDataMapping {

    private TextView errLogin;
    private EditText inLogin;
    private EditText inSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inLogin = findViewById(R.id.inLogin);
        inSenha = findViewById(R.id.inSenha);
        errLogin = findViewById(R.id.TVerrLogin);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCad = findViewById(R.id.btnCad);

        btnLogin.setOnClickListener(op -> checkCredentials());
        btnCad.setOnClickListener(op -> goToCadastro());
    }

    @Override
    public Bundle getInputData() {
        Bundle inputData = new Bundle();
        inputData.putString("login", inLogin.getText().toString());
        inputData.putString("senha", inSenha.getText().toString());
        return inputData;
    }
    private void checkCredentials() {
        try {
            LoginAuthentication aut = new LoginAuthentication();
            Bundle bundle = getInputData();
            Usuario usuario = aut.getUser(bundle);
            if (aut.isRegistered(usuario, this)) {
                usuario = aut.getAllUserData(usuario, this);
                goToMainApplication(bundle, usuario);
            } else {
                throw new Exception(getString(R.string.errLogin));
            }
        }catch (Exception e) {
            Log.wtf("err", e.getMessage());
            errLogin.setText(e.getMessage());
        }
    }

    private void goToCadastro() {
        this.startActivity(new Intent(this, CadastroActivity.class),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void goToMainApplication(Bundle b, @NonNull Usuario usr) {
        Toast.makeText(this.getBaseContext(), getString(R.string.successLogin), Toast.LENGTH_LONG).show();
        b.putString("userLogged", "true");
        if (usr.isCollab()) {
            b.putString("frag", "HomeC");
            this.startActivity(new Intent(this, HomeCollabActivity.class).putExtras(b));
            this.finish();
        } else {
            b.putString("frag", "HomeP");
            this.startActivity(new Intent(this, HomePessoaActivity.class).putExtras(b));
            this.finish();
        }
    }
}