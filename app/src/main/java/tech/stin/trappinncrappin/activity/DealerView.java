package tech.stin.trappinncrappin.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.activity.helper.DealerHolder;
import tech.stin.trappinncrappin.data.Dealer;

/**
 * Created by Austin on 5/11/2017.
 */

public class DealerView extends Fragment {
    private static final String TAG = DealerView.class.getSimpleName();

    private RecyclerView dRecycler;


    interface DealerListener {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dealer, container, false);

        dRecycler = (RecyclerView) view.findViewById(R.id.dealer_list_recycler);


        return view;
    }

    private class DealerAdapter extends RecyclerView.Adapter<DealerHolder> {
        private final String TAG = DealerView.DealerAdapter.class.getSimpleName();

        private int selectedPos = 0;
        private List<Dealer> mDealers;

        DealerAdapter(List<Dealer> dealers) {
            mDealers = dealers;
        }

        @Override
        public DealerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.dealer_recycler_item, parent, false);
            return new DealerHolder(view);
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
