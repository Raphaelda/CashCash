package com.example.raphaeld.cashcash;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.raphaeld.cashcash.FragFromMe.adapter;
import static com.example.raphaeld.cashcash.FragFromMe.elemFrom;
import static com.example.raphaeld.cashcash.FragFromMe.listfrom;
import static com.example.raphaeld.cashcash.FragFromMe.notifyAdapterFrom;
import static com.example.raphaeld.cashcash.FragToMe.elemTo;
import static com.example.raphaeld.cashcash.FragToMe.listTo;
import static com.example.raphaeld.cashcash.FragToMe.notifyAdapterTo;

/**
 * Created by Raphaeld on 27/06/2018.
 */

public class EditDialog extends Dialog {

    RadioButton totalRef;
    RadioButton partialRef;
    Button confirm;
    Button cancel;
    TextView amount;
    TextView device;
    int position;
    String frag;
    public  DataBaseManager dataBaseManager;


    public EditDialog(@NonNull Context context) {
        super(context);
        dataBaseManager=DataBaseManager.getInstance(this.getContext().getApplicationContext());


    }


    public void show(int pos,String dev, String f) {
        super.show();
        device.setText(dev);
        position=pos;
        frag=f;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogedit);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        totalRef=(RadioButton)findViewById(R.id.fullyRef);
        partialRef=(RadioButton)findViewById(R.id.partRef);
        amount=(EditText)findViewById(R.id.amountEdit);
        device=(TextView)findViewById(R.id.deviceTex);
        confirm=(Button)findViewById(R.id.buttonAdd);
        cancel=(Button)findViewById(R.id.buttonCancel);
        partialRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
        totalRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
        amount.setFilters(new InputFilter[] { new InputFilter.LengthFilter(5) });



        totalRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partialRef.setChecked(false);
                amount.setEnabled(false);
                amount.setText("");
                partialRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                totalRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));

            }
        });

        partialRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalRef.setChecked(false);
                amount.setEnabled(true);
                partialRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                totalRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
                // set focus on the edittext to write the amount and open the keyboard
                amount.requestFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(amount, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                partialRef.performClick();
                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!totalRef.isChecked() && !partialRef.isChecked()) ||
                        (partialRef.isChecked() && amount.getText().toString().isEmpty())) {
                    if (totalRef.isChecked() == partialRef.isChecked()) {
                        partialRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorRed));
                        totalRef.setButtonTintList(getContext().getResources().getColorStateList(R.color.colorRed));
                        Toast.makeText(getContext(), "please fill all fields",
                                Toast.LENGTH_LONG).show();
                    }
                    if (partialRef.isChecked() || amount.getText().toString().isEmpty()) {
                        amount.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        Toast.makeText(getContext(), "please fill all fields",
                                Toast.LENGTH_LONG).show();
                    }
                }
                // if everything is ok
                else {
                    if (frag.equals("from")) {
                        ElementList el = (ElementList) listfrom.getItemAtPosition(position);
                        ElementList elOld = new ElementList(getContext(),el.getNom(),el.getMydate(),el.getPrix(),el.getDevise(),el.getNote());
                        if (totalRef.isChecked()) {
                                elemFrom.remove(el);
                            dataBaseManager.deleteNoteFrom(el);
                            notifyAdapterFrom();
                                dismiss();
                        } else {
                            if (partialRef.isChecked() &&
                                    Integer.parseInt(amount.getText().toString()) == Integer.valueOf(el.getPrix())) {
                                    elemFrom.remove(el);
                                notifyAdapterFrom();
                                dataBaseManager.deleteNoteFrom(el);
                                    dismiss();
                            } else if (partialRef.isChecked() &&
                                    Integer.parseInt(amount.getText().toString()) < Integer.valueOf(el.getPrix())) {
                                    el.setPrix(String.valueOf(Integer.parseInt(el.getPrix()) - (Integer.parseInt(amount.getText().toString()))));
                                notifyAdapterFrom();
                                dataBaseManager.update(elOld,el ,"NotesToGiveBack");
                                    dismiss();

                            } else if (partialRef.isChecked() &&
                                    Integer.parseInt(amount.getText().toString()) > Integer.valueOf(el.getPrix())) {
                                    Toast.makeText(getContext(), "The refound is higther than the amount:"+el.getPrix(),
                                    Toast.LENGTH_LONG).show();
                                    amount.setTextColor(Color.RED);
                            }
                        }

                    } else if (frag.equals("to")) {
                        ElementList el = (ElementList) listTo.getItemAtPosition(position);
                        ElementList old =el;
                        if (totalRef.isChecked()) {
                                elemTo.remove(el);
                            notifyAdapterTo();
                            dataBaseManager.deleteNoteTo(el);

                                dismiss();
                        } else {
                            if (partialRef.isChecked() &&
                                    Integer.parseInt(amount.getText().toString()) == Integer.valueOf(el.getPrix())) {
                                    elemTo.remove(el);
                                notifyAdapterTo();
                                dataBaseManager.deleteNoteTo(el);

                                    dismiss();
                            } else if (partialRef.isChecked() &&
                                    Integer.parseInt(amount.getText().toString()) < Integer.valueOf(el.getPrix())) {
                                    el.setPrix(String.valueOf(Integer.parseInt(el.getPrix()) - (Integer.parseInt(amount.getText().toString()))));
                                dataBaseManager.update(old, el,"NotesToGetBack");
                                notifyAdapterTo();
                                    dismiss();

                            } else if (partialRef.isChecked() &&
                                    Integer.parseInt(amount.getText().toString()) > Integer.valueOf(el.getPrix())) {
                                    Toast.makeText(getContext(), "The refound is higther than the amount:"+el.getPrix(),
                                    Toast.LENGTH_LONG).show();
                                    amount.setTextColor(Color.RED);

                            }
                        }

                    }

                }
            }



    });

    }
}
