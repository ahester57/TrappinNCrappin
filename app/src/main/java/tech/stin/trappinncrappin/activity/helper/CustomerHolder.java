package tech.stin.trappinncrappin.activity.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.data.Customer;

/**
 * Created by Austin on 5/16/2017.
 */

public class CustomerHolder extends RecyclerView.ViewHolder {
    private final String TAG = CustomerHolder.class.getSimpleName();
    private TextView tCustomerName;
    private TextView tCustomerCaption;
    private TextView tCustomerMessage;

    public CustomerHolder(View itemView) {
        super(itemView);
        tCustomerName = (TextView) itemView.findViewById(R.id.text_customer_name);
        tCustomerCaption = (TextView) itemView.findViewById(R.id.text_customer_caption);
        tCustomerMessage = (TextView) itemView.findViewById(R.id.text_customer_message);
    }

    public void bindDealer(Customer customer) {
        tCustomerName.setText(customer.getName());
        String money = "Money: $" + customer.getMoney();
        tCustomerCaption.setText(money);
        tCustomerMessage.setText(customer.getMessage());
    }

}
