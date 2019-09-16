package com.example.ics.documentscanner;

import android.accounts.Account;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Accountlist_adapter  extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Account_sqldb> Account_list;

    public Accountlist_adapter(Context context, int layout, ArrayList<Account_sqldb> Account_list) {
        this.context = context;
        this.layout = layout;
        this.Account_list = Account_list;
    }

    @Override
    public int getCount() {
        return Account_list.size();
    }

    @Override
    public Object getItem(int position) {
        return Account_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtPrice;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

//            holder.txtName = (TextView) row.findViewById(R.id.txtName);
//            holder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
//            holder.imageView = (ImageView) row.findViewById(R.id.imgFood);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Account_sqldb account = Account_list.get(position);

//        holder.txtName.setText(food.getName());
//        holder.txtPrice.setText(food.getPrice());
      System.out.println(""+account.getName());
      System.out.println(""+account.getEmail());
      System.out.println(""+account.getPAN());
//      //  byte[] foodImage = food.getImage();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
//        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
