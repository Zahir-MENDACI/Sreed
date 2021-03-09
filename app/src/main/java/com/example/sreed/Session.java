package com.example.sreed;

import android.content.Context;
import android.content.SharedPreferences;


public class Session {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "app_prefs";
    private final static int PRIVATE_MODE = 0;
    private final static String IS_LOGGED = "isLogged";
    private final static String EMAIL = "Email";
    private final static String Pseudo = "Pseudo";
    private Context context;

    public Session(Context context)
    {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public boolean isLogged()
    {
        return prefs.getBoolean(IS_LOGGED, false);
    }

    public String getEmail()
    {
        return prefs.getString(EMAIL, null);
    }

    public String getPseudo()
    {
        return prefs.getString(Pseudo, null);
    }

    public void insertUser(String Email)
    {
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(EMAIL, Email);
        editor.commit();
    }

    public void inserPseudo(String pseudo)
    {
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(Pseudo, pseudo);
        editor.commit();
    }

    public void Logout()
    {
        editor.clear().commit();
    }
}
