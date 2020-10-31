package com.jotterpro.notes;

import android.content.Context;
import android.content.SharedPreferences;

public class NitSettings {

    SharedPreferences nitPref;

    public NitSettings(Context context) {
        nitPref = context.getSharedPreferences("NightPreference", Context.MODE_PRIVATE);
    }

    public void setNitState(Boolean state) {
        SharedPreferences.Editor editor = nitPref.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }

    public Boolean loadNitState() {
        Boolean state = nitPref.getBoolean("NightMode", false);
        return state;
    }
}
