package me.stockmarker.android;

import android.os.AsyncTask;
import android.util.Log;

import org.json.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by ktomega on 1/18/15.
 */
public class APIHelper {
    private static final String API_ROOT = "http://35.2.106.52:8000";
    public static final String GIANT_JSON = "{\n" +
            "  \"companies\": [\n" +
            "    {\n" +
            "      \"Company\": \"Radioshack\",\n" +
            "      \"Description\": \"RadioShack Corporation operates a chain of retail consumer electronics goods and services stores located throughout the United States and Mexico, along with wireless phone kiosks in the US, and dealer outlets worldwide. The Company offers consumers wireless phone and other electronic products and services from national brands and exclusive private brands and wireless carriers.\",\n" +
            "      \"Industry\": \"Retail - Discretionary\",\n" +
            "      \"Sector\": \"Consumer Discretionary\",\n" +
            "      \"Sub-Industry\": \"Consumer Elec & Applc Stores\",\n" +
            "      \"Ticker\": \"RSH\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Company\": \"Sears Holding Corp\",\n" +
            "      \"Description\": \"Sears Holdings Corporation is a broadline retailer with full-line and specialty retail stores in the United States and Canada. The Company retails home appliances, as well as tools, lawn and garden products, home electronics, and other products. Sears Holdings also provides automotive repair and maintenance.\",\n" +
            "      \"Industry\": \"Retail - Discretionary\",\n" +
            "      \"Sector\": \"Consumer Discretionary\",\n" +
            "      \"Sub-Industry\": \"Department Stores\",\n" +
            "      \"Ticker\": \"SHLD\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Company\": \"Wet Seal Inc\",\n" +
            "      \"Description\": \"Wet Seal, Inc. retails fashionable and contemporary apparel and accessory items. The Company operates its stores in the United States, the District of Columbia, and Puerto Rico. Wet Seal operates its stores under the names Wet Seal Contempo Casuals, Limbo Lounge, and Arden B. stores.\",\n" +
            "      \"Industry\": \"Retail - Discretionary\",\n" +
            "      \"Sector\": \"Consumer Discretionary\",\n" +
            "      \"Sub-Industry\": \"Specialty Apparel Stores\",\n" +
            "      \"Ticker\": \"WTSL\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Company\": \"Kior Inc\",\n" +
            "      \"Description\": \"KiOR, Inc. intends to produce renewable fuels. The Company developed a technology that converts non-food biomass into hydrocarbon-based oil. The renewable crude oil can be refined, using standard refinery equipment, into gasoline and diesel fuels.\",\n" +
            "      \"Industry\": \"Renewable energy\",\n" +
            "      \"Sector\": \"Energy\",\n" +
            "      \"Sub-Industry\": \"Biofuels\",\n" +
            "      \"Ticker\": \"KIORQ\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Company\": \"Caesa's Entertainment Corp\",\n" +
            "      \"Description\": \"Caesars Entertainment Corp., is a gaming company. The Company operates casino resorts on multiple continents. Caesars' also owns an on-line gaming business, providing for real money casino, bingo and poker games in the United Kingdom and play for fun offerings in other jurisdictions.\",\n" +
            "      \"Industry\": \"Gaming, Lodging & Restaurants\",\n" +
            "      \"Sector\": \"Consumer Discretionary\",\n" +
            "      \"Sub-Industry\": \"Casinos & Gaming\",\n" +
            "      \"Ticker\": \"CZR\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Company\": \"Compusa Inc\",\n" +
            "      \"Description\": \"KiOR, Inc. intends to produce renewable fuels. The Company developed a technology that converts non-food biomass into hydrocarbon-based oil. The renewable crude oil can be refined, using standard refinery equipment, into gasoline and diesel fuels.\",\n" +
            "      \"Industry\": \"Retail - Discretionary\",\n" +
            "      \"Sector\": \"Consumer Discretionary\",\n" +
            "      \"Sub-Industry\": \"Consumer Elec & Applc Stores\",\n" +
            "      \"Ticker\": \"CPU\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    static class JSONRetrieverTask extends AsyncTask<String, Void, JSONObject> {

        private Exception exception;
        private JSONExecutor exec;
        
        public void addRunner(JSONExecutor je) {
            exec = je;
        }

        protected JSONObject doInBackground(String... urls) {
            return getEndpoint(urls[0]);
        }

        protected void onPostExecute(JSONObject obj) {
            if (exec != null) exec.processJSON(obj);
        }
    }
    
    interface JSONExecutor {
        void processJSON(JSONObject obj);
    }
    
    public static JSONObject getEndpoint(String endpoint) {
        try {
            return new JSONObject(GIANT_JSON);
        } catch (Exception e) {
            Log.e("APIHelper", e.getMessage());
            return null;
        }
        /*try {
            URL url = new URL(API_ROOT + endpoint);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            
            while (!br.ready()) {}
            
            String data = "", input;
            while ((input = br.readLine()) != "") {
                data += input;
            }
            
            return new JSONObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
    }
    
    private static void levelPrint(String str, int level) {
        String delim = "..";
        String out = "";
        for (int i = 0; i < level; i++) out += delim;
        out += str;
        Log.e("APIHelper", out);
    }
    
    public static void printJSON(JSONObject arr, int level) {
        if (arr == null) return;
        
        Iterator<String> keys = arr.keys();
        do {
            try {
                String curKey = keys.next();
                Object next = arr.get(curKey);
                if (next.getClass() == JSONObject.class) {
                    levelPrint("{", level);
                    printJSON((JSONObject) next, level + 1);
                    levelPrint("}", level);
                } else if (next.getClass() == JSONArray.class) {
                    JSONArray n = (JSONArray) next;
                    levelPrint("[", level);
                    printJSON(n, level + 1);
                    levelPrint("]", level);
                } else {
                    levelPrint(curKey + " => " + next.toString(), level);
                }
            } catch (Exception e) {
                
            }
        } while (keys.hasNext());
    }
    
    public static void printJSON(JSONArray arr, int level) {
        if (arr == null) return;
        
        for (int i = 0; i < arr.length(); i++) {
            try {
                Object next = arr.get(i);

                if (next.getClass() == JSONObject.class) {
                    levelPrint("{", level);
                    printJSON((JSONObject) next, level + 1);
                    levelPrint("}", level);
                } else if (next.getClass() == JSONArray.class) {
                    JSONArray n = (JSONArray) next;
                    levelPrint("[", level);
                    printJSON(n, level + 1);
                    levelPrint("]", level);
                } else {
                    levelPrint(next.toString(), level);
                }
            } catch (Exception e) {
                
            }
        }
    }
}