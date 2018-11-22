package com.example.raphaeld.cashcash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.raphaeld.cashcash.ElementList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.raphaeld.cashcash.FragFromMe.elemFrom;
import static com.example.raphaeld.cashcash.FragFromMe.notifyAdapterFrom;
import static com.example.raphaeld.cashcash.FragToMe.elemTo;
import static com.example.raphaeld.cashcash.FragToMe.listTo;
import static com.example.raphaeld.cashcash.FragToMe.notifyAdapterTo;

public class AddEditDialog extends Dialog {




    Button save , cancel;
    EditText name ,amount,note;
    Spinner spin;
    TextView date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    LinearLayout layradbut;
    private String TAG = "MainActivity";
    ElementList el;
    DataBaseManager dataBaseManager;

    public AddEditDialog(@NonNull Context context,
                           ElementList elementList) {
        super(context);
        el = elementList;
        dataBaseManager=DataBaseManager.getInstance(this.getContext().getApplicationContext());


    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    public AddEditDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.dialogaddedit);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        save = (Button) findViewById(R.id.buttonSaveEdit);
        cancel = (Button) findViewById(R.id.buttonCancelEdit);
        name = (EditText) findViewById(R.id.editNameEdit);
        date = (TextView) findViewById(R.id.textDateEdit);
        amount = (EditText) findViewById(R.id.editTextAmountEdit);
        note = (EditText) findViewById(R.id.editTextNoteEdit);
        spin = (Spinner) findViewById(R.id.spinnerdeviseEdit);
        layradbut = (LinearLayout) findViewById(R.id.layoutradiobutton);

        name.setText(el.getNom());
        note.setText(el.getNote());
        amount.setText(el.getPrix());
        date.setText(el.getMydate());
        spin.setSelection(getIndex(spin, el.getDevise()));

        name.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
        amount.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
        note.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() ||
                          amount.getText().toString().isEmpty() ) {
                    if(name.getText().toString().isEmpty()) {
                        name.setHintTextColor(Color.RED);
                    }
                    if(amount.getText().toString().isEmpty()){
                        amount.setHintTextColor(Color.RED);
                    }


                    Toast.makeText(getContext(), "please fill all fields",
                            Toast.LENGTH_LONG).show();
                } else {

                    ElementList old = new ElementList(getContext(),el.getNom(),el.getMydate(),el.getPrix(),el.getDevise(),el.getNote());
                    el.setNom(name.getText().toString());
                    el.setMydate(date.getText().toString());
                    el.setPrix(amount.getText().toString());
                    el.setDevise(spin.getSelectedItem().toString());
                    el.setNote(note.getText().toString());

                    if(elemFrom.contains(el)){
                        dataBaseManager.update(old,el,"NotesToGiveBack");
                    } if (elemTo.contains(el)) {
                        dataBaseManager.update(old,el,"NotesToGetBack");
                    }
                    notifyAdapterFrom();
                    notifyAdapterTo();
                    dismiss();

                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogp = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialogp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT) );
                dialogp.show();

            };
        });
        mDateSetListener =new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year , int month, int day){
                Log.d(TAG,"onDateSet: date " +day+"/"+month+"/"+year+"/");
                String newDate= day+"/"+month+"/"+year;
                date.setText(newDate);
            }
        };

        amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                amount.setHintTextColor(Color.BLACK);

                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });





    }
}
