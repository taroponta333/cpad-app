package com.example.retrotool;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvInfo = findViewById(R.id.tv_info);
        
        String ipAddress = getIPAddress();
        String systemInfo = "【ネットワーク情報】\n" +
                "IPアドレス: " + ipAddress + "\n\n" +
                "【端末システム情報】\n" +
                "OSバージョン: Android " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")\n" +
                "モデル名: " + Build.MODEL + "\n" +
                "ハードウェア: " + Build.HARDWARE + "\n" +
                "製造元: " + Build.MANUFACTURER;

        tvInfo.setText(systemInfo);
    }

    // Android 4.2.2でも確実にIPアドレスを取得するロジック
    private String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':') < 0;
                        if (isIPv4) return sAddr;
                    }
                }
            }
        } catch (Exception ignored) { }
        return "取得失敗 (Wi-Fiオフライン)";
    }
}
