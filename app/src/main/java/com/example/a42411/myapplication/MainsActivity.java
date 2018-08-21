package com.example.a42411.myapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainsActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Button start;
    private static final String TAG = "Service";
    private static final int ERROR_DIALOG_REQUEST = 8888;
    private DBUtils dbUtils;
    private DBUtils dbUtils4Quiz;
    int count=0;
    int countQuiz=0;
    int random = 0;
    int randomQuiz=0;
    String quizContent;
    String quizAnswer;
    String missionLocation;
    String missionLocationID;
    int flag;
    double x_bd,y_bd;
    private Intent preIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);

        preIntent = new Intent();
        flag = preIntent.getIntExtra("flag",-2);
        dbUtils = new DBUtils(this,"db_building",1,"tb_bd",null,null,null,null,null,null);
        count = dbUtils.getCount();


        do{
            random = dbUtils.randomNumber(count);
        }while (random == flag);
        flag = random;
        missionLocation = dbUtils.getBuildingName().get(random);
        missionLocationID = dbUtils.getBuildingId().get(random);
        x_bd = Double.parseDouble(dbUtils.getBuildingLatitude().get(random));
        y_bd = Double.parseDouble(dbUtils.getBuildingLongitude().get(random));

        Log.e("Latitude:-0-0-0- ","o : "+x_bd+"  ***  ");
        Log.e("Longitude:-0-0-0- ","k : "+y_bd);
        dbUtils4Quiz = new DBUtils(this,"db_building",1,"tb_que",new String[]{"id_bd","content_que","ans_que"},"id_bd=?",new String[]{missionLocationID},null,null,null);

        countQuiz = dbUtils4Quiz.getCount();
        randomQuiz = dbUtils4Quiz.randomNumber(countQuiz);

        Log.e("问题数量: ",""+countQuiz);
        quizContent = dbUtils4Quiz.getQuizContent().get(randomQuiz);
        quizAnswer = dbUtils4Quiz.getQuizAnswer().get(randomQuiz);
//        for (int i=0;i<countQuiz;i++){
//            Log.e("我是问题内容: ",dbUtils4Quiz.getQuizContent().get(i));
//            Log.e("我是问题答案: ",dbUtils4Quiz.getQuizAnswer().get(i));
//        }

        //Create a new info:
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("content_que","how many types of pizzas?");
//        contentValues.put("id_db",1);
//        sqLiteDatabase.insert("tb_que",null,contentValues);
//        contentValues.clear();

        requestPermission();
        if (isServiceOK()) {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            start = (Button) findViewById(R.id.startbutton);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (ActivityCompat.checkSelfPermission(MainsActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        return;
                    }
                    mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(MainsActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null){
                                TextView textView = findViewById(R.id.location);
                                textView.setText(location.toString());
                            }
                            Intent i = new Intent(MainsActivity.this , MapsActivity.class);
                            i.putExtra("Destination",missionLocation);
                            i.putExtra("quizContent",quizContent);
                            i.putExtra("quizAnswer",quizAnswer);
                            i.putExtra("flag",flag);
                            i.putExtra("x",x_bd);
                            i.putExtra("y",y_bd);
                            /*  获取Building的ID*/
                            //dbUtils.getBuildingId().get(random);
                            //i.putExtra("longitude",location.getLatitude());
                            startActivity(i);
                        }
                    });

                }
            });
        }

    }
    public boolean isServiceOK() {
        Log.d(TAG, "checking google services version!");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainsActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play Service is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "An error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }
}
