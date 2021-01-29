package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int  EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btn;
    EditText etItem;
    RecyclerView rvItems;
    ItemAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);



        loadItems();


        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                //Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };




        itemsAdapter = new ItemAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                String todoItem = etItem.getText().toString();
                  //Add item to the model
                items.add(todoItem);
                  //Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted( items.size()  - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();






            }
        });

    }
            //Persistance
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    // This function will load items by reading every line of the data file
    private void loadItems()
    {
        try{             //We will try to execute this line of code and if there is an exception then we need to handle it in the catch block
        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
    }
        catch(IOException e)
        {
            Log.e("MainActivity", "Error reading items", e) ;                                                 //log.e is a way to log errors to log catch, logs is a way for developers to identify what's actually happening in their program
            items = new ArrayList<>();

        }
    //This function saves items by writing them into the data file
}
       private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
       }


}