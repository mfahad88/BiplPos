package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.bipl.biplpos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {
    EditText name,unitPrice;
    Button btn_done;
    TableLayout tableLayout;
    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_inventory, container, false);
        name=(EditText)view.findViewById(R.id.editTextName);
        unitPrice=(EditText)view.findViewById(R.id.editTextUnitPrice);
        btn_done=(Button)view.findViewById(R.id.buttonDone);
        tableLayout=(TableLayout)view.findViewById(R.id.tableLayout);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!String.valueOf(name.getText()).equals("") && !String.valueOf(unitPrice.getText()).equals("")) {
                    TableRow tr = new TableRow(view.getContext());
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView name_tv = new TextView(view.getContext());
                    TextView unit_tv = new TextView(view.getContext());
                    name_tv.setText(String.valueOf(name.getText()));
                    unit_tv.setText(String.valueOf(unitPrice.getText()));
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
                    name.setText(null);
                    name.setHint("Name");
                    unitPrice.setText(null);
                    unitPrice.setHint("Unit Price");
                    name.requestFocus();
                    editor.putString("productName", String.valueOf(name_tv.getText()));
                    editor.putString("productPrice", String.valueOf(unit_tv.getText()));
                    editor.commit();
                }
            }
        });
        return view;
    }

}
