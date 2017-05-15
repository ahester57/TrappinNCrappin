package tech.stin.trappinncrappin.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.data.Dealer;

/**
 * Created by Austin on 5/14/2017.
 */

public class DealerPageView extends Fragment {
    private static final String TAG = DealerPageView.class.getSimpleName();

    private RecyclerView dsRecycler;
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

        dsRecycler = (RecyclerView) view.findViewById(R.id.dealer_stock_recycler);
        dsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    void setDealer(Dealer dealer) {

    }
}
