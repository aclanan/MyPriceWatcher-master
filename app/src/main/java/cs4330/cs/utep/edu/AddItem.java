package cs4330.cs.utep.edu;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.content.*;

public class AddItem extends AppCompatActivity {

    PriceFinder pf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        EditText addItemURL = findViewById(R.id.newItem);
        EditText addItemName = findViewById(R.id.newItemName);
        Button addItemButton = findViewById(R.id.add);

        String url = getIntent().getStringExtra("newItem");

        if (url != null) {
            addItemURL.setText(url);
        }

        addItemButton.setOnClickListener(view -> {
            Item item = new Item(addItemURL.getText().toString());
            item.itemName = addItemName.getText().toString();
//            item.currentPrice = pf.priceFinder(item.itemName);
//            item.initialPrice = pf.priceFinder(item.itemName);
//            item.calculatePercent();

            Intent data = new Intent();
            data.putExtra("newItem", item);
            setResult(RESULT_OK, data);
            finish();
        });
    }


}
