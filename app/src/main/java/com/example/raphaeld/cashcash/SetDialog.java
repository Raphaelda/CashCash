package com.example.raphaeld.cashcash;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;


/**
 * Created by Raphaeld on 04/09/2017.
 */

public class SetDialog extends Dialog  implements android.view.View.OnClickListener {

    Activity a;
    Spinner spinTheme, spinView;
    Button butCancel, butApply;

    public SetDialog(Activity c) {
        super(c);
        this.a = c;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.settings);
        spinTheme =(Spinner) findViewById(R.id.spinnerTheme);
        spinView =(Spinner)findViewById(R.id.spinnerView);
        butApply =(Button)findViewById(R.id.buttonApplySet);
        butCancel = (Button)findViewById(R.id.buttonCancelSet);*/

    }

    @Override
    public void onClick(View v){}
}

