package me.ivanworld.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;

import me.ivanworld.test.utils.DBOpenHelper;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class HelloActivity extends AppCompatActivity {

    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteStudioService.instance().start(this);

        dbOpenHelper = new DBOpenHelper( HelloActivity.this,"db_building",null,1 );

        setContentView(R.layout.activity_hello);
        Button login = (Button) findViewById(R.id.login);
        Button create = (Button) findViewById(R.id.create);

    }

    public void loginClick(View view){
        Intent intent = new Intent(HelloActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void createClick(View view){
        Intent intent2 = new Intent(HelloActivity.this,CreateActivity.class);
        startActivity(intent2);
    }
}
