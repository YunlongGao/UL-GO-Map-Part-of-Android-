package me.ivanworld.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import me.ivanworld.test.model.login;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateActivity extends AppCompatActivity {


    private EditText ed_number;
    private EditText ed_pwd;
    private TextView tv_status;
    private static String check;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ed_number = (EditText) findViewById(R.id.ed_number);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        tv_status = (TextView) findViewById(R.id.login_status);
        ed_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void create(View v){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final String usn = ed_number.getText().toString().trim();
        final String psw = ed_pwd.getText().toString().trim();
        Log.e("usn+",usn);
        Log.e("psw+",psw);

        final Request request = new Request.Builder()
                .url("http://192.168.0.71:8080/adserver/test/testClass/create.action?usn="+usn+"&psw="+psw+"")
                //.url("http://10.52.18.49:8080/adserver/test/testClass/create.action?usn="+usn+"&psw="+psw+"")
                .build();

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                login lo = JSON.parseObject(res,login.class);
                Log.e("check",lo.getCheck());
                check = lo.getCheck();
                showResponse(check);
            }

        });
//    if(check.equals("1")){
//        Toast.makeText(MainActivity.this,"Login successed!",Toast.LENGTH_SHORT).show();
//    }else {
//        Toast.makeText(MainActivity.this,"Login failed!",Toast.LENGTH_SHORT).show();
//    }

    }

    public void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateActivity.this,check,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
