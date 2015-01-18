package me.stockmarker.android;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.stockmarker.android.R;

public class CompanyDetailActivity extends ActionBarActivity {
    private ArrayList<Company> companyList = new ArrayList<>();
    private int selected = -1;
    private Company cur;
    
    private CompanyDetailListAdapter cdla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        restoreActionBar();

        Intent i = getIntent();
        if (i != null) {
            onNewIntent(i);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        companyList = intent.getParcelableArrayListExtra("list");
        selected = intent.getIntExtra("selected", -1);
        cur = companyList.get(selected);
        
        getSupportActionBar().setTitle(cur.getName());
        
        cdla = new CompanyDetailListAdapter(this);

        ListView lv = (ListView) findViewById(R.id.company_stuff);
        lv.setAdapter(cdla);
        
        cdla.put("Company", cur.getName());
        cdla.put("Ticker", cur.getTicker());

        HashMap<String, String> otherData = cur.getOtherData();

        for (String key : otherData.keySet()) {
            cdla.put(key, otherData.get(key));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_company_detail, menu);
        return true;
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
        }

        return super.onOptionsItemSelected(item);
    }
}
