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


        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference(token + "-user");


    }

    public void addLoginInfo(String username, String email, boolean isJoined) {
        userFBInfo = new UserFBInfo();
        userFBInfo.setUsername(username);
        userFBInfo.setEmail(email);
        userFBInfo.setToken(token);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(userFBInfo);

                if (isJoined) {
                    SQLQueries newQueries = new SQLQueries(context);
                    newQueries.addUser(username, email, token);
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

    public void updateSettings(boolean isActive, String settings) {
        userFBInfo = new UserFBInfo();
        if (settings.equalsIgnoreCase("emailNotify")) {
            userFBInfo.setEmailNotify(isActive);
        } else {
            userFBInfo.setPushNotify(isActive);
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(userFBInfo);
                Toast.makeText(context, "Settings updated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                AlertDialog.Builder builder = createAlertDialog(context, "Update Error",
                        "There was an error updating your settings. Please try again later");
                builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            }

        });

    }

}
