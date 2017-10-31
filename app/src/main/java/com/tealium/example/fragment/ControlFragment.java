package com.tealium.example.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tealium.example.R;
import com.tealium.example.helper.TealiumHelper;
import com.tealium.library.Tealium;

import java.util.Locale;


public class ControlFragment extends Fragment implements
        View.OnClickListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener {

    private final RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout relativeLayout;

    public ControlFragment() {
        super();

        layoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        relativeLayout = null;
    }

    public boolean setControl(int layoutId) {

        if (getActivity() == null) {
            return false;
        }

        //Log.d("Layout ID: ", Integer.toString(layoutId));


        View control = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        control.setLayoutParams(layoutParams);

        if (control instanceof AdapterView) {
            try {
                ((AdapterView<?>) control).setOnItemClickListener(this);
            } catch (RuntimeException e) {
                // Item click not supported.
                ((AdapterView<?>) control).setOnItemSelectedListener(this);
            }
        } else if (control instanceof RadioGroup) {
            ((RadioGroup) control).setOnCheckedChangeListener(this);
        } else if (control instanceof CompoundButton) {
            ((CompoundButton) control).setOnCheckedChangeListener(this);
        //}else if (control instanceof RelativeLayout){
        //    ((RelativeLayout) control).getChildAt(9).setOnClickListener(this);
        } else {
            control.setOnClickListener(this);
        }

        relativeLayout.removeAllViews();
        relativeLayout.addView(control);

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_custom, null);

        return relativeLayout;
    }

    // Interface Implementations

    @Override
    public void onClick(View v) {
        /*
        if(v.getId() != -1){
            TealiumHelper.trackEvent(v.getClass().getSimpleName() + ":Click", null);
            submitAccPro(v);
        } else {
        */
            Log.d("ON CLICK: ", Integer.toString(v.getId()));
            TealiumHelper.trackEvent(v.getClass().getSimpleName() + ":Click", null);
            Toast.makeText(
                    getActivity(),
                    String.format(Locale.US, "%s: Click!", v.getClass().getSimpleName()),
                    Toast.LENGTH_SHORT).show();
        //}

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        TealiumHelper.trackEvent(compoundButton.getClass().getSimpleName() + ":" + (isChecked ? "Checked" : "Unchecked"), null);
        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%s: %s!",
                        compoundButton.getClass().getSimpleName(),
                        (isChecked ? "Checked" : "Unchecked")),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%d was checked!", checkedId),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View selection, int position, long id) {
        Object item = adapterView.getAdapter().getItem(position);

        TealiumHelper.trackEvent(item.toString() + ":Click", null);

        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%s was clicked!", item.toString()),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View selectionView, int position, long id) {
        Object item = adapterView.getAdapter().getItem(position);

        TealiumHelper.trackEvent(item.toString() + ":Selected", null);

        Toast.makeText(
                getActivity(),
                String.format(Locale.US, "%s was selected!", item.toString()),
                Toast.LENGTH_SHORT).show();

    }

    /*
    public void submitAccPro(View v) {
        Tealium current = Tealium.getInstance(TealiumHelper.TEALIUM_MAIN);

        EditText account = (EditText)relativeLayout.findViewById(R.id.account);
        EditText profile = (EditText)relativeLayout.findViewById(R.id.profile);
        EditText env = (EditText)relativeLayout.findViewById(R.id.environment);
        EditText traceId = (EditText)relativeLayout.findViewById(R.id.traceId);

        //use to set global vars so that when user clicks on Env Switch screen text values can be set to current values.
        current.getAccountName();
        current.getProfileName();
        current.getEnvironmentName();

        account.setText(Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getAccountName());
        profile.setText(Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).getProfileName());


        Log.d("Account: ", account.getText().toString());
        Log.d("Profile: ", profile.getText().toString());
        Log.d("Env: ", env.getText().toString());

        if(account.getText().toString().trim().equalsIgnoreCase("account") || profile.getText().toString().trim().equalsIgnoreCase("profile")){
            Toast.makeText(
                    getActivity(),
                    String.format(Locale.US, "Please enter a valid account/profile", ""),
                    Toast.LENGTH_SHORT).show();
        }else {

            Tealium.destroyInstance(TealiumHelper.TEALIUM_MAIN);

            //final Tealium.Config config = Tealium.Config.create(getApplication(), account.toString(), profile.toString() , env.toString());
            TealiumHelper.initialize(getActivity().getApplication(), account.getText().toString(), profile.getText().toString(), env.getText().toString());

            if (traceId.getText().toString().length() > 0) {
                Log.d("Trace ID: ", traceId.getText().toString());
                //Tealium.getInstance(TealiumHelper.TEALIUM_MAIN).joinTrace(traceId.getText().toString());
                current.getDataSources().getVolatileDataSources().put("cp.trace_id", traceId.getText().toString());

            }
        }
    }
    */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Nothing to do here...
    }
}
