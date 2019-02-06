package upec.projetandroid2017_2018;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by greg9 on 08/03/2018.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private Main2Activity context;
    private ImageButton edit,delete;
    private TextView itemName;

    public ItemAdapter(Main2Activity context, ArrayList<Item> items) {
        super(context,0, items);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemicon, parent, false);
        }
        itemName = (TextView) convertView.findViewById(R.id.item);
         edit = (ImageButton) convertView.findViewById(R.id.edit);
        delete = (ImageButton) convertView.findViewById(R.id.delete);
       if(context.getMode()) {
           edit.setVisibility(View.VISIBLE);
           delete.setVisibility(View.VISIBLE);
       }else itemName.getLayoutParams().width=LinearLayout.LayoutParams.MATCH_PARENT;

        itemName.setText(item.getName());

        if(item.getType()==ItemType.LIST) {
            LinearLayout lin=(LinearLayout)convertView.findViewById(R.id.linear) ;
            lin.setVisibility(View.VISIBLE);
            TextView tx=(TextView)convertView.findViewById(R.id.version_number) ;
            tx.setText(item.getNbElements());
            itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context,ItemList.class);
                   intent.putExtra("listName",item.getName());
                   intent.putExtra("lid",item.getId());
                    intent.putExtra("usrName",context.getUsrName());
                    intent.putExtra("password",context.getPassword());
                    intent.putExtra("email",context.getEmail());
                   context.startActivity(intent);
                }
            });
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.edit(item.getId(),item.getName());

            }
        });

       delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(item.getId());
            }
        });

        return convertView;
    }


}
