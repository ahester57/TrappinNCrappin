package tech.stin.trappinncrappin.activity.helper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tech.stin.trappinncrappin.R;

/**
 * Created by Austin on 5/16/2017.
 */

public class DealerStashHolder extends RecyclerView.ViewHolder {
    private static final String TAG = DealerStashHolder.class.getSimpleName();
    private TextView tDrugName;
    private TextView tDrugValue;
    private TextView tDrugInfo;
    private TextView tDrugCaption;
    private Button bBuyDrug;
    private DealerStashListener dsListener;

    public interface DealerStashListener {
        void buyDrug(String drug);
    }

    public DealerStashHolder(View itemView, DealerStashListener dsl) {
        super(itemView);
        dsListener = dsl;
        tDrugName = (TextView) itemView.findViewById(R.id.text_drug_name);
        tDrugValue = (TextView) itemView.findViewById(R.id.text_drug_amount);
        tDrugInfo = (TextView) itemView.findViewById(R.id.text_drug_info);
        tDrugCaption = (TextView) itemView.findViewById(R.id.text_drug_caption);
        bBuyDrug = (Button) itemView.findViewById(R.id.button_buy_drug);
    }

    public void bindStash(final String key, int value) {
        tDrugName.setText(key);
        tDrugValue.setText(String.valueOf(value));
        tDrugInfo.setText("Price: " + "$???");
        tDrugCaption.setText("Get it while it's hot");
        bBuyDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dsListener != null) {
                    dsListener.buyDrug(key);
                    Log.d(TAG, "Bought: " + key);
                }
            }
        });
    }
}
