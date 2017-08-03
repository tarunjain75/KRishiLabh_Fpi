package example.com.klfpi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 4/1/2017.
 */

public class UpdateItemActivity extends Activity {
    EditText nameItem,availbaleQuant,itemRate,unit;
    TextView update,gotoDash;
    String company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_demand);
        initView();
    }

    private void initView() {
        nameItem = (EditText) findViewById(R.id.input_ItemName);
        availbaleQuant = (EditText) findViewById(R.id.input_ItemQuantity);
        itemRate = (EditText) findViewById(R.id.input_ItemRate);
        update = (TextView) findViewById(R.id.input_update);
        unit = (EditText) findViewById(R.id.input_unit);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        company = settings.getString("company", "");
        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            nameItem.setText(bundle.getString("product_Name"));
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();
                    Product user2 = new Product(nameItem.getText().toString(), availbaleQuant.getText().toString(), itemRate.getText().toString(), unit.getText().toString());
                    myRef2.child("FPI_Product_list").child(company).child(nameItem.getText().toString()).setValue(user2);
                    if(bundle!=null){
                        onBackPressed();
                    }
                    Toast.makeText(UpdateItemActivity.this,"Item Updated Succesfully",Toast.LENGTH_LONG).show();

                } catch (DatabaseException r) {
                    System.out.println(r);
                }


            }
        }); //setOnClickListener
    }
}
