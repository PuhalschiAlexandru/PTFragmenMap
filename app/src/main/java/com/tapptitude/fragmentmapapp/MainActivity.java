package com.tapptitude.fragmentmapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mai_i_add:
                onAddClicked();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void onAddClicked() {
        // TODO: 30/06/2017 Implement
        Intent intent = new Intent(MainActivity.this,MapActivity.class);
        startActivity(intent);
    }
}
