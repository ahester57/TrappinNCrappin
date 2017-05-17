package tech.stin.trappinncrappin.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.activity.helper.CustomerHolder;
import tech.stin.trappinncrappin.data.Customer;

/**
 * Created by Austin on 5/11/2017.
 *
 * View fragment for customer list
 */

public class StreetView extends Fragment {
    private static final String TAG = StreetView.class.getSimpleName();

    private Customer curCustomer;
    private int dPosition;

    private RecyclerView cRecycler;
    private Button bGoToCustomer;
    private AdView mAdView;
    private StreetListener sListener;

    interface StreetListener {
        void goToCustomerPage(Customer customer);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dPosition = 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_streets, container, false);
        sListener = (StreetListener) getActivity();

        mAdView = (AdView) view.findViewById(R.id.adview_streets);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        bGoToCustomer = (Button) view.findViewById(R.id.button_view_customer);
        bGoToCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sListener != null && curCustomer != null) {
                    sListener.goToCustomerPage(curCustomer);
                    Log.d(TAG, "Customer visited: " + curCustomer.toString());
                }
            }
        });

        cRecycler = (RecyclerView) view.findViewById(R.id.customer_recycler);
        cRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        // @ TODO fix this resetting all the time
        ArrayList<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customers.add(new Customer());
        }
        cRecycler.setAdapter(new CustomerAdapter(customers, dPosition));

        return view;
    }


    private class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {
        private final String TAG = StreetView.CustomerAdapter.class.getSimpleName();

        private int selectedPos = 0;
        private List<Customer> mCustomers;

        CustomerAdapter(List<Customer> dealers, int pos) {
            mCustomers = dealers;
            selectedPos = pos;
            setSelectedCustomer(pos);
        }

        @Override
        public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.customer_recycler_item, parent, false);
            return new CustomerHolder(view);
        }

        private void setSelectedCustomer(int pos) {
            if (mCustomers != null) {
                curCustomer = mCustomers.get(pos);
                dPosition = pos;
                Log.d(TAG, "Customer selected: " + curCustomer.toString());
            }
        }

        @Override
        public void onBindViewHolder(CustomerHolder holder, final int position) {
            if (mCustomers != null) {
                try {
                    // for highlighting
                    if (selectedPos == position) {
                        holder.itemView.setBackground(getResources().getDrawable(R.drawable.line));
                        holder.itemView.setPadding(8, 8, 8, 8);
                    } else {
                        holder.itemView.setBackgroundResource(0);
                        holder.itemView.setPadding(8, 8, 8, 8);
                    }

                    holder.bindCustomer(mCustomers.get(position));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notifyItemChanged(selectedPos);
                            selectedPos = position;
                            notifyItemChanged(selectedPos);
                            setSelectedCustomer(position);
                        }
                    });
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mCustomers != null) {
                return mCustomers.size();
            }
            return 0;
        }
    }
}
