package tech.stin.trappinncrappin.activity;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.activity.helper.StashHolder;
import tech.stin.trappinncrappin.app.DrugConfig;
import tech.stin.trappinncrappin.app.SessionManager;
import tech.stin.trappinncrappin.data.Player;
import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/9/2017.
 */

public class HomeView extends Fragment {
    private static final String TAG = HomeView.class.getSimpleName();

    private TextView tNameView;
    private TextView tMessageView;
    private TextView tPlayerMoney;
    private RecyclerView qRecyclerView;

    private SessionManager session;
    private WeakReference<HomeListener> hListener;

    interface HomeListener {
        void goToDealers();
        void goToStreets();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        session = new SessionManager(getActivity());
        hListener = new WeakReference<>((HomeListener) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        tNameView = (TextView) view.findViewById(R.id.name_text_view);
        tMessageView = (TextView) view.findViewById(R.id.message_text_view);
        tPlayerMoney = (TextView) view.findViewById(R.id.text_player_money);

        qRecyclerView = (RecyclerView) view.findViewById(R.id.stash_list_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        qRecyclerView.setLayoutManager(layoutManager);

        Button temp = (Button) view.findViewById(R.id.groups_button);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = session.getCurrentPlayer();
                player.addItem(DrugConfig.LSD, 10);
                session.addPlayer(player);
                setStashAdapter(player.getStash());
            }
        });

        Button dealers = (Button) view.findViewById(R.id.dealers_button);
        Button streets = (Button) view.findViewById(R.id.streets_button);
        dealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hListener.get().goToDealers();
            }
        });
        streets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hListener.get().goToStreets();
            }
        });


        Player player = session.getCurrentPlayer();
        if (player != null) {
            setPlayerText(player);
            setStashAdapter(player.getStash());
        } else {
            newPlayerDialog();
        }

        Log.d(TAG, "Home view onCreateView");
        return view;
    }

    void setPlayerText(Player player) {
        if (tNameView != null && tMessageView != null && tPlayerMoney != null) {
            tNameView.setText(player.getName());
            tMessageView.setText(player.getMessage());
            String money = "Money: $" + player.getMoney();
            tPlayerMoney.setText(money);
        }
    }

    void setStashAdapter(Stash stash) {
        if (qRecyclerView != null) {
            qRecyclerView.setAdapter(new StashAdapter(stash));
        }
    }

    private void newPlayerDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_token_input, null);
        final EditText tokenIn = (EditText) dialogView.findViewById(R.id.player_name_input);
        new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth)
                .setTitle(Html.fromHtml("<h2>New Player</h2>"))
                .setMessage(Html.fromHtml("Enter your name" +
                        ": "))
                .setView(dialogView)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tokenIn != null) {
                            String token = tokenIn.getText().toString();
                            if (token.length() >= 4) {
                                Player player = new Player(token, "message");
                                session.addPlayer(player);
                                if (player != null) {
                                    setPlayerText(player);
                                    setStashAdapter(player.getStash());
                                }
                            } else {
                                newPlayerDialog();
                                Toast.makeText(getActivity(),
                                        "Name must be greater than 4 characters.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            Log.d(TAG, "Quiz token input: " + token);
                        }
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_menu_directions)
                .show();
    }


    private class StashAdapter extends RecyclerView.Adapter<StashHolder> {
        private final String TAG = StashAdapter.class.getSimpleName();

        private int selectedPos = 0;
        private Stash mStash;

        StashAdapter(Stash stash) {
            mStash = stash;
        }

        @Override
        public StashHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.stash_recycler_item, parent, false);
            return new StashHolder(view);
        }

        @Override
        public void onBindViewHolder(StashHolder holder, final int position) {
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
