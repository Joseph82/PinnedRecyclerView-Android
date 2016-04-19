package gvillani.it.pinnedlistdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Giuseppe on 09/04/2016.
 */
public class DemoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String[] mItems;
    private Intent[] mActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView list = new ListView(this);
        setContentView(list);

        buildActionsList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mItems);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

    }

    private void buildActionsList() {
        mItems = new String[] {
                "Text Pinned List",
                "Image Pinned List"
        };

        mActions = new Intent[] {
                new Intent(this, DemoTextActivity.class),
                new Intent(this, DemoImageActivity.class)
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent action = mActions[position];
        startActivity(action);
    }
}