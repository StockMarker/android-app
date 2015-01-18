package me.stockmarker.android;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by ktomega on 1/18/15.
 */
public class Utils {
    public static List<String> getStringListFromParcel(Parcel in) {
        ArrayList<String> inArr = new ArrayList<>();
        in.readStringList(inArr);
        return inArr;
    }

    public static HashMap<String,String> getStringHashMapFromParcel(Parcel in) {
        HashMap<String, String> map = new HashMap<>();
        int size = in.readInt();
        
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String val = in.readString();
            
            map.put(key, val);
        }
        
        return map;
    }

    public static void writeStringHashMapFromParcel(Parcel out, HashMap<String, String> map) {
        Set<String> keySet = map.keySet();
        out.writeInt(keySet.size());
        for (String key : keySet) {
            out.writeString(key);
            out.writeString(map.get(key));
        }
    }
}
