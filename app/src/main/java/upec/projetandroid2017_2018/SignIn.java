package upec.projetandroid2017_2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class SignIn extends AppCompatActivity {
    private EditText userName , password , password2 , mail;
    private String s_userName , s_password , s_password2 , s_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        mail = (EditText) findViewById(R.id.mail);
    }

    public void signIn(View view){
        String msg ="";
        if(userName.getText().toString().equals("") && password.getText().toString().equals("")
                && mail.getText().toString().equals("") && password2.getText().toString().equals(""))
            msg="Veuillez remplir les champs nom d'utilisateur, mail et mot de passe.";
        else if (userName.getText().toString().equals("")) msg = "Veuillez remplir le champ nom d'utilisateur.";
        else if (mail.getText().toString().equals("")) msg = "Veuillez remplir le champ email.";
        else if (password.getText().toString().equals("") || password2.getText().toString().equals(""))
            msg = "Veuillez remplir le champ mot de passe.";
        else {
            s_userName = userName.getText().toString();
            s_mail = mail.getText().toString();
            s_password = password.getText().toString();
            s_password2= password2.getText().toString();
            try {
                String res= new ConnectDb(this).execute("signIn",s_userName,s_mail,s_password,s_password2).get().get(0);
                if (res.equals("success")){
                    Intent app = new Intent(getApplicationContext(),Main3Activity.class);
                    app.putExtra("usrName",s_userName);
                    app.putExtra("password",s_password);
                    app.putExtra("email",s_mail);

                    startActivity(app);
                    finish();
                }else if(res.equals("user name already existemail already exist")){
                    msg="Le nom d'utilisateur et l'email existent déjà.";
                }else if(res.equals("user name already exist")){
                    msg="Ce nom d'utilisateur existe déjà.";
                }else if(res.equals("email already exist")){
                    msg="Cet email existe déjà.";
                }else if(res.equals("password does not match")) {
                    msg="Les mots de passe de correspondent pas.";
                }else{

                    Toast.makeText(SignIn.this, "erreur", Toast.LENGTH_LONG).show();
                    finish();

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if(!msg.equals("")) {
            Toast.makeText(SignIn.this, msg, Toast.LENGTH_LONG).show();

        }
    }
}
