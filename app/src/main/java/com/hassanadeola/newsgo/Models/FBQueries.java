package com.hassanadeola.newsgo.Models;


import static com.hassanadeola.newsgo.utils.Functions.createAlertDialog;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FBQueries {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    UserFBInfo userFBInfo;
    Context context;

    String token;


    public FBQueries(Context context, String token) {
        this.context = context;
        this.token = token;
        userFBInfo = new UserFBInfo();

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference(token + "-user");


    }

    public void addLoginInfo(String username, String email, boolean isJoined) {
        userFBInfo.setUsername(username);
        userFBInfo.setEmail(email);
        userFBInfo.setToken(token);
        userFBInfo.setOs("android");
        userFBInfo.setPushNotify(false);
        userFBInfo.setEmailNotify(false);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(userFBInfo);
                // after adding this data we are showing toast message.
                // Toast.makeText(context, "data added", Toast.LENGTH_SHORT).show();
                SQLQueries newQueries = new SQLQueries(context);
                newQueries.addUser(username, email, token);

                if (isJoined) {
                    Toast.makeText(context, "Sign up Successful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                //  Toast.makeText(context, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = createAlertDialog(context, "Signup Error",
                        "An Error Occurred but you can continue to use this App. You can always sign up later. We got you!");
                builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            }

        });

    }

}
