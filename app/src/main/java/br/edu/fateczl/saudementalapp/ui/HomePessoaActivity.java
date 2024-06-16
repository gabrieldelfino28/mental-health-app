package br.edu.fateczl.saudementalapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import br.edu.fateczl.saudementalapp.LoginActivity;
import br.edu.fateczl.saudementalapp.R;
import br.edu.fateczl.saudementalapp.ui.user.ArtigoViewFragment;
import br.edu.fateczl.saudementalapp.ui.user.DiarioFragment;
import br.edu.fateczl.saudementalapp.ui.user.HomePessoaFragment;
import br.edu.fateczl.saudementalapp.ui.user.PerfilPessoaFragment;

public class HomePessoaActivity extends AppCompatActivity {
    Fragment fragmentP;
    private static String userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_pessoa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (Objects.equals(b.getString("userLogged"), "true")) {
                userLogin = b.getString("login");
                b.remove("userLogged");
            }
            loadFragment(b);
        }
    }

    private void loadFragment(Bundle b) {
        String typeFrag = b.getString("frag");
        assert typeFrag != null;
        switch (typeFrag) {
            case "HomeP":
                fragmentP = new HomePessoaFragment(userLogin);
                break;
            case "PerfilP":
                fragmentP = new PerfilPessoaFragment(userLogin);
                break;
            case "Diario":
                fragmentP = new DiarioFragment(userLogin);
                break;
            case "ArtigosView":
                fragmentP = new ArtigoViewFragment(userLogin);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragmentP).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        Bundle bundle = new Bundle();
        Intent i = new Intent(this, HomePessoaActivity.class);
        if (itemID == R.id.item_home_pessoa) {
            bundle.putString("frag", "HomeP");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_perfil_pessoa) {
            bundle.putString("frag", "PerfilP");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_diario) {
            bundle.putString("frag", "Diario");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_artigos_pessoa) {
            bundle.putString("frag", "ArtigosView");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }

        if (itemID == R.id.item_logoff) {
            Toast.makeText(this, getString(R.string.loggin_out), Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, LoginActivity.class);
            this.startActivity(in);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}