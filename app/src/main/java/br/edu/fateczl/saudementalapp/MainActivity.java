package br.edu.fateczl.saudementalapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    /* *
     *  TODO: Fragments da Pessoa e Collab
     *  TODO: Fragment de Contato para Collab
     *  TODO: Banco de Dados (GenericDAO) Modelagem + Implementação
     *  TODO: Conexão com Banco
     *  TODO: CRUD's do ICRUD
     *  TODO: Regra de Negócio principal: Criar Entradas no Jornal Pessoal
     * */
}