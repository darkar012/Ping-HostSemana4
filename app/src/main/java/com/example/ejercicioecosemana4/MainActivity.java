package com.example.ejercicioecosemana4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private Button ping, hosts;
    private EditText ident1, ident2, hostind, host;
    private TextView miIP;
    private String id1, id2, hi, h, ip, red;
    private boolean isIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ping = findViewById(R.id.pingBtn);
        hosts = findViewById(R.id.hostsBtn);
        ident1 = findViewById(R.id.identRed1);
        ident2 = findViewById(R.id.identRed2);
        hostind = findViewById(R.id.hostIdent);
        host = findViewById(R.id.host);
        miIP = findViewById(R.id.miIP);
        isIP = true;

        setMiIP();

        ping.setOnClickListener(
                (v) -> {
                    ping();
                    if (isIP) {
                        Intent i = new Intent(this, ping.class);
                        i.putExtra("ip", ip);
                        i.putExtra("isIp",isIP);
                        startActivity(i);
                    }

                }
        );

        hosts.setOnClickListener(
                (v)->{
                    isIP=false;
                    Intent i = new Intent(this, ping.class);
                    i.putExtra("red", red);
                    i.putExtra("isIp",isIP);
                    startActivity(i);
                }
        );

    }

    public void setMiIP() {
        new Thread(
                () -> {
                    Socket s = null;
                    try {
                        s = new Socket("google.com", 80);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String ip = s.getLocalAddress().getHostAddress();
                    runOnUiThread(
                            () -> {
                                miIP.setText(ip);
                            }
                    );
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String[] ipH = ip.split("\\.");
                    red = ipH[0]+"."+ipH[1]+"."+ipH[2]+".";
                }
        ).start();
    }

    public void ping() {

        isIP = true;

        id1 = ident1.getText().toString();
        id2 = ident2.getText().toString();
        hi = hostind.getText().toString();
        h = host.getText().toString();

        String[] ips = new String[]{
                id1,
                id2,
                hi,
                h
        };

        if (isIP) {
            for (int i = 0; i < ips.length; i++) {
                if (ips[i] == null || ips[i].isEmpty()) {
                    runOnUiThread(
                            () -> {
                                Toast.makeText(this, "Hay un valor vacio", Toast.LENGTH_SHORT).show();
                            }
                    );
                    isIP = false;
                } else {
                    int number = Integer.parseInt(ips[i]);
                    if (number < 0 || number > 255) {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "Los valores para las ID van de 0 a 255", Toast.LENGTH_SHORT).show();
                                }
                        );
                        isIP = false;
                    }
                }
            }
            ip = id1 + "." + id2 + "." + hi + "." + h;
        }
    }
}