package gvillani.it.pinnedlistdemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gvillani.pinnedlist.Group;
import com.gvillani.pinnedlist.GroupListWrapper;
import com.gvillani.pinnedlist.ImageItemPinned;
import com.gvillani.pinnedlist.ItemPinned;
import com.gvillani.pinnedlist.PinnedAdapter;
import com.gvillani.pinnedlist.PinnedListLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giuseppe on 19/04/2016.
 */
public class DemoImageActivity extends AppCompatActivity {

    private PinnedListLayout mListLayout;
    private PinnedAdapter mListAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_demo);

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
        mListLayout = (PinnedListLayout) findViewById(R.id.pinned_layout);
        mRecyclerView = mListLayout.getRecyclerView();

        GroupListWrapper listGroup = createItems();

        mListAdapter = new ContactAdapter(this, listGroup, mListLayout);
        mRecyclerView.setAdapter(mListAdapter);

    }

    private GroupListWrapper createItems() {
        GroupListWrapper listGroup = new GroupListWrapper();

        ItemPinned itemD1 = new ImageItemPinned<>(new Contact("Ben", "Weber", R.drawable.contact1)); // M
        ItemPinned itemD2 = new ImageItemPinned<>(new Contact("Emma", "Hartmann", R.drawable.contact9)); //F

        List<ItemPinned> listD = new ArrayList<>();
        listD.add(itemD1);
        listD.add(itemD2);
        Group groupD = new Group(listD, R.drawable.germany);


        ItemPinned itemS1 = new ImageItemPinned<>(new Contact("Gustav", "Eriksson", R.drawable.contact7)); //M
        ItemPinned itemS2 = new ImageItemPinned<>(new Contact("Lucas", "Nillson", R.drawable.contact4)); //M
        ItemPinned itemS3 = new ImageItemPinned<>(new Contact("Maia", "Andersson", R.drawable.contact11)); //F
        ItemPinned itemS4 = new ImageItemPinned<>(new Contact("Oscar", "Karlsson", R.drawable.contact6)); //M
        ItemPinned itemS5 = new ImageItemPinned<>(new Contact("Williams", "Johansson", R.drawable.contact5)); //M

        List<ItemPinned> listS = new ArrayList<>();
        listS.add(itemS1);
        listS.add(itemS2);
        listS.add(itemS3);
        listS.add(itemS4);
        listS.add(itemS5);
        Group groupS = new Group(listS, R.drawable.sweden);


        ItemPinned itemNL1 = new ImageItemPinned<>(new Contact("Jayden", "De Jong", R.drawable.contact4)); //M
        ItemPinned itemNL2 = new ImageItemPinned<>(new Contact("Julia", "Meijer", R.drawable.contact11)); // F
        ItemPinned itemNL3 = new ImageItemPinned<>(new Contact("Lieke", "Visser", R.drawable.contact2)); //F
        ItemPinned itemNL4 = new ImageItemPinned<>(new Contact("Luuk", "Tassi", R.drawable.contact3)); //M
        ItemPinned itemNL5 = new ImageItemPinned<>(new Contact("Sam", "Van den Berg", R.drawable.contact1)); //M
        ItemPinned itemNL6 = new ImageItemPinned<>(new Contact("Stijn", "De Vries", R.drawable.contact6)); //M
        ItemPinned itemNL7 = new ImageItemPinned<>(new Contact("Thomas", "Van Dijk", R.drawable.contact5)); //M


        List<ItemPinned> listNL = new ArrayList<>();
        listNL.add(itemNL1);
        listNL.add(itemNL2);
        listNL.add(itemNL3);
        listNL.add(itemNL4);
        listNL.add(itemNL5);
        listNL.add(itemNL6);
        listNL.add(itemNL7);
        Group groupNL = new Group(listNL, R.drawable.netherlands);


        ItemPinned itemIT1 = new ImageItemPinned<>(new Contact("Franco", "Bianchi", R.drawable.contact3)); //F
        ItemPinned itemIT2 = new ImageItemPinned<>(new Contact("Giuseppe", "Villani", R.drawable.me)); // M
        ItemPinned itemIT3 = new ImageItemPinned<>(new Contact("Lisa", "Verdi", R.drawable.contact9)); //F
        ItemPinned itemIT4 = new ImageItemPinned<>(new Contact("Maria", "Rossi", R.drawable.contact10)); //M


        List<ItemPinned> listIT = new ArrayList<>();
        listIT.add(itemIT1);
        listIT.add(itemIT2);
        listIT.add(itemIT3);
        listIT.add(itemIT4);
        Group groupIT = new Group(listIT, R.drawable.italy);


        ItemPinned itemP1 = new ImageItemPinned<>(new Contact("Leonor", "Santos", R.drawable.contact9)); //F
        ItemPinned itemP2 = new ImageItemPinned<>(new Contact("Santiago", "Ferreira", R.drawable.contact1)); // M
        ItemPinned itemP3 = new ImageItemPinned<>(new Contact("Maria", "Pereira", R.drawable.contact9)); //F
        ItemPinned itemP4 = new ImageItemPinned<>(new Contact("Francisco", "Sousa", R.drawable.contact4)); //M
        ItemPinned itemP5 = new ImageItemPinned<>(new Contact("Rodrigo", "Martins", R.drawable.contact5)); //M
        ItemPinned itemP6 = new ImageItemPinned<>(new Contact("Duarte", "Gomes", R.drawable.contact6)); //M

        List<ItemPinned> listP = new ArrayList<>();
        listP.add(itemP1);
        listP.add(itemP2);
        listP.add(itemP3);
        listP.add(itemP4);
        listP.add(itemP5);
        listP.add(itemP6);
        Group groupP = new Group(listP, R.drawable.portugal);


        ItemPinned itemES1 = new ImageItemPinned<>(new Contact("Lucia", "Garcia", R.drawable.contact9)); //F
        ItemPinned itemES2 = new ImageItemPinned<>(new Contact("Pedro", "Fernandez", R.drawable.contact1)); // M
        ItemPinned itemES3 = new ImageItemPinned<>(new Contact("Daniela", "Gonzales", R.drawable.contact9)); //F
        ItemPinned itemES4 = new ImageItemPinned<>(new Contact("Alvaro", "Perez", R.drawable.contact7)); //M
        ItemPinned itemES5 = new ImageItemPinned<>(new Contact("David", "Martin", R.drawable.contact3)); //M

        List<ItemPinned> listES = new ArrayList<>();
        listES.add(itemES1);
        listES.add(itemES2);
        listES.add(itemES3);
        listES.add(itemES4);
        listES.add(itemES5);
        Group groupES = new Group(listES, R.drawable.spain);


        ItemPinned itemF1 = new ImageItemPinned<>(new Contact("Camille", "Bernard", R.drawable.contact9)); //F
        ItemPinned itemF2 = new ImageItemPinned<>(new Contact("Louis", "Robert", R.drawable.contact1)); // M
        ItemPinned itemF3 = new ImageItemPinned<>(new Contact("Jules", "Petit", R.drawable.contact4)); //M

        List<ItemPinned> listF = new ArrayList<>();
        listF.add(itemF1);
        listF.add(itemF2);
        listF.add(itemF3);
        Group groupF = new Group(listF, R.drawable.france);


        ItemPinned itemEAK1 = new ImageItemPinned<>(new Contact("Daniel", "Kimani", R.drawable.contact8)); //M
        ItemPinned itemEAK2 = new ImageItemPinned<>(new Contact("Faith", "Mwangi", R.drawable.contact9)); //F
        ItemPinned itemEAK3 = new ImageItemPinned<>(new Contact("Jamesohn", "Kamau", R.drawable.contact1)); // M
        ItemPinned itemEAK4 = new ImageItemPinned<>(new Contact("Jonson", "Kariuki", R.drawable.contact3)); //M
        ItemPinned itemEAK5 = new ImageItemPinned<>(new Contact("Victor", "Ochieng", R.drawable.contact6)); //M

        List<ItemPinned> listEAK = new ArrayList<>();
        listEAK.add(itemEAK1);
        listEAK.add(itemEAK2);
        listEAK.add(itemEAK3);
        listEAK.add(itemEAK4);
        listEAK.add(itemEAK5);
        Group groupEAK = new Group(listEAK, R.drawable.kenya);


        ItemPinned itemMA1 = new ImageItemPinned<>(new Contact("Aziza", "Elzarak", R.drawable.contact9)); //F
        ItemPinned itemMA2 = new ImageItemPinned<>(new Contact("Youssef", "Jamil", R.drawable.contact1)); // M

        List<ItemPinned> listMA = new ArrayList<>();
        listMA.add(itemMA1);
        listMA.add(itemMA2);
        Group groupMA = new Group(listMA, R.drawable.morocco);


        listGroup.addGroup(groupD);
        listGroup.addGroup(groupS);
        listGroup.addGroup(groupNL);
        listGroup.addGroup(groupIT);
        listGroup.addGroup(groupP);
        listGroup.addGroup(groupES);
        listGroup.addGroup(groupF);
        listGroup.addGroup(groupEAK);
        listGroup.addGroup(groupMA);

        return listGroup;
    }

}
