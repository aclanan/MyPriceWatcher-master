package cs4330.cs.utep.edu;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
//Needs Work //
public class PriceFinder extends AsyncTask<Void,Void,String> {
    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";

    protected Item item;
    protected theArrayAdapter listAdapter;
    public PriceFinder(Item i,theArrayAdapter listAdapter){
        this.item = i;
        this.listAdapter = listAdapter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.d("Andrew","Downloading...." + item.url);

        try{
            //zumiez works //
            if(item.url.contains("zumiez")){
                Document itemDoc = Jsoup.connect(item.url).get();
                Elements price = itemDoc.select(".price");
                this.item.currentPrice = Double.parseDouble(price.get(0).text().substring(1,price.get(0).text().length()));
                Log.d("Andrew","Zumiez: " + item.currentPrice);
                return price.get(0).text().substring(1,price.get(0).text().length());
            }
            if(item.url.contains("hm")){
                Document itemDoc = Jsoup.connect(item.url).get();
                Elements price = itemDoc.select(".price-value");
                this.item.currentPrice = Double.parseDouble(price.get(0).text().substring(1,price.get(0).text().length()));
                Log.d("Andrew","H&M: " + price.get(0).text());
                return price.get(0).text().substring(1,price.get(0).text().length());
            }


        }catch(IOException e ) {
            Log.d("Andrew","Download Failed ");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        item.currentPrice = Double.parseDouble(result);
        listAdapter.notifyDataSetChanged();
    }
}
