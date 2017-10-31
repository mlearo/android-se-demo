package com.tealium.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import com.tealium.example.helper.TealiumHelper;
import com.tealium.library.Tealium;

import java.util.Locale;

/**
 * Created by michaellearo on 10/23/17.
 */

public class EnvSwitcher extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_env_switcher);

        Tealium current = Tealium.getInstance(TealiumHelper.TEALIUM_MAIN);

        EditText account = (EditText)findViewById(R.id.account);
        EditText profile = (EditText)findViewById(R.id.profile);
        EditText env = (EditText)findViewById(R.id.environment);
        //EditText traceId = (EditText)findViewById(R.id.traceId);

        //use to set global vars so that when user clicks on Env Switch screen text values can be set to current values.
        current.getAccountName();
        current.getProfileName();
        current.getEnvironmentName();

        account.setText(Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getAccountName());
        profile.setText(Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getProfileName());
        env.setText(Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getEnvironmentName());

    }

    public void submitAccPro(View v) {
        Tealium current = Tealium.getInstance(TealiumHelper.TEALIUM_MAIN);

        EditText account = (EditText)findViewById(R.id.account);
        EditText profile = (EditText)findViewById(R.id.profile);
        EditText env = (EditText)findViewById(R.id.environment);
        EditText traceId = (EditText)findViewById(R.id.traceId);

        //use to set global vars so that when user clicks on Env Switch screen text values can be set to current values.
        //current.getAccountName();
        //current.getProfileName();
        //current.getEnvironmentName();

        //account.setText(Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getAccountName());
        //profile.setText(Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getProfileName());


        Log.d("Account: ", account.getText().toString());
        Log.d("Profile: ", profile.getText().toString());
        Log.d("Env: ", env.getText().toString());

        //if(account.getText().toString().trim().equalsIgnoreCase("account") || profile.getText().toString().trim().equalsIgnoreCase("profile")){
        //    Toast.makeText(
        //            getApplication(),
        //            String.format(Locale.US, "Please enter a valid account/profile", ""),
        //            Toast.LENGTH_SHORT).show();
        //}else {

        Tealium.destroyInstance(TealiumHelper.TEALIUM_MAIN);

            //final Tealium.Config config = Tealium.Config.create(getApplication(), account.toString(), profile.toString() , env.toString());
        TealiumHelper.initialize(getApplication(), account.getText().toString(), profile.getText().toString(), env.getText().toString());

            if (traceId.getText().toString().length() > 0) {
                Log.d("Trace ID: ", traceId.getText().toString());
                //Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).joinTrace(traceId.getText().toString());
                current.getDataSources().getVolatileDataSources().put("cp.trace_id", traceId.getText().toString());

            }
        //}
    }

}
