package me.stockmarker.android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import me.stockmarker.android.R;

public class CompanyDetailActivity extends ActionBarActivity {
    private ArrayList<Company> companyList = new ArrayList<>();
    private int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        Intent i = getIntent();
        if (i != null) {
            onNewIntent(i);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        companyList = intent.getParcelableArrayListExtra("list");
        selected = intent.getIntExtra("selected", -1);

        TextView tv = (TextView) findViewById(R.id.company_stuff);
        tv.setText(companyList.get(selected).toString());
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
