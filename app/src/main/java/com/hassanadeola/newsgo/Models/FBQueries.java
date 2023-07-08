package com.hassanadeola.newsgo.Models;


import static com.hassanadeola.newsgo.utils.Functions.createAlertDialog;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FBQueries {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context context;

    String uuid;


    public FBQueries(Context context, String uuid) {
        this.context = context;

        if (uuid != null) {
            this.uuid = uuid;
        } else {
            SQLQueries sqlQueries = new SQLQueries(context);
            String sqlId = sqlQueries.getUUID();
            this.uuid = sqlId;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(this.uuid);


    }

    public void addLoginInfo(String username, String email, String token) {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("token", token);
        updates.put("os", "android");
        updates.put("username", username);
        updates.put("email", email);

        databaseReference.setValue(updates).addOnSuccessListener((listener) -> {

            SQLQueries newQueries = new SQLQueries(context);
            newQueries.addUser(username, email, token);
            Toast.makeText(context, "Sign up Successful!", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener((listener) -> {
            AlertDialog.Builder builder = createAlertDialog(context, "Signup Error",
                    "An Error Occurred but you can continue to use this App. You can always sign up later. We got you!");
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        });
    }

    public void updateSettings(boolean isActive, String settings) {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put(settings, isActive);
        databaseReference.updateChildren(updates).addOnSuccessListener((listener) -> {
            Toast.makeText(context, "Settings updated", Toast.LENGTH_SHORT).show();
        });
    }
}
