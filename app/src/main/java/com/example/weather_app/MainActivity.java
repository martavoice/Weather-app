package com.example.weather_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.refrbutton);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) // клик на кнопку
            {
                RefreshTemper();
            }
        });

        RefreshTemper();
    };

    public String GetTemper(String urlsite) // фукция загрузки температуры
    {
        String matchtemper = "";
        try
        {
            // загрузка страницы
            URL url = new URL(urlsite);
            URLConnection conn = url.openConnection();
            InputStreamReader rd = new InputStreamReader(conn.getInputStream());
            StringBuilder allpage = new StringBuilder();
            int n = 0;
            char[] buffer = new char[40000];
            while (n >= 0)
            {
                n = rd.read(buffer, 0, buffer.length);
                if (n > 0)
                {
                    allpage.append(buffer, 0, n);
                }
            }
            // работаем с регулярками
            Pattern pattern = Pattern.compile
                    ("<span style=\"color:#[a-zA-Z0-9]+\">[^-+0]+([-+0-9]+)[^<]+</span>[^(а-яА-ЯёЁa-zA-Z0-9)]+([а-яА-ЯёЁa-zA-Z ]+)");
            Matcher matcher = pattern.matcher(allpage.toString());
            if (matcher.find())
            {
                matchtemper = matcher.group(1);
            }
            return matchtemper;
        }
        catch (Exception e)
        {

        }
        return matchtemper;
    };

    public void RefreshTemper()
    {
        TextView tTemper = (TextView) findViewById(R.id.temper);
        String bashtemp = "";
        bashtemp = GetTemper("https://sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-%D0%BB%D1%8C%D0%B2%D0%BE%D0%B2");
        tTemper.setText(bashtemp.concat("°")); // отображение температуры
    }
    }

