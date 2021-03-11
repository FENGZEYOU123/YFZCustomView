package yfz.utils;

import java.util.ArrayList;

public class PreventErrorUtils {

    /**
     * 检查数组不为空且数量>=1
     * @param arrayList
     * @return
     */
    public static boolean checkArrayList(ArrayList arrayList){
        if(null!=arrayList){
            if(arrayList.size()>0){
                return true;
            }
        }
        return false;
    }


}
