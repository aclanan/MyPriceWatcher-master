package cs4330.cs.utep.edu;

import android.os.Parcelable;

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
        pf = new PriceFinder(this);
    }

    protected double getPrice(){
        return 0.0;
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
