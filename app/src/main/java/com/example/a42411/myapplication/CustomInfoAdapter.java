package com.example.a42411.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private final View infoWindow;
    private Context mContext;

    public CustomInfoAdapter(Context mContext) {
        this.mContext = mContext;
        infoWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info,null);

    }
    private void windowText(Marker marker,View view){
        String title = marker.getTitle();
        TextView tvTitle = (TextView)view.findViewById(R.id.title);
        if(!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView)view.findViewById(R.id.snippet);
        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        windowText(marker,infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        windowText(marker,infoWindow);
        return infoWindow;
    }
}
