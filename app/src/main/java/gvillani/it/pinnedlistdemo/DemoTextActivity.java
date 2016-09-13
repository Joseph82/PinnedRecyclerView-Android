package gvillani.it.pinnedlistdemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gvillani.pinnedlist.GroupListWrapper;
import com.gvillani.pinnedlist.PinnedListLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giuseppe on 19/04/2016.
 */
public class DemoTextActivity extends AppCompatActivity {

    private PinnedListLayout mPinnedListLayout;
    private RecyclerView.Adapter mListAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_demo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initLayout();


        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "CoordinatorLayout still works! :)", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initLayout() {
        mPinnedListLayout = (PinnedListLayout) findViewById(R.id.pinned_layout);
        mRecyclerView = mPinnedListLayout.getRecyclerView();

        Contact c1 = new Contact("Ben", "Weber", R.drawable.contact1);
        Contact c2 = new Contact("Emma", "Hartmann", R.drawable.contact9);
        Contact c3 = new Contact("Gustav", "Eriksson", R.drawable.contact7);
        Contact c4 = new Contact("Lucas", "Nillson", R.drawable.contact4);
        Contact c5 = new Contact("Maia", "Andersson", R.drawable.contact11);
        Contact c6 = new Contact("Oscar", "Karlsson", R.drawable.contact6);
        Contact c7 = new Contact("Williams", "Johansson", R.drawable.contact5);
        Contact c8 = new Contact("Jayden", "De Jong", R.drawable.contact4);
        Contact c9 = new Contact("Julia", "Meijer", R.drawable.contact11);
        Contact c10 = new Contact("Lieke", "Visser", R.drawable.contact2);
        Contact c11 = new Contact("Luuk", "Tassi", R.drawable.contact3);
        Contact c12 = new Contact("Sam", "Van den Berg", R.drawable.contact1);
        Contact c13 = new Contact("Stijn", "De Vries", R.drawable.contact6);
        Contact c14 = new Contact("Thomas", "Van Dijk", R.drawable.contact5);
        Contact c15 = new Contact("Franco", "Bianchi", R.drawable.contact3);
        Contact c16 = new Contact("Giuseppe", "Villani", R.drawable.me);
        Contact c17 = new Contact("Lisa", "Verdi", R.drawable.contact9);
        Contact c18 = new Contact("Maria", "Rossi", R.drawable.contact10);
        Contact c19 = new Contact("Leonor", "Santos", R.drawable.contact9);
        Contact c20 = new Contact("Santiago", "Ferreira", R.drawable.contact1);
        Contact c21 = new Contact("Maria", "Pereira", R.drawable.contact9);
        Contact c22 = new Contact("Francisco", "Sousa", R.drawable.contact4);
        Contact c23 = new Contact("Rodrigo", "Martins", R.drawable.contact5);
        Contact c24 = new Contact("Duarte", "Gomes", R.drawable.contact6);
        Contact c25 = new Contact("Lucia", "Garcia", R.drawable.contact9);
        Contact c26 = new Contact("Pedro", "Fernandez", R.drawable.contact1);
        Contact c27 = new Contact("Daniela", "Gonzales", R.drawable.contact9);
        Contact c28 = new Contact("Alvaro", "Perez", R.drawable.contact7);
        Contact c29 = new Contact("David", "Martin", R.drawable.contact3);
        Contact c30 = new Contact("Camille", "Bernard", R.drawable.contact9);
        Contact c31 = new Contact("Louis", "Robert", R.drawable.contact1);
        Contact c32 = new Contact("Jules", "Petit", R.drawable.contact4);
        Contact c33 = new Contact("Daniel", "Kimani", R.drawable.contact8);
        Contact c34 = new Contact("Faith", "Mwangi", R.drawable.contact9);
        Contact c35 = new Contact("Jamesohn", "Kamau", R.drawable.contact1);
        Contact c36 = new Contact("Jonson", "Kariuki", R.drawable.contact3);
        Contact c37 = new Contact("Victor", "Ochieng", R.drawable.contact6);
        Contact c38 = new Contact("Aziza", "Elzarak", R.drawable.contact9);
        Contact c39 = new Contact("Youssef", "Jamil", R.drawable.contact1);
        Contact c40 = new Contact("Bob", "Miller", R.drawable.contact4);
        Contact c41 = new Contact("Carl", "Mayer", R.drawable.contact6);
        Contact c42 = new Contact("Bill", "Carson", R.drawable.contact5);
        Contact c43 = new Contact("Thom", "Sayer", R.drawable.contact1);
        Contact c44 = new Contact("Jim", "Hall", R.drawable.contact6);
        Contact c45 = new Contact("Paul", "Stroustrup", R.drawable.contact3);

        List<GroupListWrapper.Selector> contacts = new ArrayList<>();

        contacts.add(c1);
        contacts.add(c2);
        contacts.add(c3);
        contacts.add(c4);
        contacts.add(c5);
        contacts.add(c6);
        contacts.add(c7);
        contacts.add(c8);
        contacts.add(c9);
        contacts.add(c10);
        contacts.add(c11);
        contacts.add(c12);
        contacts.add(c13);
        contacts.add(c14);
        contacts.add(c15);
        contacts.add(c16);
        contacts.add(c17);
        contacts.add(c18);
        contacts.add(c19);
        contacts.add(c20);
        contacts.add(c21);
        contacts.add(c22);
        contacts.add(c23);
        contacts.add(c24);
        contacts.add(c25);
        contacts.add(c26);
        contacts.add(c27);
        contacts.add(c28);
        contacts.add(c29);
        contacts.add(c30);
        contacts.add(c31);
        contacts.add(c32);
        contacts.add(c33);
        contacts.add(c34);
        contacts.add(c35);
        contacts.add(c36);
        contacts.add(c37);
        contacts.add(c38);
        contacts.add(c39);
        contacts.add(c40);
        contacts.add(c41);
        contacts.add(c42);
        contacts.add(c43);
        contacts.add(c44);
        contacts.add(c45);

        GroupListWrapper listGroup = GroupListWrapper.createAlphabeticList(contacts, GroupListWrapper.ASCENDING);

        mListAdapter = new ContactAdapter(this, listGroup, mPinnedListLayout);
        mRecyclerView.setAdapter(mListAdapter);
    }
}