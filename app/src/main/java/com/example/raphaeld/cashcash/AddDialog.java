package com.example.raphaeld.cashcash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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
import java.util.Calendar;
import static com.example.raphaeld.cashcash.FragFromMe.adapter;
import static com.example.raphaeld.cashcash.FragFromMe.elemFrom;
import static com.example.raphaeld.cashcash.FragFromMe.listfrom;
import static com.example.raphaeld.cashcash.FragFromMe.notifyAdapterFrom;
import static com.example.raphaeld.cashcash.FragToMe.notifyAdapterTo;


public class AddDialog extends Dialog {

    Activity a;
    Dialog d;
    Button add , cancel;
    RadioButton owes,lent  ;
    EditText name ,amount,note;
    Spinner spin;
    TextView date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    LinearLayout layradbut;
    private String TAG = "MainActivity";
    private  DataBaseManager dataBaseManager;

    private AddDialog(Activity c) {
        super(c);
        this.a = c;
        dataBaseManager=DataBaseManager.getInstance(this.getContext().getApplicationContext());
    }




    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogadd);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        add = (Button) findViewById(R.id.buttonAdd);
        cancel = (Button) findViewById(R.id.buttonCancel);
        owes = (RadioButton) findViewById(R.id.radioButtonOwes);
        lent = (RadioButton) findViewById(R.id.radioButtonLent);
        name = (EditText) findViewById(R.id.editName);
        date = (TextView) findViewById(R.id.textDate);
        amount = (EditText) findViewById(R.id.editTextAmount);
        note = (EditText) findViewById(R.id.editTextNote);
        spin = (Spinner) findViewById(R.id.spinnerdevise);
        layradbut = (LinearLayout) findViewById(R.id.layoutradiobutton);

        name.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
        amount.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
        note.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });
        setCurrentDate();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() ||
                        owes.isChecked() == lent.isChecked() ||
                          amount.getText().toString().isEmpty() ) {
                    if (name.getText().toString().isEmpty()) {
                        name.setHintTextColor(Color.RED);
                    }
                    if (owes.isChecked() == lent.isChecked()) {
                        owes.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorRed));
                        lent.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorRed));
                    }if(amount.getText().toString().isEmpty()){
                        amount.setHintTextColor(Color.RED);
                    }


                    Toast.makeText(getContext(), "please fill all fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    String valName=name.getText().toString();
                    String valDate =date.getText().toString();
                    String valNote=note.getText().toString();
                    String valDev=spin.getSelectedItem().toString();
                    String valAmount=amount.getText().toString();
                    if(owes.isChecked()) {

                        dataBaseManager.addNoteToGetBack(
                                                valName,
                                                 valDate,
                                                valAmount,
                                                valDev,
                                                valNote);
                        FragToMe.elemTo.add(
                                new ElementList(
                                        getContext(),
                                        valName,
                                        valDate,
                                        valAmount,
                                        valDev,
                                        valNote));

                    }else if(lent.isChecked()) {

                        dataBaseManager.addNoteToGiveBack(
                                valName,
                                valDate,
                                valAmount,
                                valDev,
                                valNote);
                           FragFromMe.elemFrom.add(
                                    new ElementList(
                                            getContext(),
                                            valName,
                                            valDate,
                                            valAmount,
                                            valDev,
                                            valNote));

                    }
                    notifyAdapterFrom();
                    notifyAdapterTo();
                    dismiss();
                    dataBaseManager.close();
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

                                    }
                                });
        mDateSetListener =new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year ,int month,int day){
                Log.d(TAG,"onDateSet: date " +day+"/"+month+"/"+year+"/");
                String newDate= day+"/"+month+"/"+year;
                date.setText(newDate);
           }
        };


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        owes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lent.setChecked(false);
                lent.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                owes.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
            }
        });
        lent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lent.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                owes.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                owes.setChecked(false);
            }
        });



    }


    private void setCurrentDate(){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("d/M/yyyy");
        String formattedDate1 = df1.format(c.getTime());
        date.setText(formattedDate1);
    }

    public Activity getA() {
        return a;
    }

    public void setA(Activity a) {
        this.a = a;
    }

    public Dialog getD() {
        return d;
    }

    public void setD(Dialog d) {
        this.d = d;
    }

    public Button getAdd() {
        return add;
    }

    public void setAdd(Button add) {
        this.add = add;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public RadioButton getOwes() {
        return owes;
    }

    public void setOwes(RadioButton owes) {
        this.owes = owes;
    }

    public RadioButton getLent() {
        return lent;
    }

    public void setLent(RadioButton lent) {
        this.lent = lent;
    }






    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    public EditText getAmount() {
        return amount;
    }

    public void setAmount(EditText amount) {
        this.amount = amount;
    }



    public EditText getNote() {
        return note;
    }

    public void setNote(EditText note) {
        this.note = note;
    }

    public Spinner getSpin() {
        return spin;
    }

    public void setSpin(Spinner spin) {
        this.spin = spin;
    }}
