package upec.projetandroid2017_2018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shared = getSharedPreferences("shared2", MODE_PRIVATE);
        if(shared.contains("login") && shared.contains("password")){
            Intent app=new Intent(this,Main3Activity.class);
            app.putExtra("usrName",shared.getString("login","def"));
            app.putExtra("password",shared.getString("password","pass"));
            app.putExtra("email",shared.getString("mail","mail"));

            startActivity(app);
            finish();

        }
        setContentView(R.layout.activity_main);


    }

    public void logIn(View view){
        Intent intent = new Intent(getApplicationContext(),LogIn.class);
        startActivity(intent);
    }

    public void signIn(View view){
        Intent intent = new Intent(getApplicationContext(),SignIn.class);
        startActivity(intent);
    }

}