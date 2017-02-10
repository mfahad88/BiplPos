package layout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.biplpos.DbHelper;
import com.example.bipl.biplpos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {
    EditText name,unitPrice;
    Button btn_done;
    TableLayout tableLayout;
    TextView name_tv;
    TextView unit_tv;

    View view;
    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_inventory, container, false);
        name=(EditText)view.findViewById(R.id.editTextName);
        unitPrice=(EditText)view.findViewById(R.id.editTextUnitPrice);
        btn_done=(Button)view.findViewById(R.id.buttonDone);
        tableLayout=(TableLayout)view.findViewById(R.id.tableLayout);
       // new getRecords().execute();
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!String.valueOf(name.getText()).equals("") && !String.valueOf(unitPrice.getText()).equals("")) {

                    fillTable(String.valueOf(name.getText()),String.valueOf(unitPrice.getText()));
                    DbHelper dbHelper=new DbHelper(view.getContext());
                    dbHelper.insertProducts(String.valueOf(name_tv.getText()),String.valueOf(unit_tv.getText()));
                    name.setText(null);
                    name.setHint("Name");
                    unitPrice.setText(null);
                    unitPrice.setHint("Unit Price");
                    name.requestFocus();

                }
            }
        });
        return view;


    }

    public void fillTable(String name,String price){
        try {
            TableRow tr = new TableRow(view.getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView name_tv = new TextView(view.getContext());
            TextView unit_tv = new TextView(view.getContext());
            name_tv.setText(String.valueOf(name));
            unit_tv.setText(String.valueOf(price));
            name_tv.setWidth(175);
            unit_tv.setWidth(170);
            name_tv.setTextSize((float) 18.0);
            unit_tv.setTextSize((float) 18.0);
            name_tv.setGravity(Gravity.CENTER_HORIZONTAL);
            unit_tv.setGravity(Gravity.CENTER_HORIZONTAL);
            name_tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            unit_tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(name_tv);
            tr.addView(unit_tv);
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
    }

  /*  private class insertRecords extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fillTable(String.valueOf(name_tv.getText()),String.valueOf(unit_tv.getText()));
            //final DbHelper dbHelper=new DbHelper(getContext());
            //dbHelper.insertProducts(String.valueOf(name_tv.getText()),String.valueOf(unit_tv.getText()));
        }
    }*/

    private class getRecords extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final DbHelper dbHelper=new DbHelper(view.getContext());
            for(int i=0;i<dbHelper.getTable().size();i++){
                fillTable(dbHelper.getTable().get(i).getNAME(),String.valueOf(dbHelper.getTable().get(i).getUNITPRICE()));
            }
        }
    }
}
