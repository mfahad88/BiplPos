package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bipl.biplpos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {


    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sales, container, false);
    /*    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);


            String name = sharedPref.getString("productName",null);
            String unitPrice = sharedPref.getString("productPrice",null);*/
            Toast.makeText(view.getContext(), "Test", Toast.LENGTH_SHORT).show();

        return view;
    }

}
