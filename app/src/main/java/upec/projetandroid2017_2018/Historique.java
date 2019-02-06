package upec.projetandroid2017_2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Historique extends Main2Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        im.setVisibility(View.INVISIBLE);
        rv.setVisibility(View.INVISIBLE);
        showHistory();
    }
    public void showHistory(){
        ArrayList<String> result = new ArrayList<>() ;
        try {
            result = new ConnectDb(this).execute("HistList").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
        list.setAdapter(mAdapter);

    }


    @Override
    public int getLayoutRessourceId() {
        return R.layout.activity_main2;
    }
}
