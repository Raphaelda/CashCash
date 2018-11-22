package com.example.raphaeld.cashcash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class FragFromMe extends Fragment   {

    public static  ListView listfrom;
    String  Name ;
    String  price;
    String  MyDate;
    String  Note ;
    String  Devise;
    TextView emptyText;
    public static ArrayList<ElementList> elemFrom;
    public static CustomAdapter adapter;
    DataBaseManager dataBaseManager;


    public static void notifyAdapterFrom (){
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragfromme, container, false);

        listfrom=(ListView)rootView.findViewById(R.id.ListForMe);
        listfrom.setEmptyView(rootView.findViewById(R.id.emptyElementFrom));

        dataBaseManager=DataBaseManager.getInstance(this.getContext().getApplicationContext());

        elemFrom=new ArrayList<>();

        // elemFrom.add(new ElementList(this.getActivity(),"DAvid","24/01/12","12","$","rien"));
        elemFrom=dataBaseManager.readAllFromMe();

        adapter = new CustomAdapter(elemFrom,this.getContext());
        listfrom.setAdapter(adapter);

        listfrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public  void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ElementList el = (ElementList) listfrom.getItemAtPosition(position);
                String dev =el.getDevise();
                 EditDialog editDialog = new EditDialog(getContext());
                editDialog.show(position,dev,"from");
            }
        });
        listfrom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean  onItemLongClick(AdapterView<?> parent, View view, int position, long id){


                ElementList el = (ElementList) listfrom.getItemAtPosition(position);
                 final AddEditDialog d = new AddEditDialog(getContext(),el);
                d.show();
            return true ;
            }

        });

        return rootView;
    }



}
