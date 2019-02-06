package upec.projetandroid2017_2018;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LogIn extends AppCompatActivity {
    private EditText user_name, pw;
    private Button logIn;
    private String usrName , password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        user_name = (EditText) findViewById(R.id.login);
        pw = (EditText) findViewById(R.id.password);

        logIn = (Button) findViewById(R.id.button);



        user_name.requestFocus();
    }

    public void logIn(View view){
        String str = "";
        if (user_name.getText().toString().equals("") && pw.getText().toString().equals(""))
            str = "Veuillez remplir les champs nom d'utilisateur et mot de passe.";
        else if (user_name.getText().toString().equals(""))
            str = "Veuillez remplir le champ nom d'utilisateur.";
        else if (pw.getText().toString().equals(""))
            str = "Veuillez remplir le champ mot de passe.";
        else {
            usrName = user_name.getText().toString();
            password = pw.getText().toString();
            try {
                String res =new ConnectDb(this).execute("logIn", usrName,password).get().get(0);
                if (res.length()>6 && res.substring(0,7).equals("success")){
                    Intent app = new Intent(getApplicationContext(),Main3Activity.class);
                    app.putExtra("usrName",usrName);
                    app.putExtra("password",password);
                    app.putExtra("email",res.substring(10));
                    startActivity(app);
                    finish();
                }else Toast.makeText(this,"Nom ou mot de passe incorrect",Toast.LENGTH_SHORT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (!str.equals("")) {
            Toast.makeText(LogIn.this, str, Toast.LENGTH_LONG).show();

        }
    }





}
