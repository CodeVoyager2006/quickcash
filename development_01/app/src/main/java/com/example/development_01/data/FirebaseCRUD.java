package com.example.development_01.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseCRUD {
    private FirebaseDatabase database = null;

    private DatabaseReference nameRef = null;
    private DatabaseReference emailRef = null;
    private DatabaseReference passwordRef = null;
    private DatabaseReference roleRef = null;

    private String extractedName;
    private String extractedEmailAddress;
    private String extractedPassword;
    private String extractedRole;


    public FirebaseCRUD(FirebaseDatabase database) {
        this.database = database;
        //missing initialization, add the required initialization

        initializeDatabaseRefs();
        initializeDatabaseRefListeners();
    }

    protected DatabaseReference getNameRef() {
        return this.database.getReference("name");
    }

    public void setName(String name) { this.nameRef.setValue(name); }
    protected void setNameListener() {
        this.nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                extractedName = snapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected DatabaseReference getEmailRef() { return this.database.getReference("email"); }

    public void setEmail(String email) { this.emailRef.setValue(email); }
    protected void setEmailListener() {
        this.emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                extractedEmailAddress = snapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    protected DatabaseReference getPasswordRef() { return this.database.getReference("password"); }

    public void setPassword(String password) { this.passwordRef.setValue(password); }
    protected void setPasswordListener() {
        this.passwordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                extractedPassword = snapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    protected DatabaseReference getRoleRef() {
        return this.database.getReference("role");
    }
    public void setRole(String role) { this.roleRef.setValue(role); }
    protected void setRoleListener() {
        this.roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                extractedRole = snapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }


    protected void initializeDatabaseRefs() {
        this.nameRef = getNameRef();
        this.emailRef = getEmailRef();
        this.passwordRef = getPasswordRef();
        this.roleRef = getRoleRef();
    }

    protected void initializeDatabaseRefListeners() {
        this.setNameListener();
        this.setEmailListener();
        this.setPasswordListener();
        this.setRoleListener();

    }

    public String getExtractedName() {
        return this.extractedName;
    }
    public String getExtractedEmailAddress() {
        return this.extractedEmailAddress;
    }
    public String getExtractedPassword() {
        return this.extractedPassword;
    }
    public String getExtractedRole() {
        return this.extractedRole;
    }
}
