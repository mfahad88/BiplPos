package layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_sales, container, false);
        totalAmount=(TextView)view.findViewById(textTotal);
        try {
            new getProduct().execute();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
        return view;
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
            for(int i=0;i<dbHelper.getTable().size();i++) {
                totalAmount.setText(dbHelper.getTable().get(i).getUNITPRICE()+"\n");
            }
        }
    }

}
