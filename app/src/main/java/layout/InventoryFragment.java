package layout;

import android.app.Dialog;
import android.app.FragmentTransaction;
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
    EditText name, unitPrice;
    Button btn_done;
    TableLayout tableLayout;
    TextView name_tv;
    TextView unit_tv;
    Float dialogQuantity;
    private String prodName,prodPrice,prodQty;
    private  String inventoryName,inventoryPrice;
    View view;
    private int Id=0;
    private int Id_prod=0;
    public InventoryFragment() {
        // Required empty public constructor
    }
    public static InventoryFragment newInstance() {
        return new InventoryFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inventory, container, false);
        name = (EditText) view.findViewById(R.id.editTextName);
        unitPrice = (EditText) view.findViewById(R.id.editTextUnitPrice);
        btn_done = (Button) view.findViewById(R.id.buttonDone);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

        try {
            new getRecords().execute();
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(name.getText()).equals("") && !String.valueOf(unitPrice.getText()).equals("")) {
                    try {
                        inventoryName=String.valueOf(name.getText());
                        inventoryPrice=String.valueOf(unitPrice.getText());
                        fillTable(inventoryName,inventoryPrice);
                        Id++;
                        new insertRecords().execute();
                    } catch (Exception e) {
                        Log.e("TAG", e.getMessage());
                    }

                    name.setText(null);
                    name.setHint("Name");
                    unitPrice.setText(null);
                    unitPrice.setHint("Unit Price");


                }
            }
        });
        return view;
    }

    public void fillTable(final String name, String price) {
        try {
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
            tr.setTag(tableLayout.getChildCount());
            tr.addView(name_tv);
            tr.addView(unit_tv);

            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rowCount=(int)tr.getTag();
                    TextView tv=(TextView)tr.getChildAt(0);
                    final TextView tv_unitprice=(TextView)tr.getChildAt(1);

                   // Toast.makeText(view.getContext(), String.valueOf(tv_unitprice.getText()), Toast.LENGTH_SHORT).show();

                    final Dialog dialog=new Dialog(view.getContext());
                    dialog.setContentView(R.layout.productdialog);
                    final EditText productName=(EditText)dialog.findViewById(R.id.productName);
                    final EditText productQuantity=(EditText)dialog.findViewById(R.id.productQty);
                    Button btn_close=(Button)dialog.findViewById(R.id.buttonClose);
                    Button btn_done=(Button)dialog.findViewById(R.id.buttonDone);
                    try {
                        dialog.setTitle("Select Quantity");
                        productName.setText(tv.getText());
                        btn_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        btn_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Id_prod++;
                                dialogQuantity=Float.parseFloat(String.valueOf(productQuantity.getText()))*Float.parseFloat(String.valueOf(tv_unitprice.getText()));
                                prodName= String.valueOf(productName.getText());
                                prodPrice=String.valueOf(dialogQuantity);
                                prodQty=String.valueOf(productQuantity.getText());
                                new insertSales().execute();
                                dialog.dismiss();

                                try {
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Qty",prodName);
                                    bundle.putString("Name",prodPrice);
                                    SalesFragment salesFragment=new SalesFragment();
                                    salesFragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction().add(R.id.sales_frag,salesFragment).commit();

                                  //  Toast.makeText(dialog.getContext(), String.valueOf(dialogQuantity), Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Log.e("TAG",e.getMessage());
                                }
                            }
                        });
                        dialog.show();
                    }catch (Exception e){
                        Log.e("TAG",e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
    }

    private class getRecords extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final DbHelper dbHelper = new DbHelper(view.getContext());
            for (int i = 0; i < dbHelper.getInventory().size(); i++) {
                fillTable(dbHelper.getInventory().get(i).getNAME(), String.valueOf(dbHelper.getInventory().get(i).getUNITPRICE()));

            }
        }
    }

    private class insertRecords extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DbHelper dbHelper = new DbHelper(view.getContext());
         //   Toast.makeText(getContext(), inventoryName+","+ inventoryPrice, Toast.LENGTH_SHORT).show();
            dbHelper.insertProducts(Id,inventoryName, inventoryPrice);
        }
    }

    private class insertSales extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DbHelper dbHelper = new DbHelper(view.getContext());
            //Toast.makeText(getContext(), prodName+" "+prodPrice, Toast.LENGTH_SHORT).show();
            dbHelper.insertSales(Id_prod,prodName,prodQty, prodPrice);
        }
    }

}