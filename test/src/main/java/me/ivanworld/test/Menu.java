package me.ivanworld.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.a42411.myapplication.MainsActivity;

public class Menu extends AppCompatActivity {

    private static String usn;
    private Button rank_btn;
    private String stuId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        usn = intent.getStringExtra("usn");
        Log.e("usn----",usn);
        stuId = intent.getStringExtra( "stuId" );
        rank_btn = (Button)findViewById( R.id.Rank_btn );

    }

    public void go_map(View view){
        Intent intent2 = new Intent(Menu.this, MainsActivity.class);
        intent2.putExtra( "stuId",stuId );
        intent2.putExtra( "flag",-1 );
        startActivity( intent2 );
    }

    public void go_rank(View view){
        Intent intent1 = new Intent( Menu.this,RankActivity.class );
        startActivity( intent1 );
    }
}
