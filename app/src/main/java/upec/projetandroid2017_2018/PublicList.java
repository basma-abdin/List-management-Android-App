package upec.projetandroid2017_2018;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by greg9 on 21/03/2018.
 */

public class PublicList extends Main2Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        add.setVisibility(View.VISIBLE);
        state=State.Public;
        type=ItemType.LIST;
        show(type,"");
        im.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.INVISIBLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater;
                layoutInflater = LayoutInflater.from(PublicList.this);
                View promptsView;
                promptsView = layoutInflater.inflate(R.layout.dialog_addlist, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        PublicList.this);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main2, menu);
    toolbar.getMenu().findItem(R.id.update).setVisible(true);
    toolbar.getMenu().findItem(R.id.editionMode).setVisible(true);
    return true;
    }


    @Override
    public int getLayoutRessourceId() {
        return R.layout.activity_main2;
    }
}
