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
import tech.stin.trappinncrappin.app.DrugConfig;
import tech.stin.trappinncrappin.app.SessionManager;
import tech.stin.trappinncrappin.data.Dealer;
import tech.stin.trappinncrappin.data.Player;
import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/16/2017.
 */

public class CustomerPageView extends Fragment {
    private static final String TAG = CustomerPageView.class.getSimpleName();

    private RecyclerView cvRecycler;
    private TextView tCusName;
    private TextView tCusMessage;
    private Dealer curCustomer;

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
            cvRecycler.setAdapter(new CustomerDemandAdapter(curCustomer.getStash()));
        }
        return view;
    }

    // should be called as of now
    public void setCustomer(Dealer customer) {
        curCustomer = customer;
    }

    private class CustomerDemandAdapter extends RecyclerView.Adapter<CustomerDemandHolder>
            implements CustomerDemandHolder.CustomerDemandListener {
        private final String TAG = CustomerDemandAdapter.class.getSimpleName();

        private int selectedPos = 0;
        private Stash mDemand;

        CustomerDemandAdapter(Stash stash) {
            mDemand = stash;
        }

        @Override
        public void sellDrug(String drug) {
            SessionManager session = new SessionManager(getActivity());
            Player player = session.getCurrentPlayer();
            if (curCustomer.takeMoney(1)) {
                player.sellItem(drug, 1);
                player.addMoney(1);
                session.addPlayer(player);
                Log.d(TAG, "actually sold");
            }
        }

        @Override
        public CustomerDemandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.customer_demand_recycler_item, parent, false);
            return new CustomerDemandHolder(view, this);
        }

        @Override
        public void onBindViewHolder(CustomerDemandHolder holder, final int position) {
            if (mDemand != null) {
                try {
                    // for highlighting
                    if (selectedPos == position) {
                        holder.itemView.setBackground(getResources().getDrawable(R.drawable.line));
                        holder.itemView.setPadding(8, 8, 8, 8);
                    } else {
                        holder.itemView.setBackgroundResource(0);
                        holder.itemView.setPadding(8, 8, 8, 8);
                    }
                    String key = DrugConfig.getKey(position);
                    int value = mDemand.getStash().get(key);

                    holder.bindDemand(key, value);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notifyItemChanged(selectedPos);
                            selectedPos = position;
                            notifyItemChanged(selectedPos);
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
            if (mDemand != null) {
                return mDemand.getStash().size();
            }
            return 0;
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Customer Page destroyed");
        super.onDestroy();
    }
}
