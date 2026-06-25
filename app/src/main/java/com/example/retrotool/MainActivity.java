package com.example.challengetouch.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.DataOutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面レイアウトをコードだけで構築（ネットワーク・リソース不要）
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);

        TextView title = new TextView(this);
        title.setText("チャレンジタッチ 初代Root工具箱 (Android 4.2.2)\n");
        title.setTextSize(22);
        layout.addView(title);

        // ボタン1: 安全な高速シャットダウン (Root必須)
        Button btnReboot = new Button(this);
        btnReboot.setText("Root権限で強制再起動");
        btnReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRootCommand("reboot");
            }
        });
        layout.addView(btnReboot);

        // ボタン2: バッテリーの統計情報を強制リセット (Root必須)
        Button btnBattery = new Button(this);
        btnBattery.setText("バッテリーキャリブレーション (統計リセット)");
        btnBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRootCommand("rm /data/system/batterystats.bin");
            }
        });
        layout.addView(btnBattery);

        setContentView(layout);
    }

    // Root権限(su)でシェルコマンドを実行するメソッド
    private void runRootCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
