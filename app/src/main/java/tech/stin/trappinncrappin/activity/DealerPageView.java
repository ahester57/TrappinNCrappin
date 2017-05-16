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
import android.widget.TextView;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.activity.helper.DealerStashHolder;
import tech.stin.trappinncrappin.app.DrugConfig;
import tech.stin.trappinncrappin.app.SessionManager;
import tech.stin.trappinncrappin.data.Dealer;
import tech.stin.trappinncrappin.data.Player;
import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/14/2017.
 */

public class DealerPageView extends Fragment {
    private static final String TAG = DealerPageView.class.getSimpleName();

    private RecyclerView dsRecycler;
    private TextView tDealerName;
    private TextView tDealerMessage;
    private Dealer curDealer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dealer_page, container, false);

        tDealerName = (TextView) view.findViewById(R.id.text_dealer_page_name);
        tDealerMessage = (TextView) view.findViewById(R.id.text_dealer_page_message);

        dsRecycler = (RecyclerView) view.findViewById(R.id.dealer_stock_recycler);
        dsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (curDealer != null) {
            tDealerName.setText(curDealer.getName());
            tDealerMessage.setText(curDealer.getMessage());
            dsRecycler.setAdapter(new DealerStashAdapter(curDealer.getStash()));
        }

        return view;
    }

    // should be called as of now
    void setDealer(Dealer dealer) {
        curDealer = dealer;
    }

    private class DealerStashAdapter extends RecyclerView.Adapter<DealerStashHolder>
            implements DealerStashHolder.DealerStashListener {
        private final String TAG = DealerPageView.DealerStashAdapter.class.getSimpleName();

        private int selectedPos = 0;
        private Stash mStash;

        DealerStashAdapter(Stash stash) {
            mStash = stash;
        }

        @Override
        public void buyDrug(String drug) {
            SessionManager session = new SessionManager(getActivity());
            Player player = session.getCurrentPlayer();
            if (curDealer.sellItem(drug, 1)) {
                player.addItem(drug, 1);
                session.addPlayer(player);
                Log.d(TAG, "actually sold");
            }
        }

        @Override
        public DealerStashHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.dealer_stash_recycler_item, parent, false);
            return new DealerStashHolder(view, this);
        }

        @Override
        public void onBindViewHolder(DealerStashHolder holder, final int position) {
            if (mStash != null) {
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
                    int value = mStash.getStash().get(key);

                    holder.bindStash(key, value);
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
            if (mStash != null) {
                return mStash.getStash().size();
            }
            return 0;
        }
    }
}
