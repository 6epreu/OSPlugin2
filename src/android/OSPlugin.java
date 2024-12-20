package com.example.osplugin;

import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class OSPlugin extends CordovaPlugin {

    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        try {
            MasterKey masterKey = new MasterKey.Builder(cordova.getContext())
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            prefs = EncryptedSharedPreferences.create(
                    cordova.getContext(),
                    "secret_shared_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            editor = prefs.edit();
        } catch (GeneralSecurityException e) {
            Log.e("E", "Qii plugin initialization failed on shared prefs");
        } catch (IOException e) {
            Log.e("E", "Qii plugin initialization failed on shared prefs");
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("showToast".equals(action)) {
            String message = args.getString(0);
            int duration = args.getInt(1);
            showToast(message, duration);
            callbackContext.success();
            return true;
        }
        else if (action.equals("set")) {
            String key = args.getString(0);
            String value = args.getString(1);
            this.set(key,value, callbackContext);
            return true;
        } else if (action.equals("get")) {
            String key = args.getString(0);
            this.get(key, callbackContext);
            return true;
        }
        callbackContext.error("ERROR");
        return false;
    }

    private void set(String key, String value, CallbackContext callbackContext) {
        if (key != null && key.length() > 0 ) {
            editor.putString(key, value);
            editor.commit();
            callbackContext.success();
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }


    private void get(String key, CallbackContext callbackContext) {
        if (key != null && key.length() > 0 ) {
            String value = prefs.getString(key, null);
            callbackContext.success(value);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void showToast(final String message, final int duration) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(cordova.getActivity(), message, duration).show();
            }
        });
    }
}
