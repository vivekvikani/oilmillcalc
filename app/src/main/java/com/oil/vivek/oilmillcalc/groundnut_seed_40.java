package com.oil.vivek.oilmillcalc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

//import com.oilCalc.vivek.oilmillcalc.R;

/**
 * Created by Vivek on 14/07/2015.
 */
public class groundnut_seed_40 extends Fragment implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener{
    View rootView;

    private EditText a2;
    private EditText b2;
    private EditText c2;
    private EditText d2;
    private EditText e2;
    private EditText f2;
    private EditText g2;
    private EditText ans2;

    double a2Double =0;
    double c2Double =0;
    double d2Double =0;
    double e2Double =0;
    double f2Double =0;
    double g2Double =0;
    double b2Double =0;

    String tempValue;
    List<EditText> editTextList = new ArrayList<EditText>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.groundnut_seed_40_layout,container,false);

        Button shr = (Button) rootView.findViewById(R.id.forwardShrBtn);
        shr.setOnClickListener(this);

        Button reset = (Button) rootView.findViewById(R.id.forwardRstBtn);
        reset.setOnClickListener(this);

        Button fullVersion = (Button) rootView.findViewById(R.id.gotoFullVersionF);
        fullVersion.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.forwardShrBtn:
                Button s = (Button) getView().findViewById(R.id.forwardShrBtn);
                s.setEnabled(false);

                shareScreenShot obj = new shareScreenShot();
                obj.shareScreenShotM(getView().findViewById(R.id.scrollView), (ScrollView) getView().findViewById(R.id.scrollView));
                startActivity(obj.shareIntent);

                a2.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(a2, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(a2, InputMethodManager.HIDE_NOT_ALWAYS);
                break;

            case R.id.forwardRstBtn:
                Button b = (Button) getView().findViewById(R.id.forwardRstBtn);
                b.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Reset all Values?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        a2.setText("");
                        b2.setText("");
                        c2.setText("");
                        d2.setText("");
                        e2.setText("");
                        f2.setText("");
                        g2.setText("");

                        a2.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(a2, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ButtonState();
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.gotoFullVersionF:
                Intent myIntent = new Intent(getActivity().getApplicationContext(), aboutUs.class);
                startActivity(myIntent);
                break;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        a2 = (EditText) getView().findViewById(R.id.a2);
        a2.setText(appdata.getString("gsr1SP", ""));
        a2.addTextChangedListener(this);
        a2.setOnFocusChangeListener(this);

        b2 = (EditText) getView().findViewById(R.id.b2);
        b2.setText(appdata.getString("gsr2SP", ""));
        b2.addTextChangedListener(this);
        b2.setOnFocusChangeListener(this);

        c2 = (EditText) getView().findViewById(R.id.c2);
        c2.setText(appdata.getString("gsr3SP", ""));
        c2.addTextChangedListener(this);
        c2.setOnFocusChangeListener(this);

        d2 = (EditText) getView().findViewById(R.id.d2);
        d2.setText(appdata.getString("gsr4SP", ""));
        d2.addTextChangedListener(this);
        d2.setOnFocusChangeListener(this);

        e2 = (EditText) getView().findViewById(R.id.e2);
        e2.setText(appdata.getString("gsr5SP", ""));
        e2.addTextChangedListener(this);
        e2.setOnFocusChangeListener(this);

        f2 = (EditText) getView().findViewById(R.id.f2);
        f2.setText(appdata.getString("gsr6SP", ""));
        f2.addTextChangedListener(this);
        f2.setOnFocusChangeListener(this);

        g2 = (EditText) getView().findViewById(R.id.g2);
        g2.setText(appdata.getString("gsr7SP", ""));
        g2.addTextChangedListener(this);
        g2.setOnFocusChangeListener(this);

        ans2 = (EditText) getView().findViewById(R.id.ans2);
        ans2.setText(appdata.getString("gsr8SP", ""));


        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.ForwardAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.ForwardBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.ForwardTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.ForwardTrialExpire2);
        boolean trialExpired = appdata.getBoolean("trialExpired",false);
        if(trialExpired)
        {
            padtarAnsRow.setVisibility(View.GONE);
            padtarBtnRow.setVisibility(View.GONE);
            padtarExpireAns.setVisibility(View.VISIBLE);
            padtarExpireBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            padtarExpireAns.setVisibility(View.GONE);
            padtarExpireBtn.setVisibility(View.GONE);
            padtarAnsRow.setVisibility(View.VISIBLE);
            padtarBtnRow.setVisibility(View.VISIBLE);
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(a2, InputMethodManager.SHOW_IMPLICIT);
        ButtonState();

        editTextList.add(a2);
        editTextList.add(b2);
        editTextList.add(c2);
        editTextList.add(e2);
        editTextList.add(d2);
        editTextList.add(f2);
        editTextList.add(g2);
    }

    @Override
    public void onResume() {
        super.onResume();
        ButtonState();

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        TableRow padtarAnsRow = (TableRow) getView().findViewById(R.id.ForwardAnsRow);
        TableRow padtarBtnRow = (TableRow) getView().findViewById(R.id.ForwardBtnRow);
        TableRow padtarExpireAns = (TableRow) getView().findViewById(R.id.ForwardTrialExpire1);
        TableRow padtarExpireBtn = (TableRow) getView().findViewById(R.id.ForwardTrialExpire2);
        boolean trialExpired = appdata.getBoolean("trialExpired",false);
        if(trialExpired)
        {
            padtarAnsRow.setVisibility(View.GONE);
            padtarBtnRow.setVisibility(View.GONE);
            padtarExpireAns.setVisibility(View.VISIBLE);
            padtarExpireBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            padtarExpireAns.setVisibility(View.GONE);
            padtarExpireBtn.setVisibility(View.GONE);
            padtarAnsRow.setVisibility(View.VISIBLE);
            padtarBtnRow.setVisibility(View.VISIBLE);
        }

        a2.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(a2, InputMethodManager.SHOW_IMPLICIT);

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus)
        {
            switch (view.getId())
            {
                case R.id.a2:
                    a2.setSelection(a2.getText().length());
                    break;
                case R.id.b2:
                    b2.setSelection(b2.getText().length());
                    break;
                case R.id.c2:
                    c2.setSelection(c2.getText().length());
                    break;
                case R.id.d2:
                    d2.setSelection(d2.getText().length());
                    break;
                case R.id.e2:
                    e2.setSelection(e2.getText().length());
                    break;
                case R.id.f2:
                    f2.setSelection(f2.getText().length());
                    break;
                case R.id.g2:
                    g2.setSelection(g2.getText().length());
                    break;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        for(EditText local : editTextList)
        {
            if(local.hasFocus())
            {
                switch (local.getId())
                {
                    case R.id.a2:
                        tempValue = a2.getText().toString().trim();
                        break;
                    case R.id.b2:
                        tempValue = b2.getText().toString().trim();
                        break;
                    case R.id.c2:
                        tempValue = c2.getText().toString().trim();
                        break;
                    case R.id.d2:
                        tempValue = d2.getText().toString().trim();
                        break;
                    case R.id.e2:
                        tempValue = e2.getText().toString().trim();
                        break;
                    case R.id.f2:
                        tempValue = f2.getText().toString().trim();
                        break;
                    case R.id.g2:
                        tempValue = g2.getText().toString().trim();
                        break;
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

        String Txt;
        String Pattern;

        for(EditText local : editTextList)
        {
            if(local.hasFocus())
            {
                switch (local.getId())
                {
                    case R.id.a2:
                        Txt = a2.getText().toString().trim();
                        Pattern = "\\d{0,5}(\\.\\d{0,2})?";
                        if (!Txt.matches(Pattern)) {
                            a2.setText(tempValue);
                            a2.setSelection(a2.getText().length());
                        }
                        break;
                    case R.id.b2:
                        Txt = b2.getText().toString().trim();
                        Pattern = "\\d{0,3}(\\.\\d{0,2})?";
                        if (!Txt.matches(Pattern)) {
                            b2.setText(tempValue);
                            b2.setSelection(b2.getText().length());
                        }
                        break;
                    case R.id.c2:
                        Txt = c2.getText().toString().trim();
                        Pattern = "\\d{0,3}(\\.\\d{0,2})?";
                        if (!Txt.matches(Pattern)) {
                            c2.setText(tempValue);
                            c2.setSelection(c2.getText().length());
                        }
                        break;
                    case R.id.d2:
                        Txt = d2.getText().toString().trim();
                        Pattern = "\\d{0,4}(\\.\\d{0,2})?";
                        if (!Txt.matches(Pattern)) {
                            d2.setText(tempValue);
                            d2.setSelection(d2.getText().length());
                        }
                        break;
                    case R.id.e2:
                        Txt = e2.getText().toString().trim();
                        Pattern = "\\d{0,2}(\\.\\d{0,2})?";
                        if (!Txt.matches(Pattern)) {
                            e2.setText(tempValue);
                            e2.setSelection(e2.getText().length());
                        }
                        break;
                    case R.id.f2:
                        Txt = f2.getText().toString().trim();
                        Pattern = "\\d{0,2}(\\.\\d{0,2})?";
                        if (!Txt.matches(Pattern)) {
                            f2.setText(tempValue);
                            f2.setSelection(f2.getText().length());
                        }
                        break;
                    case R.id.g2:
                        Txt = g2.getText().toString().trim();
                        Pattern = "\\d{0,2}(\\.\\d{0,2})?";
                        if (!Txt.matches(Pattern)) {
                            g2.setText(tempValue);
                            g2.setSelection(g2.getText().length());
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        for(EditText local : editTextList)
        {
            double localDouble = 0;
            String localString = local.getText().toString();
            Boolean localBool = localString.isEmpty();
            if(localString.startsWith("."))
            {
                local.getText().insert(0,"0");
            }
            else if(!localBool)
            {
                localDouble = Double.parseDouble(localString);
            }

            switch (local.getId())
            {
                case R.id.a2:
                    a2Double = localDouble;
                    break;
                case R.id.b2:
                    b2Double = localDouble;
                    break;
                case R.id.c2:
                    c2Double = localDouble;
                    break;
                case R.id.d2:
                    d2Double = localDouble;
                    break;
                case R.id.e2:
                    e2Double = localDouble;
                    break;
                case R.id.f2:
                    f2Double = localDouble;
                    break;
                case R.id.g2:
                    g2Double = localDouble;
                    break;
            }
            double calcA = ((a2Double+b2Double)-(e2Double*(c2Double/20))-(f2Double*(d2Double/20)))/(400-e2Double-f2Double-g2Double);

            if(a2Double==0 || c2Double==0 || d2Double==0 || e2Double==0 || f2Double==0)
            {
                calcA = 0;
            }

            ans2 = (EditText) getView().findViewById(R.id.ans2);

            if(calcA>0){
                String padtarFinal = String.format("%.2f", calcA);
                ans2.setText(padtarFinal);
            }else
            {
                ans2.setText("Invalid!!");
            }

            ButtonState();
        }
    }


    public void ButtonState()
    {
        Button b = (Button) getView().findViewById(R.id.forwardRstBtn);
        Button s = (Button) getView().findViewById(R.id.forwardShrBtn);
        if(a2.getText().toString().isEmpty() &&
                b2.getText().toString().isEmpty() &&
                c2.getText().toString().isEmpty() &&
                d2.getText().toString().isEmpty() &&
                e2.getText().toString().isEmpty() &&
                f2.getText().toString().isEmpty() &&
                g2.getText().toString().isEmpty())
        {
            s.setEnabled(false);
            b.setEnabled(false);
        }
        else
        {
            b.setEnabled(true);
            s.setEnabled(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        SharedPreferences.Editor editor = appdata.edit();
        editor.putString("gsr1SP", a2.getText().toString());
        editor.putString("gsr2SP", b2.getText().toString());
        editor.putString("gsr3SP", c2.getText().toString());
        editor.putString("gsr4SP", d2.getText().toString());
        editor.putString("gsr5SP", e2.getText().toString());
        editor.putString("gsr6SP", f2.getText().toString());
        editor.putString("gsr7SP", g2.getText().toString());
        editor.putString("gsr8SP", ans2.getText().toString());
        editor.commit();
    }
}
