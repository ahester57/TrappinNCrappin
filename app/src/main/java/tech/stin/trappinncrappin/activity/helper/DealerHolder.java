package tech.stin.trappinncrappin.activity.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.data.Dealer;

/**
 * Created by Austin on 5/14/2017.
 */

public class DealerHolder extends RecyclerView.ViewHolder {
    private final String TAG = DealerHolder.class.getSimpleName();
    private TextView tDealerName;
    private TextView tDealerNumber;
    private TextView tDealerMessage;

    public DealerHolder(View itemView) {
        super(itemView);
        tDealerName = (TextView) itemView.findViewById(R.id.text_dealer_name);
        tDealerNumber = (TextView) itemView.findViewById(R.id.text_dealer_number);
        tDealerMessage = (TextView) itemView.findViewById(R.id.text_dealer_message);
    }

    public void bindDealer(Dealer dealer) {
        tDealerName.setText(dealer.getName());
        tDealerNumber.setText(dealer.getStash().toString());
        tDealerMessage.setText(dealer.getMessage());
    }
}
