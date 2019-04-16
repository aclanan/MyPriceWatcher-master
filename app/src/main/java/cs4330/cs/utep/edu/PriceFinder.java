package cs4330.cs.utep.edu;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
//Needs Work //
public class PriceFinder extends AsyncTask<Void,Void,Void> {
    protected Item item;

    public PriceFinder(Item i){
        this.item = i;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("Andrew","Downloading...." + item.url);
        try{
            Document itemDoc = Jsoup.connect(item.url).get();
            Elements price = itemDoc.select(".price");
            Log.d("Andrew",price.get(0).text());

        }catch(IOException e ) {
            Log.d("Andrew","Download Failed :( ");
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result){

    }

}
