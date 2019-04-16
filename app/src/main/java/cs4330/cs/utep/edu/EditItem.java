package cs4330.cs.utep.edu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

public class EditItem extends AppCompatActivity {

    EditText newItemName;
    EditText newItemURL;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edititem);

        int position = getIntent().getIntExtra("current", 2);
        Item item = (Item) getIntent().getSerializableExtra("item");

        newItemName = findViewById(R.id.itemName);
        newItemName.setText(item.itemName);
        newItemURL = findViewById(R.id.itemURL);
        newItemURL.setText(item.url);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            item.itemName = newItemName.getText().toString();
            item.url = newItemURL.getText().toString();
            Intent data = new Intent();
            data.putExtra("updatedItem", item);
            data.putExtra("position", position);
            setResult(RESULT_OK, data);
            finish();
        });


    }

}
