package com.example.raphaeld.cashcash;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by Raphaeld on 11/01/2017.

 pour le custom list view
 */

public class ElementList extends BaseAdapter {

    private String  nom;
    private String  mydate;
    private String  prix;
    private String  devise;
    private String  note;
    private String id;

    Context context;
    private static LayoutInflater inflater=null;


    public ElementList(Context mainActivity, String nom, String mydate, String prix, String devise, String note) {
        this.nom = nom;
        this.mydate = mydate;
        this.prix = prix;
        this.devise = devise;
        this.note = note;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getMydate() {
        return mydate;
    }

    public void setMydate(String mydate) {
        this.mydate = mydate;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        ElementList.inflater = inflater;
    }

    @Override
    public int getCount() {
        return nom.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class Holder
    {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.element, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.name);
        holder.tv2=(TextView) rowView.findViewById(R.id.price);
        holder.tv3=(TextView) rowView.findViewById(R.id.date);
        holder.tv4=(TextView) rowView.findViewById(R.id.devise);
        holder.tv5=(TextView) rowView.findViewById(R.id.note);
        holder.tv1.setText(nom);
        holder.tv2.setText(prix);
        holder.tv3.setText(mydate);
        holder.tv4.setText(devise);
        holder.tv5.setText(note);

        return rowView;
    }
}
