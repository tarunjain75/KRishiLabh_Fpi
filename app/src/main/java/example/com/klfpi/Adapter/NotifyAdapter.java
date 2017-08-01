package example.com.klfpi.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import example.com.klfpi.Data.DataNotifyInfo;
import example.com.klfpi.R;

/**
 * Created by User on 7/30/2017.
 */

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {
    ArrayList<DataNotifyInfo> dataNotifyInfos=new ArrayList<>();
    Context context;

    public NotifyAdapter(ArrayList<DataNotifyInfo> dataNotifyInfos, Context context) {
        this.dataNotifyInfos = dataNotifyInfos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_panel_layout,parent,false);
        NotifyAdapter.ViewHolder vh=new NotifyAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("state",dataNotifyInfos.get(position).getStatus());
        if(dataNotifyInfos.get(position).getStatus().equals("pending")){
            Log.e("check status",dataNotifyInfos.get(position).getStatus());
            holder.InfoCompany.setText(dataNotifyInfos.get(position).getFirmName());
            holder.Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.CompanyApproved.setText(dataNotifyInfos.get(position).getFirmName());
                final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notification").child(settings.getString("company",null)).child(dataNotifyInfos.get(position).getFirmName());
                reference.child("status").setValue("Approved");
                holder.paymentTabLayout.setVisibility(View.GONE);
                holder.InfoLayout.setVisibility(View.VISIBLE);
                Toast.makeText(context,"Status Approved",Toast.LENGTH_SHORT).show();

            }
        });
        }
        else {
            Log.e("check status 1",dataNotifyInfos.get(position).getStatus());
            holder.paymentTabLayout.setVisibility(View.GONE);
            holder.CompanyApproved.setText(dataNotifyInfos.get(position).getFirmName());
            holder.InfoLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataNotifyInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Pay,InfoCompany,CompanyApproved;
        LinearLayout paymentTabLayout,InfoLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            Pay=(TextView)itemView.findViewById(R.id.paymentTab);
            InfoCompany=(TextView)itemView.findViewById(R.id.CompanyName);
            CompanyApproved=(TextView)itemView.findViewById(R.id.company_NAME);
            paymentTabLayout=(LinearLayout) itemView.findViewById(R.id.paymentTabLayout);
            InfoLayout=(LinearLayout) itemView.findViewById(R.id.InfoLayout);
        }
    }
}
