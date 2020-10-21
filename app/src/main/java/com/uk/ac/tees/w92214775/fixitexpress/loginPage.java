package com.uk.ac.tees.w92214775.fixitexpress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

import java.util.HashMap;

public class loginPage extends AppCompatActivity {

    //init
    private Button gGoogleSignIn,gPhoneAuth;
    private TextInputEditText gPhoneNumber;
    private String PHONE,CID;
    private CountryCodePicker gCCP;

    //googleSignIn
    private GoogleSignInClient gGoogleSignInClient;
    private GoogleApiClient gGoogleApiClient;
    private static final int RC_SIGN_IN = 123;

    //firebase
    private DatabaseReference gUserRef;
    private HashMap<String,String> dataMap = new HashMap<>();

    //progress dialog
    private ProgressDialog gDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //functions
        googleSignInInstance();
        gUserRef = firebase.gDatabase.getReference().child(COMM.USERS);

        //init
        gGoogleSignIn = (Button)findViewById(R.id.login_googleSignInBtn);
        gPhoneAuth = (Button)findViewById(R.id.login_loginBtn);
        gPhoneNumber = (TextInputEditText) findViewById(R.id.login_phone);
        gCCP = (CountryCodePicker)findViewById(R.id.ccp);
        gCCP.registerCarrierNumberEditText(gPhoneNumber);

        //progress dialog
        gDialog = new ProgressDialog(this);
        gDialog.setTitle("Please wait");
        gDialog.setMessage("While we are loading....");

        //googleSignIn
        gGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        gPhoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PHONE = gCCP.getFormattedFullNumber();
                Toast.makeText(loginPage.this, PHONE, Toast.LENGTH_SHORT).show();
                toOTP(PHONE);
            }
        });
    }

    private void toOTP(String phone) {
        Intent toOTP = new Intent(loginPage.this,otpPage.class);
        toOTP.putExtra("PHONE",phone);
        startActivity(toOTP);
        finish();
    }

    private void googleSignInInstance() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    private void googleSignIn() {

        Intent signInIntent = gGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){}
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        gDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebase.gAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            CID = task.getResult().getUser().getUid();
                            String email = task.getResult().getUser().getEmail();
                            String name = task.getResult().getUser().getDisplayName();
                            firebase.gUser = firebase.gAuth.getCurrentUser();

                            firebase.UID = CID;
                            promotUser(CID,email,name);

                        }
                        else
                        {
                            gDialog.dismiss();
                            Toast.makeText(loginPage.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void promotUser(final String cid, final String email, final String name) {


        gUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(cid))
                {
                    gDialog.dismiss();
                    toHome();
                }
                else
                {
                    createClientInstance(cid,email,name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void createClientInstance(String cid, String email, String name) {

        dataMap.put(COMM.U_EMAIL,email);
        dataMap.put(COMM.U_NAME,name);

        gUserRef.child(cid).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    gDialog.dismiss();
                    toEditProfile();
                }
                else
                {
                    gDialog.dismiss();
                    Toast.makeText(loginPage.this, "error at db.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void toEditProfile() {
        Intent toEditProfile = new Intent(loginPage.this,profileRegisterPage.class);
        startActivity(toEditProfile);
        finish();
    }

    private void toHome() {
        Intent toHome = new Intent(loginPage.this,homePage.class);
        startActivity(toHome);
        finish();
    }
}