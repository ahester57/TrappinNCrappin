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

public class CustomerDemandHolder extends RecyclerView.ViewHolder {
    private static final String TAG = CustomerDemandHolder.class.getSimpleName();
    private TextView tDrugName;
    private TextView tDrugValue;
    private TextView tDrugInfo;
    private TextView tDrugCaption;
    private Button bSellDrug;
    private CustomerDemandListener cdListener;

    public interface CustomerDemandListener {
        void sellDrug(String drug);
    }

    public CustomerDemandHolder(View itemView, CustomerDemandListener cdl) {
        super(itemView);
        cdListener = cdl;
        tDrugName = (TextView) itemView.findViewById(R.id.text_demand_drug_name);
        tDrugValue = (TextView) itemView.findViewById(R.id.text_demand_drug_amount);
        tDrugInfo = (TextView) itemView.findViewById(R.id.text_demand_drug_info);
        tDrugCaption = (TextView) itemView.findViewById(R.id.text_demand_drug_caption);
        bSellDrug = (Button) itemView.findViewById(R.id.button_demand_sell_drug);
    }

    public void bindDemand(final String key, int value) {
        tDrugName.setText(key);
        tDrugValue.setText(String.valueOf(value));
        tDrugInfo.setText("Asking: " + "$???");
        tDrugCaption.setText("Please?????");
        bSellDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cdListener != null) {
                    cdListener.sellDrug(key);
                    Log.d(TAG, "Bought: " + key);
                }
            }
        });
    }
}
