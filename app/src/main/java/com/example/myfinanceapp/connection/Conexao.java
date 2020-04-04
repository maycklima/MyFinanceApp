package com.example.myfinanceapp.connection;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Conexao
{
    private static FirebaseAuth auth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;

    public Conexao()
    {

    }

    public static FirebaseAuth getReferenceAuth(){
        if(auth == null)
        {
            inicializarFireBaseAuth();
        }
        return auth;
    }

    private static void inicializarFireBaseAuth()
    {
        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                  firebaseUser = user;
                }
            }
        };
        auth.addAuthStateListener(authStateListener);
    }

    public static FirebaseUser gerFireBaseUser(){
        return firebaseUser;
    }

    public static void logout(){
        auth.signOut();
    }
}
