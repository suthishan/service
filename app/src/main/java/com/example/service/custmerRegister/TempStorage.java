package com.example.service.custmerRegister;

import android.content.Context;
import android.content.SharedPreferences;

public class TempStorage {
    public static final String MyPREFERENCES = "nearbycollegeevent" ;
    SharedPreferences sharedpreferences;
    String strUser=null;

    String strPass=null;

    String strPhone=null;

    String strMail=null;

   public TempStorage(Context context)
    {
      //  SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode

        sharedpreferences =context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

  public void SetUserName(String user)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("UserName", user);
        editor.commit();
    }

    public void SetPassword(String pass)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("Password", pass);
        editor.commit();
    }

    public void SetPhone(String phone)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("Phone", phone);
        editor.commit();
    }

    public void SetMail(String mail)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("Mail", mail);
        editor.commit();
    }



    public String getUserName()
    {
        strUser=sharedpreferences.getString("UserName",null);

        return strUser;
    }

    public String getPassword()
    {
        strPass=sharedpreferences.getString("Password",null);

        return strPass;
    }

    public String getPhone()
    {
        strPhone=sharedpreferences.getString("Phone",null);

        return strPhone;
    }

    public String getMail()
    {
        strMail=sharedpreferences.getString("Mail",null);

        return strMail;
    }




    public void setUserClear()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("UserName");
       // editor.remove("Password");
        editor.remove("Phone");
       // editor.remove("Mail");

        editor.commit();
    }

    public void setAllClear()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
       // editor.remove("UserName");
        editor.clear();
        editor.commit();
    }

}
