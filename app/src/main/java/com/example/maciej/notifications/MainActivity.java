package com.example.maciej.notifications;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String[] linksArray = new String[]{"https://www.milosierdzieboze.pl/rozaniec.php", "https://www.milosierdzieboze.pl/rozaniec.php", "https://www.milosierdzieboze.pl/rozaniec.php", "https://www.milosierdzieboze.pl/rozaniec.php"};

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Calendar calendar = Calendar.getInstance();
    Boolean onoff = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void buttonClicked(View view) {
        ImageButton imageButton = findViewById(R.id.button);

        if (onoff == false) {

            imageButton.setImageResource(R.drawable.milerename);
            onoff = true;
        } else {
            imageButton.setImageResource(R.drawable.rsz_1sad);
            onoff = false;
        }


        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);


        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        intent.putExtra("url", linkForADay());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        sendBroadcast(intent);

    }


    public void click(final View view) {
        preferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        if (preferences.contains("link")) {
            String word = preferences.getString("link", null);
            Log.e("word", word);
            linksArray = word.split(";");
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_title_link, null);
        mBuilder.setView(mView);

        final EditText link = (EditText) mView.findViewById(R.id.editTextLink);


        if (view.getId() == R.id.radosnaBtn) {
            mBuilder.setTitle("Tajemnica radosna");
            link.setText(linksArray[0]);

        } else if (view.getId() == R.id.bolesnaBtn) {
            mBuilder.setTitle("Tajemnica bolesna");
            link.setText(linksArray[1]);

        }
        if (view.getId() == R.id.chwalebnaBtn) {
            mBuilder.setTitle("Tajemnica chwalebna");
            link.setText(linksArray[2]);

        }
        if (view.getId() == R.id.swiatlaBtn) {
            mBuilder.setTitle("Tajemnica swiatla");
            link.setText(linksArray[3]);

        }
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {

            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(final DialogInterface dialogInterface, final int a) {
                if (view.getId() == R.id.radosnaBtn) {
                    linksArray[0] = link.getText().toString();
                } else if (view.getId() == R.id.bolesnaBtn) {
                    linksArray[1] = link.getText().toString();
                } else if (view.getId() == R.id.chwalebnaBtn) {
                    linksArray[2] = link.getText().toString();
                } else if (view.getId() == R.id.swiatlaBtn) {
                    linksArray[3] = link.getText().toString();
                }
                StringBuilder linksStringBuilder = new StringBuilder();
                for (int i = 0; i < linksArray.length; i++) {
                    linksStringBuilder.append(linksArray[i] + ";");
                }
                editor = preferences.edit();
                editor.putString("link", linksStringBuilder.toString());
                editor.apply();

            }
        });



        Log.e("swoatla", linksArray[3]);
        AlertDialog dialog = mBuilder.create();
        dialog.show();


    }

    public String linkForADay() {
        preferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        if (preferences.contains("link")) {
            String word = preferences.getString("link", null);

            linksArray = word.split(";");
        }
        String url;
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.MONDAY || day == Calendar.SATURDAY) {
            url = linksArray[0].toString();//radosna
        } else if (day == Calendar.THURSDAY) {
            url = linksArray[3].toString();//swiatla
        } else if (day == Calendar.TUESDAY || day == Calendar.FRIDAY) {
            url = linksArray[1].toString();//bolesna
        } else {
            url = linksArray[2].toString();//chwalebna
        }
        return url;
    }
}
