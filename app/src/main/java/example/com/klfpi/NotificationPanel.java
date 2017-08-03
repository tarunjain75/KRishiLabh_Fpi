package example.com.klfpi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import example.com.klfpi.Adapter.NotifyAdapter;
import example.com.klfpi.Data.DataNotifyInfo;

/**
 * Created by User on 7/30/2017.
 */

public class NotificationPanel extends Activity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<String>Firm=new ArrayList<>();
    ArrayList<String>Status=new ArrayList<>();
    ArrayList<DataNotifyInfo> dataNotifyInfo=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_panel);
        final SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
        Log.e("Name",settings.getString("company",null));
        DatabaseReference Myref= FirebaseDatabase.getInstance().getReference("Notification").child(settings.getString("company",null));
        ValueEventListener postlistner=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hello");// Get Post object and use the values to update the UI

                try{
                HashMap<String,Object> name = new HashMap<String, Object>();
                name = (HashMap<String, Object>)dataSnapshot.getValue();
                for ( Map.Entry<String, Object> entry : name.entrySet()) {
                    Firm.add((String)entry.getKey());
                    //Tab tab = entry.getValue();
                    // do something with key and/or tab
                }
                Log.e("NAME",name.toString());
                Log.e("Firm",Firm.toString());
                String []key=new String [name.size()];
                int i=0;
                for(String k:name.keySet())
                {
                    key[i] = k;
                    System.out.print(k);
                    Object USER = name.get(key[i]);
                    HashMap<String, Object> test = (HashMap<String, Object>) USER;
                    Log.e("test",test.toString());

                    /*Firm.add((String) test.get("companyName"));*/
                    Status.add((String) test.get("status"));
                }
                for(int j=0;j<Firm.size();j++){
                    dataNotifyInfo.add(new DataNotifyInfo(Firm.get(j),Status.get(j)));
                }
                Log.e("datanotify",dataNotifyInfo.toString());
                }
                catch (ClassCastException classCastException){
                    Log.e("Exception",classCastException.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Test", "loadPost:onCancelled", databaseError.toException());
            }
        };
        Myref.addValueEventListener(postlistner);



        recyclerView = (RecyclerView) findViewById(R.id.notification_recyclerView);
        layoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotifyAdapter(dataNotifyInfo, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
