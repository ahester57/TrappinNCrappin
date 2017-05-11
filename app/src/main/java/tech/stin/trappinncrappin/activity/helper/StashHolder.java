package tech.stin.trappinncrappin.activity.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tech.stin.trappinncrappin.R;

/**
 * Created by Austin on 5/11/2017.
 */

public class StashHolder extends RecyclerView.ViewHolder {
    private final String TAG = StashHolder.class.getSimpleName();
    private TextView tDrugName;
    private TextView tDrugValue;
    private TextView tDrugInfo;
    private TextView tDrugCaption;

    public StashHolder(View itemView) {
        super(itemView);
        tDrugName = (TextView) itemView.findViewById(R.id.text_drug_name);
        tDrugValue = (TextView) itemView.findViewById(R.id.text_drug_amount);
        tDrugInfo = (TextView) itemView.findViewById(R.id.text_drug_info);
        tDrugCaption = (TextView) itemView.findViewById(R.id.text_drug_caption);
    }

    public void bindStash(String key, int value) {
        tDrugName.setText(key);
        tDrugValue.setText(String.valueOf(value));
    }
}
