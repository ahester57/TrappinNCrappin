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
import tech.stin.trappinncrappin.activity.helper.DealerHolder;
import tech.stin.trappinncrappin.app.SessionManager;
import tech.stin.trappinncrappin.data.Dealer;

/**
 * Created by Austin on 5/11/2017.
 *
 * View Fragment for Dealer ('contact') screen
 *
 */

public class DealerView extends Fragment {
    private static final String TAG = DealerView.class.getSimpleName();

    private Dealer curDealer;
    private int dPosition;

    private RecyclerView dRecycler;
    private Button bGoToDealer;
    private AdView mAdView;
    private DealerListener dListener;
    private SessionManager session;

    interface DealerListener {
        void goToDealerPage(Dealer dealer);
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
        View view = inflater.inflate(R.layout.fragment_dealer, container, false);
        dListener = (DealerListener) getActivity();
        session = new SessionManager(getActivity());

        mAdView = (AdView) view.findViewById(R.id.adview_dealers);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        bGoToDealer = (Button) view.findViewById(R.id.button_view_dealer);
        bGoToDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dListener != null && curDealer != null) {
                    dListener.goToDealerPage(curDealer);
                    Log.d(TAG, "Dealer visited: " + curDealer.toString());
                }
            }
        });

        dRecycler = (RecyclerView) view.findViewById(R.id.dealer_list_recycler);
        dRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        // @ TODO fix this resetting all the time
        ArrayList<Dealer> dealers = session.getCurrentDealers();
        if (dealers == null) {
            for (int i = 0; i < 5; i++) {
                session.addDealer(new Dealer(true));
            }
            dealers = session.getCurrentDealers();
        }
        dRecycler.setAdapter(new DealerAdapter(dealers, dPosition));

        return view;
    }

    private class DealerAdapter extends RecyclerView.Adapter<DealerHolder> {
        private final String TAG = DealerView.DealerAdapter.class.getSimpleName();

        private int selectedPos = 0;
        private List<Dealer> mDealers;

        DealerAdapter(List<Dealer> dealers, int pos) {
            mDealers = dealers;
            selectedPos = pos;
            setSelectedDealer(pos);
        }

        @Override
        public DealerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.dealer_recycler_item, parent, false);
            return new DealerHolder(view);
        }

        private void setSelectedDealer(int pos) {
            if (mDealers != null) {
                curDealer = mDealers.get(pos);
                dPosition = pos;
                Log.d(TAG, "Dealer selected: " + curDealer.toString());
            }
        }

        @Override
        public void onBindViewHolder(DealerHolder holder, final int position) {
            if (mDealers != null) {
                try {
                    // for highlighting
                    if (selectedPos == position) {
                        holder.itemView.setBackground(getResources().getDrawable(R.drawable.line));
                        holder.itemView.setPadding(8, 8, 8, 8);
                    } else {
                        holder.itemView.setBackgroundResource(0);
                        holder.itemView.setPadding(8, 8, 8, 8);
                    }

                    holder.bindDealer(mDealers.get(position));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notifyItemChanged(selectedPos);
                            selectedPos = position;
                            notifyItemChanged(selectedPos);
                            setSelectedDealer(position);
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
            if (mDealers != null) {
                return mDealers.size();
            }
            return 0;
        }
    }
}
