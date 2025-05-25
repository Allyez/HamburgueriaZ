package com.example.hamburgueriaz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int quantidade = 0;
    private TextView quantidadeText;
    private EditText nomeClienteEdit;
    private CheckBox baconCheck, cheddarCheck, maioneseCheck, cebolaCheck, tomateCheck, ketchupCheck, ovoCheck;
    private TextView precofinalText;
    private TextView resumoPedidoText;

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

        quantidadeText = findViewById(R.id.quantpedido);
        Button botaoAdicionar = findViewById(R.id.adicionar);
        Button botaoSubtrair = findViewById(R.id.subtrair);
        Button botaoFazerpedido = findViewById(R.id.fazerPedido);
        nomeClienteEdit = findViewById(R.id.nomecliente);
        precofinalText = findViewById(R.id.precofinal);
        resumoPedidoText = findViewById(R.id.resumo);
        baconCheck = findViewById(R.id.bacon);
        cheddarCheck = findViewById(R.id.cheddar);
        maioneseCheck = findViewById(R.id.maionese);
        cebolaCheck = findViewById(R.id.cebola);
        tomateCheck = findViewById(R.id.tomate);
        ketchupCheck = findViewById(R.id.ketchup);
        ovoCheck = findViewById(R.id.ovo);

        configEventCheckBox();
        atualizarQuantidadeText();
        atualizarPrecoFinal();

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementarQuant();
            }
        });

        botaoSubtrair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementarQuant();
            }
        });

        botaoFazerpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPedido();
            }
        });
    }


    private void incrementarQuant() {
        quantidade++;
        atualizarQuantidadeText();
        atualizarPrecoFinal();
    }


    private void decrementarQuant() {
        if (quantidade > 0) {
            quantidade--;
            atualizarQuantidadeText();
            atualizarPrecoFinal();
        }
    }


    private void atualizarQuantidadeText() {
        quantidadeText.setText(String.valueOf(quantidade));
    }


    private void configEventCheckBox() {
        baconCheck.setOnCheckedChangeListener((buttonView, isChecked) -> atualizarPrecoFinal());
        cheddarCheck.setOnCheckedChangeListener((buttonView, isChecked) -> atualizarPrecoFinal());
        maioneseCheck.setOnCheckedChangeListener((buttonView, isChecked) -> atualizarPrecoFinal());
        ketchupCheck.setOnCheckedChangeListener((buttonView, isChecked) -> atualizarPrecoFinal());
        tomateCheck.setOnCheckedChangeListener((buttonView, isChecked) -> atualizarPrecoFinal());
        ovoCheck.setOnCheckedChangeListener((buttonView, isChecked) -> atualizarPrecoFinal());
        cebolaCheck.setOnCheckedChangeListener((buttonView, isChecked) -> atualizarPrecoFinal());
    }


    private void atualizarPrecoFinal() {
        boolean temBacon = baconCheck.isChecked();
        boolean temCheddar = cheddarCheck.isChecked();
        boolean temMaionese = maioneseCheck.isChecked();
        boolean temKetchup = ketchupCheck.isChecked();
        boolean temTomate = tomateCheck.isChecked();
        boolean temOvo = ovoCheck.isChecked();
        boolean temCebola = cebolaCheck.isChecked();

        double precoFinal = calcularPreco(temBacon, temCheddar, temMaionese, temKetchup, temTomate, temOvo, temCebola);
        precofinalText.setText("R$ " + String.format("%.2f", precoFinal));
    }


    private double calcularPreco(boolean temBacon, boolean temCheddar, boolean temMaionese,
            boolean temKetchup, boolean temtomate, boolean temOvo, boolean temCebola)
    {
       double precoBase = 15.0;
       double valorBacon = temBacon ? 3.0 : 0.0;
       double valorCheddar = temCheddar ? 3.0 : 0.0;
       double valorMaionese = temMaionese ? 1.0 : 0.0;
       double valorKetchup = temKetchup ? 1.0 : 0.0;
       double valorTomate = temtomate ? 1.5 : 0.0;
       double valorOvo = temOvo ? 2.0 : 0.0;
       double valorCebola = temCebola ? 2.0 : 0.0;

       return (precoBase + valorCebola + valorBacon + valorCheddar + valorKetchup + valorOvo +
       valorTomate + valorMaionese) * quantidade;
    }


    private void enviarPedido() {
        String nomeCliente = nomeClienteEdit.getText().toString();
        boolean temBacon = baconCheck.isChecked();
        boolean temCheddar = cheddarCheck.isChecked();
        boolean temMaionese = maioneseCheck.isChecked();
        boolean temKetchup = ketchupCheck.isChecked();
        boolean temTomate = tomateCheck.isChecked();
        boolean temOvo = ovoCheck.isChecked();
        boolean temCebola = cebolaCheck.isChecked();

        double precoFinal = calcularPreco(temBacon,temCheddar,temMaionese,temKetchup,temTomate,temOvo,temCebola);

        String resumo = "Nome do cliente: " + nomeCliente + "\n" +
                "tem Bacon? " + (temBacon ? "sim" : "Nao") + "\n" +
                "tem Cheddar? " + (temCheddar ? "sim" : "Nao") + "\n" +
                "tem Ovo? " + (temOvo ? "sim" : "Nao") + "\n" +
                "tem Anéis de cebola? " + (temCebola ? "sim" : "Nao") + "\n" +
                "tem Tomate? " + (temTomate ? "sim" : "Nao") + "\n" +
                "tem Maionese? " + (temMaionese ? "sim" : "Nao") + "\n" +
                "tem Ketchup? " + (temKetchup ? "sim" : "Nao") + "\n" +
                "Quantidade : " + quantidade + "\n" +
                "Preço final : R$ " + String.format("%.2f", precoFinal);

        resumoPedidoText.setText(resumo);
        precofinalText.setText("Preço final: R$ " + String.format("%.2f", precoFinal));


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nomeCliente);
        intent.putExtra(Intent.EXTRA_TEXT, resumo);

        startActivity(intent);
    }
}