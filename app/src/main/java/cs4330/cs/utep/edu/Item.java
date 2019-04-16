package cs4330.cs.utep.edu;

import android.os.Parcelable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.text.DecimalFormat;

public class Item implements Serializable,Comparable<Item> {

    protected String itemName;
    protected String url;
    protected double initialPrice;
    protected double currentPrice;
    protected PriceFinder pf;


    public Item(String name){
        url = name;
    }

    protected void getPrice(){
        Log.d("Andrew","Made it to getPrice");
        pf.execute();
        this.currentPrice = pf.item.currentPrice;
        Log.d("Andrew","Current price: " + this.currentPrice + " " + pf.item.currentPrice);
    }
    void setPrice(Double newPrice){
        this.currentPrice = newPrice;
    }

//    public void calculatePercent() {
//        DecimalFormat df = new DecimalFormat("#.##");
//        double temp = ((currentPrice-initialPrice)/currentPrice * 100);
//        percent = df.format(temp) + "%";
//    }

    @Override
    public int compareTo(Item item) {
        return Double.compare(this.currentPrice,item.currentPrice);
    }
}
