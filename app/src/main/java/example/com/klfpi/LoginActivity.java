package example.com.klfpi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

/**
 * Created by User on 6/30/2017.
 */

public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;
    String emailid,pass,email;
    TextView textView;
    private FirebaseAuth.AuthStateListener mAuthListener;
    LovelyProgressDialog lovelyProgressDialog;






    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressBar spinner;
    public CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_in_layout);
        //final User USER=new User(LoginActivity.this);

        spinner = (ProgressBar)findViewById(R.id.login_progress);
        cardView=(CardView)findViewById(R.id.card_view);
        textView=(TextView)findViewById(R.id.create_account);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    user.sendEmailVerification();
                    Log.d("", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };




        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmailView.getText().length()==0 || !isEmailValid(mEmailView.getText().toString())){
                    mEmailView.setError("Enter a valid Email id");
                }else if(mPasswordView.getText().length()==0 || !isPasswordValid(mPasswordView.getText().toString())){
                    mPasswordView.setError("password length too short");
                }else{
                    attemptLogin();}

            }
        });
    }
    private void attemptLogin() {


        lovelyProgressDialog=new LovelyProgressDialog(this);
                lovelyProgressDialog.setIcon(R.drawable.ic_cast_connected_white_36dp)
                .setTitle(R.string.connecting_to_server)
                .setTopColorRes(R.color.teal)
                .show();
//        if (mAuthTask != null) {
//            return;
//        }
//
//        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        emailid=mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        pass=mPasswordView.getText().toString();
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor2 = settings.edit();
        editor2.putString("Email",emailid);
        editor2.putString("Password",pass);



        editor2.commit();

//



        signin(email,password);



    }




    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;

    }




    public void signin(final String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("New User", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("New User", "signInWithEmail", task.getException());
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Authentication failed.Unable to sign in",
                                    Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Successfull ", Toast.LENGTH_SHORT).show();
                            User U=new User(getApplicationContext());
                            U.setName(email);
                            System.out.println("TEST"+email);
                            Intent in=new Intent(LoginActivity.this,MainActivity.class);
                            //in.putExtra("UID",  user.getUid());
                            startActivity(in);
                            lovelyProgressDialog.dismiss();
                            finish();

                        }

                        // ...
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
