package com.example.philipricedealership._models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.philipricedealership._utils.DatabaseHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int uid, userId;
    private float total;
    private String items, date; // <~> + ex : img.jpg+ube+100+1

    public Order(int uid, int userId, float total, String items, String date) {
        this.uid = uid;
        this.userId = userId;
        this.total = total;
        this.items = items;
        this.date = date;
    }

    public Order(int userId, float total, String items, String date) {
        this.userId = userId;
        this.total = total;
        this.items = items;
        this.date = date;
    }

    public Order(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private ContentValues getSelfContentValues(){
        ContentValues vals = new ContentValues();
        vals.put("total", total);
        vals.put("userId", userId);
        vals.put("items", items);
        vals.put("date", date);
        return vals;
    }

    public boolean saveState(Context context, DatabaseHelper dbHelper, boolean isNew){
        if(isNew){
            if(dbHelper.insert(getSelfContentValues(), "orders")){
                System.out.println("Product : New movie Saved Self");
                return true;
            }else{
                Toast.makeText(null, "Failed to create product", Toast.LENGTH_LONG);
                return false;
            }
        }else{
            if( !dbHelper.update(getSelfContentValues(), "uid="+getUid()+"", "orders") ){
                Toast.makeText(context, "Failed to save state", Toast.LENGTH_LONG);
                System.out.println("Product : Updated Self");
                return false;
            }else{
                fetchSelf(dbHelper);
                return true;
            }
        }
    }

    public void fetchSelf(DatabaseHelper dbHelper){
        try{
            Cursor cur = dbHelper.execRawQuery(String.format("SELECT * FROM orders WHERE uid=%d;",uid), null);
            if (cur == null || cur.getCount() == 0 || !cur.moveToNext()) return;
            setUid(cur.getInt(0));
            setUserId(cur.getInt(1));
            setTotal(cur.getFloat(2));
            setItems(cur.getString(3));
            setDate(cur.getString(4));
        }catch(Exception e){
            System.out.println("ERR ON FETCH " + e);
        }
    }

    public static ArrayList <Order> getAllOrder(DatabaseHelper dbHelper){
        ArrayList <Order> alls = new ArrayList<>();
        Cursor all = dbHelper.execRawQuery("SELECT * FROM orders", null);

        while(all.moveToNext()){
            alls.add(new Order(
                    all.getInt(0),
                    all.getInt(1),
                    all.getFloat(2),
                    all.getString(3),
                    all.getString(4)
            ));
        }

        return alls;
    }

}