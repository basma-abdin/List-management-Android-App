package upec.projetandroid2017_2018;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class UserAccount extends Main2Activity{
    private TextView usrname,mail;
    private String newName;
    private ImageButton modifie;
    private Button passwordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cl.setVisibility(View.VISIBLE);
        im.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.INVISIBLE);
        passwordBtn = (Button) findViewById(R.id.password);
        usrname = (TextView) findViewById(R.id.user);
        mail = (TextView) findViewById(R.id.email);
        usrname.setText(usrName);
        mail.setText(email);
        modifie = (ImageButton) findViewById(R.id.modifie);

    }
     @Override
    public int getLayoutRessourceId() {
        return R.layout.activity_main2;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
    public void modifie(View view){
        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(UserAccount.this);
        View promptsView;

        promptsView = layoutInflater.inflate(R.layout.dialig_modifiename, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UserAccount.this);
        alertDialogBuilder.setView(promptsView);
        final EditText uname = (EditText) promptsView
                .findViewById(R.id.newname);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Modifier",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                newName = uname.getText().toString();
                                if (!newName.equals("")) changeName();
                                else uname.setFocusable(true);
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
    public  void  changeName(){
        String result;
        if (newName!=""){
            try {
                result =new ConnectDb(this).execute("Change","changeName.php",usrName,newName).get().get(0);
                if(result.equals("success")){
                    usrName=newName;
                    usrname.setText(usrName);
                    user.setText(usrName);
                    Toast.makeText(this,result , Toast.LENGTH_LONG).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(result+"Ce nom est déjà utilisé")

                            .setTitle("Alert!")
                            .setIcon(R.drawable.ic_action_deletedialog);
                   final AlertDialog alert = builder.create();
                    alert.show();
                    final Handler handler  = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (alert.isShowing()) {
                                alert.dismiss();
                            }
                        }
                    };

                    alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            handler.removeCallbacks(runnable);
                        }
                    });

                    handler.postDelayed(runnable, 5000);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
    public void changePass(View view){
        LayoutInflater layoutInflater;
        layoutInflater = LayoutInflater.from(UserAccount.this);
        View promptsView;

        promptsView = layoutInflater.inflate(R.layout.dialog_modifie_pass, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UserAccount.this);
        alertDialogBuilder.setView(promptsView);
        final EditText pass1 = (EditText) promptsView
                .findViewById(R.id.pass1);final EditText pass2 = (EditText) promptsView.findViewById(R.id.pass2);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Changer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               if(pass1.getText().toString().equals(pass2.getText().toString())
                                       && !pass1.getText().toString().equals("")){
                                   String mdp=pass1.getText().toString();
                                   change(mdp);
                               }
                               else try {
                                   cantChange();
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
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
    public void change(String pass) {
        if (pass != "") {
            try {
                String x= new ConnectDb(this).execute("Change", "changePass.php", usrName, pass).get().get(0);
                if (x.equals("success")) {
                    password = pass;
                    Toast.makeText(this,"Votre mot de passe a été changé" , Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Vous n'avez pas changé de mot de passe", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
    public void cantChange() throws InterruptedException {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Les deux mots de passes ne sont pas identiques")

                .setTitle("Alert!")
                .setIcon(R.drawable.ic_action_deletedialog);
        final AlertDialog alert = builder.create();
        alert.show();
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            handler.removeCallbacks(runnable);
        }
        });

        handler.postDelayed(runnable, 3000);

    }


}
