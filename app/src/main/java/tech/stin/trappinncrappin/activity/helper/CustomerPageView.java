package tech.stin.trappinncrappin.activity.helper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.data.Customer;

/**
 * Created by Austin on 5/16/2017.
 */

public class CustomerPageView extends Fragment {
    private static final String TAG = CustomerPageView.class.getSimpleName();

    private RecyclerView cvRecycler;
    private TextView tCusName;
    private TextView tCusMessage;
    private Customer curCustomer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_page, container, false);

        tCusName = (TextView) view.findViewById(R.id.text_customer_page_name);
        tCusMessage = (TextView) view.findViewById(R.id.text_customer_page_message);

        cvRecycler = (RecyclerView) view.findViewById(R.id.customer_page_recycler);
        cvRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (curCustomer != null) {
            tCusName.setText(curCustomer.getName());
            tCusMessage.setText(curCustomer.getMessage());
            //cvRecycler.setAdapter(new DealerStashAdapter(curDealer.getStash()));
        }
        return view;
    }

    // should be called as of now
    public void setCustomer(Customer customer) {
        curCustomer = customer;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Customer Page destroyed");
        super.onDestroy();
    }
}
