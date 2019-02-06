package upec.projetandroid2017_2018;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public abstract class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    protected String usrName , password , email;
    protected ListView list;
    protected TextView user;
    protected FloatingActionButton add;
    protected FloatingActionButton hist;
    protected enum  State {Private, Public,Other};
    protected State state;
    protected ItemType type;
    protected String title;
    protected String lid;
    protected boolean editionMode;
    protected ItemAdapter adapter;
    protected Toolbar toolbar;
    protected SharedPreferences shared;
    protected ConstraintLayout cl;

    protected  TextView rv;
    protected ImageView im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRessourceId());
         cl= findViewById(R.id.userAccLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list=(ListView)findViewById(R.id.vlist);

        editionMode=false;
        this.add = findViewById(R.id.fab);
        hist=findViewById(R.id.fab2);
        hist.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);
        rv=findViewById(R.id.back);
        im=findViewById(R.id.imageback);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        usrName=getIntent().getStringExtra("usrName");
        password=getIntent().getStringExtra("password");
        email=getIntent().getStringExtra("email");

        final View headerLayout = navigationView.getHeaderView(0);
        user=(TextView)headerLayout.findViewById(R.id.username);
        user.setText(usrName);
        TextView mail=(TextView)headerLayout.findViewById(R.id.email);
        mail.setText(email);


        shared = getSharedPreferences("shared2", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("login",usrName);
        editor.putString("password",password);
        editor.putString("mail",email);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editionMode) {
            editionMode=!editionMode;
            toolbar.getMenu().findItem(R.id.editionMode).setTitle(editionMode?"DÉSACTIVER":"MODE EDITION");
            add.setVisibility(editionMode?View.INVISIBLE:View.VISIBLE);
            hist.setVisibility(editionMode?View.INVISIBLE:View.VISIBLE);
            show(type,lid);
            return true;
        }
        else if(id==R.id.update){
            show(type,lid);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id== R.id.lPrivate) {
            Intent intent=new Intent(getApplicationContext(),PrivateList.class);
            intent.putExtra("usrName",usrName);
            intent.putExtra("password",password);
            intent.putExtra("email",email);
            startActivity(intent);
        }else if(id == R.id.lPublic){
            Intent intent=new Intent(getApplicationContext(),PublicList.class);
            intent.putExtra("usrName",usrName);
            intent.putExtra("password",password);
            intent.putExtra("email",email);
            startActivity(intent);
        }else if(id == R.id.uAccount){
            Intent intent=new Intent(getApplicationContext(),UserAccount.class);
            intent.putExtra("usrName",usrName);
            intent.putExtra("password",password);
            intent.putExtra("email",email);
            startActivity(intent);
        }else if(id == R.id.Historic) {
            Intent intent = new Intent(getApplicationContext(), Historique.class);
            intent.putExtra("usrName", usrName);
            intent.putExtra("password", password);
            intent.putExtra("email", email);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(View view){
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void add()  {
        try{
            ArrayList<String> l;
            String msg="";
            if (type.equals(ItemType.LIST)){
                l=new ConnectDb(this).execute("Add","addList.php",usrName,title,state.toString()).get();
                msg="La liste est ajoutée.";
            }else{
                l=new ConnectDb(this).execute("Add","addElement.php",usrName,lid,title).get();
                msg="L'élément est ajouté.";
            }
            if(l.get(0).equals("success")) {
                show(type,lid);
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }else Toast.makeText(this, "problème!!!", Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void show(ItemType type , String lid){
        try {
            ArrayList<String> l;
            ArrayList<Item> li= new ArrayList<>();
            String id="";
            if(type.equals(ItemType.LIST)){
                String nb="";
                l= new ConnectDb(this).execute("get","getList.php",usrName,String.valueOf(state)).get();
                for(int i=0;i<l.size();i++){
                    if(i%3==0)id=l.get(i);
                    else if(i%3==1) nb=l.get(i);
                    else li.add(new Item(id,l.get(i),nb,type));
                }
            }
            else{
                l= new ConnectDb(this).execute("get","getElement.php",lid,String.valueOf(state)).get();
                for(int i=0;i<l.size();i++){
                    if(i%2==0)id=l.get(i);
                    else li.add(new Item(id,l.get(i),type));
                }
                this.type=type;
                this.lid=lid;
            }


            adapter=new ItemAdapter(this,li);
            list.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public boolean getMode(){
        return editionMode;
    }

    public void edit(String id,String nameItem) {
        final String ide = id;
        LayoutInflater layoutInflater = LayoutInflater.from(Main2Activity.this);
        View promptsView;
        if(type.equals(ItemType.LIST))promptsView= layoutInflater.inflate(R.layout.dialog_addlist, null);
        else promptsView= layoutInflater.inflate(R.layout.dialog_addelem, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Main2Activity.this);
        alertDialogBuilder.setView(promptsView);
        final EditText Ltitlet = (EditText) promptsView
                .findViewById(R.id.title);

        Ltitlet.setText(nameItem);
        Ltitlet.setSelection(Ltitlet.length());
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Modifier",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                title =Ltitlet.getText().toString();
                                editItem(ide);
                            }
                        })
                .setNegativeButton("Annuler",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Toast.makeText(this, ""+ide, Toast.LENGTH_LONG).show();
    }

    public void editItem(String id){
        try{
            if(type.equals(ItemType.LIST)) {
                if (new ConnectDb(this).execute("Edit","editList.php", title, id,usrName).get().get(0).equals("success")) {
                    show(type, "");
                    Toast.makeText(this, "la liste est modifiée.", Toast.LENGTH_LONG).show();
                } else Toast.makeText(this, "problème!!!", Toast.LENGTH_LONG).show();
            }else{
                if (new ConnectDb(this).execute("Edit","editElement.php",usrName, title, id).get().get(0).equals("success")) {
                    show(type, lid);
                    Toast.makeText(this, "l'element est modifié.", Toast.LENGTH_LONG).show();
                } else Toast.makeText(this, "problème!!!", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public void delete(String d) {
        final String lid=d;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Etes-vous sûr ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteItem(lid);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setTitle("Alert!")
                .setIcon(R.drawable.ic_action_deletedialog);
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void deleteItem(String id){
        Toast.makeText(this, ""+id, Toast.LENGTH_LONG).show();
        try{
            if(type.equals(ItemType.LIST)) {
                if (new ConnectDb(this).execute("delete","deleteList.php", id,usrName).get().get(0).equals("success")) {
                    show(type, "");
                    Toast.makeText(this, "la liste est suprimée", Toast.LENGTH_LONG).show();
                } else Toast.makeText(this, "problème!!!", Toast.LENGTH_LONG).show();
            }else{
                if (new ConnectDb(this).execute("delete","deleteElement.php",usrName, id).get().get(0).equals("success")) {
                    show(type, lid);
                    Toast.makeText(this, "L'élément est supprimé.", Toast.LENGTH_LONG).show();
                } else Toast.makeText(this, "problème!!!", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public abstract int getLayoutRessourceId();


    public String getTitle2() {
        return title;
    }

    public String getUsrName() {
        return usrName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
