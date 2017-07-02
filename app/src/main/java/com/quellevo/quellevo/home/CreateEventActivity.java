package com.quellevo.quellevo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quellevo.quellevo.R;
import com.quellevo.quellevo.events.CreateEventSuccess;
import com.quellevo.quellevo.events.GetAnEvent;
import com.quellevo.quellevo.events.GetEventListSuccess;
import com.quellevo.quellevo.events.GetItemsList;
import com.quellevo.quellevo.events.PostNewUsertToEventSuccess;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.utils.adapters.DividerItemDecoration;
import com.quellevo.quellevo.utils.adapters.GuestInvitedAdapter;
import com.quellevo.quellevo.utils.adapters.ListViewAdapter;
import com.quellevo.quellevo.web_services.rest_entities.CreateEventRequest;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.EventItem;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class CreateEventActivity extends AbstractActivity {
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
    @BindView(R.id.save_info_event_id)
    TextView saveInfo;
    @BindView(R.id.event_name_id)
    EditText eventNameEditText;
    @BindView(R.id.items_spinner_id)
    Spinner spinnerItems;
    @BindView(R.id.items_list_view_id)
    ListView listItems;

    private ArrayList<EventItem> possibleItemsToSelect;
    private ArrayList<EventItem> itemsSelectedByUser;
    private int checkSpinnerCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.create_event_activity);
        ButterKnife.bind(this);
        registerOnEventBus(this);
        possibleItemsToSelect = new ArrayList<>();
        checkSpinnerCount = 0;
        itemsSelectedByUser = new ArrayList<>();

        adapter = new GuestInvitedAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_recycler_view));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        guests = new ArrayList<>();
        adapter.setGusets(guests,null);
        initListAdapter();
        getPossibleItems();
    }

    private void initListAdapter() {
        listItems.setAdapter(new ListViewAdapter<EventItem>(this, android.R.layout.simple_dropdown_item_1line, itemsSelectedByUser));
    }

    private void getPossibleItems() {
        showProgressDialog();
        EventBus.getDefault().post(new GetItemsList());
    }


    @Subscribe
    public void getItemsFromBackend(GetEventListSuccess event) {
        possibleItemsToSelect = event.getEventItems();
        possibleItemsToSelect.add(0, new EventItem("Select Item..."));
        stopDialog();
        loadItems();
    }


    private void loadItems() {
        ArrayAdapter<EventItem> adapter = new ArrayAdapter<EventItem>(
                this, android.R.layout.simple_dropdown_item_1line, possibleItemsToSelect);
        spinnerItems.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EventItem selected = (EventItem) adapterView.getAdapter().getItem(i);
                if (selected != null && checkSpinnerCount > 0) {
                    if (i != 0) {
                        ((ListViewAdapter) listItems.getAdapter()).add(selected);
                        spinnerItems.setSelection(0);
                    }
                }
                ++checkSpinnerCount;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
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


    @OnClick(R.id.save_info_event_id)
    public void clickSaveInfo() {
        if (!eventNameEditText.getText().toString().isEmpty()) {
            showProgressDialog();
            CreateEventRequest request = new CreateEventRequest(eventNameEditText.getText().toString(), new Date("17/07/2017"), guests, itemsSelectedByUser);
            EventBus.getDefault().post(request);
        } else {
            Toast.makeText(this, R.string.complete_event_name, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void createEventSuccess(CreateEventSuccess eventSuccess) {
        stopDialog();
        Gson gson = new Gson();
        String eventString = gson.toJson(eventSuccess.getEvent());
        Intent intent = new Intent();
        intent.putExtra(Constants.NEW_EVENT_CREATED_KEY, eventString);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Subscribe
    public void postAddUsersSuccess(PostNewUsertToEventSuccess eventSuccess) {
        stopDialog();
        Toast.makeText(this, R.string.event_saved_successfully, Toast.LENGTH_SHORT).show();

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
