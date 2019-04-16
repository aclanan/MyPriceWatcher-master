package cs4330.cs.utep.edu;

import android.app.PendingIntent;
import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.text.DecimalFormat;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    theArrayAdapter itemList;
    ArrayList <Item> items = new ArrayList<Item>();
    int currentItemPosition;
    int addCode = 1;
    int editCode = 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case R.id.addItem:
                onClickAddItem(menuItem);
                return true;
            case R.id.refresh:
                for(Item i: itemList.itemList){
                    i.getPrice();
//                    i.calculatePercent();
                }
                itemList.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected void onClickSortBy(MenuItem menuitem){
        Collections.sort(itemList.itemList, new Comparator<Item>() {
            @Override
            public int compare(Item item, Item t1) {
                return (int) item.currentPrice - (int) t1.currentPrice;
            }
        });
        itemList.notifyDataSetChanged();
    }

    protected void onClickFilterBy(MenuItem menuItem ){
        itemList.filter("Nike");
    }

    protected void onClickAddItem(MenuItem menuItem){
        startActivityForResult(new Intent(this, AddItem.class), addCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == addCode){
            if (resultCode == RESULT_OK){
                //Log.d("Entered: ", requestCode + " " + resultCode);
                Item item = (Item) data.getSerializableExtra("newItem");
                items.add(item);
                itemList.notifyDataSetChanged();
            }
        }
        if (requestCode == editCode){
            if (resultCode == RESULT_OK){
                Item item = (Item) data.getSerializableExtra("updatedItem");
                int pos = data.getIntExtra("position", currentItemPosition);
                items.set(pos, item);
                itemList.notifyDataSetChanged();
            }
        }
    }

    protected void onClickEditItem(MenuItem menuItem){
        Intent data = new Intent(this, EditItem.class);
        data.putExtra("current", currentItemPosition);
        data.putExtra("item", items.get(currentItemPosition));
        startActivityForResult(data, editCode);
    }

    protected void onClickDeleteItem(MenuItem menuItem){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                items.remove(currentItemPosition);
                itemList.notifyDataSetChanged();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    protected void onClickStartWebpage(MenuItem menuItem){
        Uri uri = Uri.parse(items.get(currentItemPosition).url);

//        //Regular Web Browser //
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private PendingIntent createPendingShareIntent(){
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("text/plain");
        actionIntent.putExtra(Intent.EXTRA_TEXT, "Share text");
        return PendingIntent.getActivity(
                getApplicationContext(), 0, actionIntent, 0);
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.refresh:
                itemList.itemList.get(currentItemPosition).pf.getPrice();
                itemList.itemList.get(currentItemPosition).getPrice();
                itemList.notifyDataSetChanged();
                return true;
            case R.id.editItem:
                onClickEditItem(menuItem);
                return true;
            case R.id.deleteItem:
                onClickDeleteItem(menuItem);
                return true;
            case R.id.startWebpage:
                onClickStartWebpage(menuItem);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Intent data = new Intent(this, AddItem.class);
            data.putExtra("newItem", sharedText);
            startActivityForResult(data, addCode);
        }
    }

    private void checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if (isConnected == false) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Wifi is not connected, redirecting to network settings");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            Toast toast = Toast.makeText(MainActivity.this, "Wifi is connected", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);

        checkNetworkConnection();

        Intent intent = getIntent();
        if (Intent.ACTION_SEND.equals(intent.getAction()) && intent.getType() != null) {
            if (intent.getType().equals("text/plain")) {
                handleSendText(intent);
            }
        }

        itemList = new theArrayAdapter(this, items);
        listView = (ListView) findViewById(R.id.theList);
        listView.setAdapter(itemList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.setOnMenuItemClickListener(MainActivity.this::onMenuItemClick);
                currentItemPosition = position;
                popup.inflate(R.menu.popupmenu);
                popup.show();
            }
        });


    }
}


