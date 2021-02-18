package utils;

import java.util.ArrayList;

public class YFZPreventError {
    public static boolean checkArrayList(ArrayList arrayList){
        if(null!=arrayList){
            if(arrayList.size()>0){
                return true;
            }
        }
        return false;
    }
}
