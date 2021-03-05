package com.example.ejercicioecosemana4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ping extends AppCompatActivity {
    private Button regresar;
    private ListView listPing;
    private ArrayList<String> pings = new ArrayList<String>();
    ;
    private String ip, red, ipH;
    private boolean isIp, conectado;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);

        listPing = findViewById(R.id.listPing);
        regresar = findViewById(R.id.regresarBtn);
        isIp = getIntent().getBooleanExtra("isIp", false);
        ip = getIntent().getExtras().getString("ip");
        red = getIntent().getExtras().getString("red");


        if (isIp) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pings);
            ipPing();
        } else {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pings);
            allHosts();
        }
        regresar.setOnClickListener(
                (v) -> finish()
        );
    }

    public void allHosts() {
        new Thread(
                () -> {

                    for (int i = 1; i < 255; i++) {
                        ipH = red + i;

                        try {
                            InetAddress inet = InetAddress.getByName(ipH);
                            conectado = inet.isReachable(500);

                            runOnUiThread(
                                    () -> {
                                        if (conectado) {
                                            pings.add("Conectado: " + ipH);
                                            Log.e("<<<", "" + pings.get(0));
                                            adapter.notifyDataSetChanged();
                                            listPing.setAdapter(adapter);
                                        }
                                    }
                            );
                        } catch (java.net.UnknownHostException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                }
        ).start();
    }

    private void ipPing() {
        new Thread(
                () -> {

                    try {
                        InetAddress inet = InetAddress.getByName(ip);

                        for (int i = 0; i < 5; i++) {
                            conectado = inet.isReachable(1500);
                            runOnUiThread(
                                    () -> {
                                        Log.e("<<<","verga");
                                        pings.add("Conectado: " + conectado);
                                        adapter.notifyDataSetChanged();
                                        listPing.setAdapter(adapter);
                                    }
                            );
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }
}