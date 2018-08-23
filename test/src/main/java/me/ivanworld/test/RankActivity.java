package me.ivanworld.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;

import me.ivanworld.test.adapter.RecyclerAdapter;
import me.ivanworld.test.model.DataBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ivanworld.test.adapter.RecyclerAdapter;
import me.ivanworld.test.model.DataBean;
import me.ivanworld.test.model.UserRank;
import me.ivanworld.test.model.login;
import me.ivanworld.test.utils.util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RankActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
    private static List<String> list_score;
    private static List<String> list_usn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://192.168.0.71:8080/adserver/test/testClass/rankScore.action")
                //.url("http://10.52.18.49:8080/adserver/test/testClass/rankScore.action")
                .build();

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                String[] resA = res.split( "@" );
                String scoreA = resA[0];
                String usnA = resA[1];
                util ut = new util();
                list_score = ut.toList( scoreA );
                list_usn = ut.toList( usnA );
                initData(list_score,list_usn);
            }

        });

    }

    /**
     * 模拟数据
     */
    private void initData(List<String> score,List<String> usn){

        dataBeanList = new ArrayList<>();
        for (int i = 0; i < score.size(); i++) {
            dataBean = new DataBean();
            dataBean.setID(i+"");
            dataBean.setType(0);
            dataBean.setParentLeftTxt("Rank "+(i+1));
            dataBean.setParentRightTxt("Name:"+usn.get( i ));
            dataBean.setChildLeftTxt("Score:");
            dataBean.setChildRightTxt(score.get( i ));
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }

    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this,dataBeanList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }

}
