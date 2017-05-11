package tech.stin.trappinncrappin.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.app.SessionManager;
import tech.stin.trappinncrappin.data.Player;

/**
 * Created by Austin on 5/9/2017.
 */

public class HomeView extends Fragment {
    private static final String TAG = HomeView.class.getSimpleName();

    private TextView tNameView;

    private SessionManager session;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        session = new SessionManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tNameView = (TextView) view.findViewById(R.id.name_text_view);
        setNameText();

        return view;
    }

    void setNameText() {
        Player player = session.getCurrentPlayer();
        if (player != null) {
            tNameView.setText(player.getName());
        }
    }
}
