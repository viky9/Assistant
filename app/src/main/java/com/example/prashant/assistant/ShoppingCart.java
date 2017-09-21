package com.example.prashant.assistant;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingCart extends Activity {
    public String selected="";

    ArrayList<Product> products = new ArrayList<Product>();
    ListAdapter boxAdapter;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        fillData();
        boxAdapter = new ListAdapter(this, products);

        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);
    }

    void fillData() {
        for (int i = 1; i <= 20; i++) {
            products.add(new Product("Product " + i, i * 100,
                    R.drawable.ic_launcher, false));
        }
    }

    public void showResult(View v) {
        String result = "Selected Product are :";
        Intent i= new Intent(this,MapPoint.class);
        int totalAmount=0;
        for (Product p : boxAdapter.getBox()) {
            if (p.box){
                result += "\n" + p.name;
                selected += p.name+"\n";
                totalAmount+=p.price;
            }
        }
        i.putExtra("items",selected);
        Toast.makeText(this, result+"\n"+"Total Amount:="+totalAmount, Toast.LENGTH_LONG).show();
        startActivity(i);
    }
}