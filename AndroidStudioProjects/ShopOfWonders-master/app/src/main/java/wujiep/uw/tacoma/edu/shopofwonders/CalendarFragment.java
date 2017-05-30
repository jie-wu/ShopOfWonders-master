package wujiep.uw.tacoma.edu.shopofwonders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import wujiep.uw.tacoma.edu.shopofwonders.Item.Item;

/**
 * Created by jieping on 5/29/17.
 */
public class CalendarFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private ListView lv;
    private TextView mID;
    private TextView mUserID;
    private TextView mAmount;
    private TextView mDesc;
    private TextView mName;
    private TextView mPrice;
    private TextView mDiscounts;
    private TextView mDate;
    private TextView mTotal;
    private Integer mY;
    private Integer mD;
    private Integer mM;
    private String date;
    ArrayList<HashMap<String, String>> addList;
        public CalendarFragment() {
            // Required empty public constructor
        }


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Warning - month is 0 based.
            Toast.makeText(getActivity(), "You picked " + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year,
                    Toast.LENGTH_LONG)
                    .show();

        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.calendar_activity, container, false);
        lv = (ListView) v.findViewById(R.id.cal_lv);
        mAmount = (TextView)v.findViewById(R.id.item_amount);
        mDesc = (TextView)v.findViewById(R.id.item_desc);
        mName= (TextView)v.findViewById(R.id.item_name);
        mPrice=(TextView)v.findViewById(R.id.item_price);
        mDiscounts =(TextView)v.findViewById(R.id.item_discounts);
        mDate = (TextView)v.findViewById(R.id.item_date);
        mTotal = (TextView)v.findViewById(R.id.item_total);
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();

        Bundle b = intent.getExtras();
//        final Item item = (Item)b.getSerializable("data);
        ArrayList<HashMap<String, String>> contactList = (ArrayList<HashMap<String,String>>)b.getSerializable("data");
        Integer y=0,m=0,d=0, dis = 0;

        double price= 0.00;
        boolean flag = false;
        for(int i = 0; i < contactList.size(); i++) {
            String theDate = contactList.get(i).get(Item.DATE);
            String s[] = theDate.split("-");
            y = Integer.valueOf(s[0]);
            m = Integer.valueOf(s[1]);
            d = Integer.valueOf(s[2]);

            if (y.intValue() == mY.intValue() && mM.intValue() == m.intValue() && mD.intValue() == d.intValue()) {
                    addList.add(contactList.get(i));
            }
        }
        if(!addList.isEmpty()) {
            ListAdapter adapter = new SimpleAdapter(CalendarFragment.this.getContext(), addList,
                    R.layout.list_item, new String[]{Item.NAME, Item.PRICE,Item.DISCOUNTS, Item.DESC, Item.AMOUNT, Item.DATE,Item.TOTAL
            }, new int[]{R.id.item_name, R.id.item_price, R.id.item_discounts,R.id.item_desc, R.id.item_amount, R.id.item_date, R.id.item_total});

            lv.setAdapter(adapter);
        }
    }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
            final Calendar c = Calendar.getInstance();
            mY = c.get(Calendar.YEAR);
            mM = c.get(Calendar.MONTH);
            mD = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, mY, mM, mD);

        }

}
