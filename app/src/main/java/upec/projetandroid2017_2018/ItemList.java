package upec.projetandroid2017_2018;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by greg9 on 21/03/2018.
 */

public class ItemList extends Main2Activity {
    private ListView historic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        im.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.INVISIBLE);
        add=findViewById(R.id.fab);
        add.setVisibility(View.VISIBLE);
        hist.setVisibility(View.VISIBLE);
        type=ItemType.ELEMENT;
        lid=getIntent().getStringExtra("lid");
        setTitle(getIntent().getStringExtra("listName"));
        show(type,lid);
        historic=findViewById(R.id.navList);
        showHist();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater;
                layoutInflater = LayoutInflater.from(ItemList.this);
                View promptsView;

                promptsView = layoutInflater.inflate(R.layout.dialog_addelem, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ItemList.this);
                alertDialogBuilder.setView(promptsView);
                final EditText Ltitlet = (EditText) promptsView
                        .findViewById(R.id.title);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ajouter",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        title = Ltitlet.getText().toString();
                                        if (!title.equals("")) add();
                                        else Ltitlet.setFocusable(true);
                                    }
                                })
                        .setNegativeButton("Annuler",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
       final DrawerLayout drawer= (DrawerLayout) findViewById(R.id.drawer_lay);
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                showHist();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               drawer.openDrawer(Gravity.END);
            }
        });


    }

    @Override
    public int getLayoutRessourceId() {
        return R.layout.activity_main3;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        toolbar.getMenu().findItem(R.id.update).setVisible(true);
        toolbar.getMenu().findItem(R.id.editionMode).setVisible(true);
        return true;
    }

    public void showHist(){
        ArrayList<String> li= null;
        try {
            li = new ConnectDb(this).execute("HistElem",lid).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, li);
        historic.setAdapter(mAdapter);
    }

}
