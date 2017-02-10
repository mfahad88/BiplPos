package layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.biplpos.DbHelper;
import com.example.bipl.biplpos.R;

import static com.example.bipl.biplpos.R.id.textTotal;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {
    TextView totalAmount;
    View view;
    TableLayout tableLayout;
    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_sales, container, false);
        totalAmount=(TextView)view.findViewById(R.id.textTotal);
        tableLayout=(TableLayout)view.findViewById(R.id.tableLayoutSales);
        try {
            new getProduct().execute();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
        return view;
    }
    public void fillTable(String name,String price){
        final TableRow tr = new TableRow(view.getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView name_tv = new TextView(view.getContext());
        final TextView unit_tv = new TextView(view.getContext());

        name_tv.setText(String.valueOf(name));
        unit_tv.setText(String.valueOf(price));
        name_tv.setWidth(175);
        unit_tv.setWidth(170);
        name_tv.setTextSize((float) 22.0);
        unit_tv.setTextSize((float) 22.0);
        name_tv.setGravity(Gravity.CENTER_HORIZONTAL);
        unit_tv.setGravity(Gravity.CENTER_HORIZONTAL);
        name_tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        unit_tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(name_tv);
        tr.addView(unit_tv);
        tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }
    public class getProduct extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DbHelper dbHelper=new DbHelper(getContext());
           for(int i=0;i<dbHelper.getSales().size();i++) {
                fillTable(dbHelper.getSales().get(i).getName(), String.valueOf(dbHelper.getSales().get(i).getTotalAmount()));
              // totalAmount.setText(String.valueOf(dbHelper.getSales().get(i).getTotalAmount()));
            }
        }
    }

}
