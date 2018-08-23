package me.ivanworld.test.utils;

import java.util.ArrayList;
import java.util.List;

public class util {

    public List<String> toList(String arr){
        arr = arr.replace( "\"", "" );
        arr = arr.replace( "[","" );
        arr = arr.replace( "]","" );
        String[] resA = arr.split( "," );
        List<String> list = new ArrayList<String>(  );
        for (int i = 0; i < resA.length;i++){
            list.add( resA[i] );
        }
        return list;
    }
}
