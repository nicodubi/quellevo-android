package com.quellevo.quellevo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.quellevo.quellevo.R;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.GetAllEvents;
import com.quellevo.quellevo.events.GetAllEventsSuccess;
import com.quellevo.quellevo.events.PostFirebaseTokenEvent;
import com.quellevo.quellevo.login.LoginActivity;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.CustomSharedPreferences;
import com.quellevo.quellevo.utils.adapters.DividerItemDecoration;
import com.quellevo.quellevo.utils.adapters.EventsRecyclerAdapter;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.FirebaseRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicolas on 19/6/2017.
 */

public class HomeScreenActivity extends AbstractActivity {

    @BindView(R.id.recycler_provider)
    RecyclerView recyclerView;

    private EventsRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.homescreen_activity);
        ButterKnife.bind(this);
        adapter = new EventsRecyclerAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_recycler_view));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerOnEventBus(this);
        postFirebaseToken();
        getEvents();

    }

    private void postFirebaseToken() {
        if(User.getInstance().getFirebaseToken()!=null){
            EventBus.getDefault().post(new PostFirebaseTokenEvent(new FirebaseRequest(User.getInstance().getFirebaseToken())));
        }
    }


    @Subscribe
    public void getEventsSuccess(GetAllEventsSuccess event) {
        ArrayList<Event> response = event.getEvents();
        if (response != null && !response.isEmpty()) {
            adapter.setEvents(response);
        } else {
            Toast.makeText(this, R.string.no_events, Toast.LENGTH_SHORT).show();
        }
        stopDialog();
    }

    private void getEvents() {
        EventBus.getDefault().post(new GetAllEvents());
        showProgressDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerOnEventBus(this);
    }

    private void logout() {
        CustomSharedPreferences sharedPreferences = new CustomSharedPreferences(this);
        sharedPreferences.removeUserCredentials();
        User.getInstance().cleanInfo();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
