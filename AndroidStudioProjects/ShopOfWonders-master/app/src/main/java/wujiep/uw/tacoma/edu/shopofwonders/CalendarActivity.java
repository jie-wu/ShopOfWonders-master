package wujiep.uw.tacoma.edu.shopofwonders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import wujiep.uw.tacoma.edu.shopofwonders.Item.Item;

/**
 * Created by jieping on 5/30/17.
 */

public class CalendarActivity extends AppCompatActivity{

    private TextView mAmountTextView;
    private TextView mDescTextView;
    private TextView mNameTextView;
    private TextView mPriceTextView;
    private TextView mDiscountsTextView;
    private TextView mDateTextView;
    private TextView mTotalTextView;
    private TextView mDate;
    private Integer mTotal;
    private Integer mAmount;
    private Integer mY;
    private Integer mD;
    private Integer mM;
    private String date;
    Item item;
    private Button btn, btn2;
    ArrayList<HashMap<String, String>> contactList;
    private TextView tvMinus, tvAdd;
    ArrayList<HashMap<String, String>> dateList;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.icon);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_green_dark));
        setSupportActionBar(myToolbar);

        lv = (ListView) findViewById(R.id.lv);
        lv.setTextFilterEnabled(true);
        dateList = new ArrayList<HashMap<String, String>>();
        Bundle b = getIntent().getExtras();
//        final Item item = (Item)b.getSerializable("data);
        ArrayList<HashMap<String, String>> contactList = (ArrayList<HashMap<String,String>>)b.getSerializable("data");
        getDate();
        createView();
        getList(contactList);

        super.onResume();


//        tvMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTotal > 0 ) {
//                    mTotal = mTotal - 1;
//                    item.setmTotal(String.valueOf(mTotal));
//                    mAmount = mAmount + 1;
//                    item.setmAmount(String.valueOf(mAmount));
//                    Intent in = new Intent();
//                    in.putExtra("total", mTotal);
////                in.putExtra("map", item);
//                    mTotalTextView.setText(String.valueOf(item.getmTotal()));
//                    mAmountTextView.setText("Amount: " + item.getAMOUNT());
//                    in.putExtra("name", item.getNAME());
//                    setResult(1001, in);
//                }
//                Log.e("total minus: ", String.valueOf(mTotal));
//                //finish();
//            }
//        });
//
//        //Add
//        tvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mAmount != 0 ) {
//                    mTotal = mTotal + 1;
//                    item.setmTotal(String.valueOf(mTotal));
//                    mAmount = mAmount - 1;
//                    item.setmAmount(String.valueOf(mAmount));
//                    Intent in = new Intent();
//                    in.putExtra("total", mTotal);
////                in.putExtra("map", item);
//                    in.putExtra("name", item.getNAME());
//                    mTotalTextView.setText(String.valueOf(item.getmTotal()));
//                    mAmountTextView.setText("Amount: " + item.getAMOUNT());
//                    setResult(1001, in);
//
//                }
//                Log.e("total add: ", item.getNAME() + String.valueOf(mTotal) + " amount:" + String.valueOf(mAmount));
//            }
//        });
    }
    private void getList(ArrayList<HashMap<String, String>> contactList) {
        for (int i = 0; i < contactList.size(); i++) {
//            Log.e("in add ", contactList.get(i).get("total"));
            if (Integer.valueOf(contactList.get(i).get("total")) > 0) {
                this.dateList.add(contactList.get(i));
                Log.e("shopping out ", contactList.get(i).toString());

            }
            String theDate = contactList.get(i).get("data");
            String s[] = theDate.split("-");
            Integer y = 0, m = 0, d = 0, dis = 0;
            double price = 0.00;
            boolean flag = false;
            y = Integer.valueOf(s[0]);
            m = Integer.valueOf(s[1]);
            d = Integer.valueOf(s[2]);
            if (y.intValue() == mY.intValue() && mM.intValue() == m.intValue() && mD.intValue() == d.intValue()) {
                dateList.add(contactList.get(i));
            }
        }
        ListAdapter adapter = new SimpleAdapter(
                CalendarActivity.this, dateList,
                R.layout.list_item, new String[]{Item.NAME, Item.PRICE, Item.DISCOUNTS, Item.DESC, Item.AMOUNT, Item.DATE, Item.TOTAL
        }, new int[]{R.id.item_name, R.id.item_price, R.id.item_discounts, R.id.item_desc, R.id.item_amount, R.id.item_date, R.id.item_total});

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(CalendarActivity.this, "You quicking in" + adapterView.getAdapter().toString(), Toast.LENGTH_SHORT).show();
                HashMap<String, String> map = new HashMap<>();
                map = (HashMap<String, String>)adapterView.getAdapter().getItem(i);
                Item item = new Item(map.get(Item.NAME), map.get(Item.PRICE), map.get(Item.DISCOUNTS), map.get(Item.DESC), map.get(Item.AMOUNT),map.get(Item.DATE), map.get(Item.TOTAL));
                Bundle b = new Bundle();

                b.putSerializable("item",item);
                Log.e("send____________",  adapterView.getAdapter().getItem(i).toString());
                Intent in = new Intent();
                in.putExtras(b);
                in.setClass(CalendarActivity.this, DetailActivity.class);
                //Log.i("message", b.toString());
                startActivityForResult(in,1000);
            }

        });
    }

    private void getDate() {

        SimpleDateFormat sdate = new SimpleDateFormat("YYYY-MM-dd");
        date = sdate.format(new Date());
        String s[] = date.split("-");
        mDate.setText(date);
        if(s.length == 3) {
            mY = Integer.valueOf(s[0]);
            mM = Integer.valueOf(s[1]);
            mD = Integer.valueOf(s[2]);
        } else {
            System.err.print("Wrong date: "+ date);
        }
    }
    public void createView() {
        mAmountTextView = (TextView) findViewById(R.id.item_amount);
        mDescTextView = (TextView) findViewById(R.id.item_desc);
        mNameTextView = (TextView) findViewById(R.id.item_name);
        mPriceTextView = (TextView) findViewById(R.id.item_price);
        mDiscountsTextView = (TextView) findViewById(R.id.item_discounts);
        mDateTextView = (TextView) findViewById(R.id.item_date);
        mTotalTextView = (TextView) findViewById(R.id.item_total);
        mDate = (TextView)findViewById(R.id.day_disc);

    }

//    public void createView(Item item) {
//        mAmountTextView = (TextView) findViewById(R.id.item_amount);
//        mDescTextView = (TextView) findViewById(R.id.item_desc);
//        mNameTextView = (TextView) findViewById(R.id.item_name);
//        mPriceTextView = (TextView) findViewById(R.id.item_price);
//        mDiscountsTextView = (TextView) findViewById(R.id.item_discounts);
//        mDateTextView = (TextView) findViewById(R.id.item_date);
//        mTotalTextView = (TextView) findViewById(R.id.item_total);
//        tvMinus = (TextView) findViewById(R.id.minus_item_button);
//        tvAdd = (TextView) findViewById(R.id.add_item_button);
//
//        mAmountTextView.setText("Amount: " + item.getAMOUNT());
//        mDescTextView.setText("Description: " + item.getDESC());
//        mNameTextView.setText("Item Name: " + item.getNAME());
//        mPriceTextView.setText("Price: " + item.getPRICE());
//        mDiscountsTextView.setText("Discounts: " + item.getDISCOUNTS() + "% OFF");
//        mDateTextView.setText("Discounts Day: " + item.getDATE());
//        mTotalTextView.setText(String.valueOf(item.getmTotal()));
//
//
//    }

}
