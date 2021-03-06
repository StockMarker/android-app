package me.stockmarker.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class MainActivity extends ActionBarActivity {
    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private DrawerLayout mDrawerLayout;
    private ListView mNavigationDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private CompanyListAdapter cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restoreActionBar();

        cl = new CompanyListAdapter(this);

        ListView lv = (ListView) findViewById(R.id.company_list);
        lv.setAdapter(cl);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, CompanyDetailActivity.class);
                i.putParcelableArrayListExtra("list", cl.getCompanyList());
                i.putExtra("selected", position);
                startActivity(i);
            }
        });

        APIHelper.JSONRetrieverTask jrt = new APIHelper.JSONRetrieverTask();
        jrt.addRunner(new APIHelper.JSONExecutor() {
            @Override
            public void processJSON(JSONObject obj) {
                try {
                    JSONArray companies = (JSONArray) obj.get("companies");
                    for (int i = 0; i < companies.length(); i++) {
                        JSONObject comp = (JSONObject) companies.get(i);
                        
                        Company com = new Company((String) comp.get("Company"),
                                (String) comp.get("Ticker"));

                        Iterator<String> keys = comp.keys();
                        do {
                            try {
                                String curKey = keys.next();
                                Object next = comp.get(curKey);
                                if (!curKey.equals("Company") || !curKey.equals("Ticker")) {
                                    com.addData(curKey, (String) next);
                                }
                            } catch (Exception e) {

                            }
                        } while (keys.hasNext());

                        cl.add(com);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        jrt.execute("/companies");
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_example) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

