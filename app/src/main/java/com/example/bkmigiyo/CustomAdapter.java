package com.example.bkmigiyo;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    int grupid;
    Context context;
    SQLiteOpenHelper dbhelper;
    private ArrayList<String> records;
    private int lastPosition = -1;

    public CustomAdapter (Context context, int vg, int id, ArrayList<String> records){
         super(context,vg,id,records);
         this.context=context;
         grupid=vg;
         this.records=records;
     }
    public static class ViewHolder{
        TextView id;
        TextView t4makanan;
        TextView t4toping;
        TextView t4pedas;
        TextView t4keterangan;
        TextView t4jumlhmakan;
        TextView t4minum;
        TextView t4jmlhminum;
        Button hapus;
        Button add;
    }


    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(grupid, parent, false);
        final View result;

        ViewHolder viewHolder = new ViewHolder();

        String[] row_items=records.get(position).split("-");

        //viewHolder.id = (TextView) itemView.findViewById(R.id.tempatid);
        viewHolder.t4makanan= (TextView) itemView.findViewById(R.id.tempatpesanan);
        viewHolder.t4jumlhmakan= (TextView) itemView.findViewById(R.id.tempatjumlahmakanan);
       // viewHolder.t4minum= (TextView) itemView.findViewById(R.id.tempatminum);
        //viewHolder.t4jmlhminum= (TextView) itemView.findViewById(R.id.tempatjumlahminum);
        viewHolder.t4toping= (TextView) itemView.findViewById(R.id.tempattoping);
        viewHolder.t4pedas= (TextView) itemView.findViewById(R.id.tempatpedas);
        viewHolder.t4keterangan= (TextView) itemView.findViewById(R.id.tempatketerangan);
        viewHolder.hapus=(Button)itemView.findViewById(R.id.delete);
        viewHolder.add=(Button)itemView.findViewById(R.id.tambah);
        itemView.setTag(viewHolder);

        //set text to each textview of listview item
        ViewHolder holder =(ViewHolder) itemView.getTag();
        //holder.id.setText("No : "+row_items[0]);
        //holder.t4makanan.setText(row_items[1]);
        if (row_items[1].isEmpty()){
            holder.t4makanan.setText(row_items[6]);
        }else{
            holder.t4makanan.setText(row_items[1]);
        }
        if (row_items[5].isEmpty()) {
            holder.t4jumlhmakan.setText(row_items[7]);
        }else
            holder.t4jumlhmakan.setText(row_items[5]);

//        holder.t4minum.setText(row_items[6]);
//        if (row_items[7].isEmpty()) {
//            holder.t4jmlhminum.setText("");
//        }else
//            holder.t4jmlhminum.setText(row_items[7]);

        if (row_items[4].isEmpty()) {
            holder.t4toping.setText("");
        }else
            holder.t4toping.setText(row_items[4]);

        if (row_items[3].isEmpty()) {
            holder.t4pedas.setText("");
        }else
            holder.t4pedas.setText(row_items[3]);

        if (row_items[2].isEmpty()) {
            holder.t4keterangan.setText("");
        }else
            holder.t4keterangan.setText(row_items[2]);

        holder.hapus.setTag(row_items[0]);

        holder.add.setTag(row_items[0]);

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_buttom: R.anim.down_from_top);
        result=itemView;
        result.startAnimation(animation);
        lastPosition = position;

        return itemView;
    }


}
