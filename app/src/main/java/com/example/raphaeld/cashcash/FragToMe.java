package com.example.raphaeld.cashcash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.raphaeld.cashcash.FragFromMe.adapter;


public class FragToMe extends Fragment {

    public  static ListView listTo;
    String  Name;
    String  Price;
    String  MyDate;
    String  Note ;
    String  Devise ;
    public static ArrayList<ElementList> elemTo;
    private static CustomAdapter adapter;
    DataBaseManager dataBaseManager;


    public static void notifyAdapterTo(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragtome, container, false);

        listTo=(ListView)rootView.findViewById(R.id.listtome);
        listTo.setEmptyView(rootView.findViewById(R.id.emptyElementTo));

        dataBaseManager=DataBaseManager.getInstance(this.getContext().getApplicationContext());



        elemTo=new ArrayList<>();
       // elemTo.add(new ElementList(this.getActivity(), "DAvid", "24/01/12", "12", "$", "rienfghfghgfhgfhgfhsghgfsdjfhdjfhdjhfjdghdk"));


        elemTo=dataBaseManager.readAllToMe();

        adapter = new CustomAdapter(elemTo, this.getContext());
        listTo.setAdapter(adapter);

        listTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public  void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ElementList el = (ElementList) listTo.getItemAtPosition(position);
                String dev =el.getDevise();
                 EditDialog editDialog = new EditDialog(getContext());
                editDialog.show(position,dev,"to");
            }
        });

        listTo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean  onItemLongClick(AdapterView<?> parent, View view, int position, long id){



                ElementList el = (ElementList) listTo.getItemAtPosition(position);
                final AddEditDialog d = new AddEditDialog(getContext(),el);
                d.show();

                return true ;
            }

        });

        return rootView;

    }



}



