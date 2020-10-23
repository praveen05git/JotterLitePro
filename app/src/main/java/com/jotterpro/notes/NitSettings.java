package com.jotterpro.notes;

import android.content.Context;
import android.content.SharedPreferences;

public class NitSettings {

    SharedPreferences NitPref;

    public NitSettings(Context context) {
        NitPref = context.getSharedPreferences("NightPreference", Context.MODE_PRIVATE);
    }

    public void setNitState(Boolean state) {
        SharedPreferences.Editor editor = NitPref.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }

    public Boolean loadNitState() {
        Boolean state = NitPref.getBoolean("NightMode", false);
        return state;
    }
}
