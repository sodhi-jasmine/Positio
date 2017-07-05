package com.jasminesodhi.positio;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Context;
import android.content.Intent;

import android.os.IBinder;

import android.support.v7.app.NotificationCompat;

import android.text.format.DateUtils;
import android.text.format.Time;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ApiCallService extends IntentService {

    String url;
    StringRequest request;

    static int uid1_prev = 0, uid2_prev = 0;

    public ApiCallService() {
        super("ApiCallService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        scheduleNextUpdate();
        CheckPosture();
    }

    private void scheduleNextUpdate() {

        Intent intent = new Intent(this, this.getClass());
        PendingIntent pendingIntent =
                PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long currentTimeMillis = System.currentTimeMillis();

        long nextUpdateTimeMillis = currentTimeMillis + 2 * DateUtils.MINUTE_IN_MILLIS;

        Time nextUpdateTime = new Time();
        nextUpdateTime.set(nextUpdateTimeMillis);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        url = "";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            onHandleIntent(intent);
        return Service.START_STICKY;
    }

    public void CheckPosture()  {

        try {

            request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {

                    parseJsonData(string);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Some error occurred!", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseJsonData(String jsonString) {

        try {

            JSONObject jsonObject = new JSONObject(jsonString);

            String uid_1 = jsonObject.getString("ultra1");
            int uid1 = Integer.decode(uid_1);

            String uid_2 = jsonObject.getString("ultra2");
            int uid2 = Integer.decode(uid_2);

            if((uid1 == uid1_prev) && (uid2 == uid2_prev)) {

                if((uid1 == 1) || (uid2 == 1)) {

                    addNotification();

                    Intent intent = new Intent(getApplicationContext(), NotificationView.class);
                    startActivity(intent);
                }
            }

            uid1_prev = uid1;
            uid2_prev = uid2;

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void addNotification() {
                NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notif)
                        .setContentTitle("Hey, you've received a notification.")
                        .setContentText("Reminder! Correct your posture.");

                Intent notificationIntent = new Intent(getApplicationContext(), LandingActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                builder.setAutoCancel(true);

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
            }
}

