package com.quellevo.quellevo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quellevo.quellevo.R;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.GetAnEvent;
import com.quellevo.quellevo.events.GetAnEventSuccess;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.utils.adapters.DividerItemDecoration;
import com.quellevo.quellevo.utils.adapters.EventsRecyclerAdapter;
import com.quellevo.quellevo.utils.adapters.GuestInvitedAdapter;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;
import com.quellevo.quellevo.web_services.rest_entities.UserEventResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class EventInfoActivity extends AbstractActivity {
    private Long eventId;
    private Event event;
    private GuestInvitedAdapter adapter;

    @BindView(R.id.recycler_provider)
    RecyclerView recyclerView;
    private ArrayList<UserEvent> guests;

    @BindView(R.id.event_title_id)
    TextView eventTitle;
    @BindView(R.id.event_date_id)
    TextView eventDate;
    @BindView(R.id.add_person_button_id)
    ImageView addPerson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.event_info_activity);
        ButterKnife.bind(this);
        registerOnEventBus(this);
        adapter = new GuestInvitedAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_recycler_view));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        guests = new ArrayList<>();
        getEventId();
    }

    private void getEventId() {
        eventId = getIntent().getLongExtra(Constants.EVENT_ID_KEY, -1);
        if (eventId == -1) {
            Toast.makeText(this, R.string.default_error_event, Toast.LENGTH_SHORT).show();
        } else {
            getEventInfo();
        }
    }

    @OnClick(R.id.add_person_button_id)
    public void addPersonClick() {
        Intent intent = new Intent(this, SelectUserActivity.class);
        startActivityForResult(intent, Constants.SELECT_GUESTS_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SELECT_GUESTS_KEY && resultCode == RESULT_OK) {
            Gson gson = new Gson();
            ArrayList<UserEvent> guestsSelected = gson.fromJson(data.getStringExtra(Constants.SELECTED_GUESTS_ARRAY_KEY), new TypeToken<ArrayList<UserEvent>>() {
            }.getType());
            addNewGuests(guestsSelected);

        }
    }

    private void addNewGuests(ArrayList<UserEvent> guestsSelected) {
        for (UserEvent userToAdd : guestsSelected) {
            if (!userToAddIsAlreadyAdded(userToAdd)) {
                guests.add(userToAdd);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private boolean userToAddIsAlreadyAdded(UserEvent userToAdd) {
        for (UserEvent user : guests) {
            if (user.getId().equals(userToAdd.getId())) {
                return true;
            }
        }
        return false;
    }

    private void getEventInfo() {
        showProgressDialog();
        EventBus.getDefault().post(new GetAnEvent(eventId));
    }

    @Subscribe
    public void getAnEventSuccess(GetAnEventSuccess eventSuccess) {
        this.event = eventSuccess.getEvent();
        initValues();
        stopDialog();
    }

    private void initValues() {
        eventTitle.setText(event.getName());
        eventDate.setText(event.getDateString());
        for (UserEventResponse userEventResponse : event.getEvent_users()) {
            guests.add(userEventResponse.getUser());
        }
        adapter.setGusets(guests);
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


}
