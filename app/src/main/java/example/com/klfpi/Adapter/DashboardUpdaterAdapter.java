package example.com.klfpi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.klfpi.R;
import example.com.klfpi.UpdateItemActivity;


/**
 * Created by User on 4/1/2017.
 */

public class DashboardUpdaterAdapter extends BaseAdapter {
    LayoutInflater newinflator;
    Context context;
    ArrayList<String> itemname=new ArrayList<>();
    ArrayList <String>quantity=new ArrayList<>();
   ArrayList<String> rate=new ArrayList<>();
    ArrayList<String>  Unit=new ArrayList<String>();

    public DashboardUpdaterAdapter(Context context, ArrayList<String> item, ArrayList<String> quant, ArrayList<String> Rate,ArrayList<String>  Unit){
        this.context=context;
        this.itemname=item;
        this.quantity=quant;
        this.rate=Rate;
        this.Unit=Unit;
        newinflator=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (itemname.size() );
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=newinflator.inflate(R.layout.dashboard_card_layout,null);
        TextView item=(TextView)convertView.findViewById(R.id.ItemNAme);
        TextView Quantity=(TextView)convertView.findViewById(R.id.Quantity);
        TextView Rate =(TextView)convertView.findViewById(R.id.PriceRate);
        ImageView edit=(ImageView)convertView.findViewById(R.id.input_edit);
        item.setText(itemname.get(position));
        Quantity.setText(quantity.get(position)+" "+Unit.get(position));
        Rate.setText(rate.get(position));
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UpdateItemActivity.class);
                intent.putExtra("product_Name",itemname.get(position));
                context.startActivity(intent);

            }
        });
        return convertView;
    }
}
