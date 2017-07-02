package com.quellevo.quellevo.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quellevo.quellevo.R;
import com.quellevo.quellevo.events.AssignItemEvent;
import com.quellevo.quellevo.events.BuyItemEventRequest;
import com.quellevo.quellevo.events.DeleteEventItemSuccess;
import com.quellevo.quellevo.events.DeleteEventRequest;
import com.quellevo.quellevo.events.UnassignItemEvent;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.utils.adapters.DividerItemDecoration;
import com.quellevo.quellevo.utils.adapters.GuestInvitedAdapter;
import com.quellevo.quellevo.utils.adapters.ListViewAdapter;
import com.quellevo.quellevo.web_services.rest_entities.AssignItemBody;
import com.quellevo.quellevo.web_services.rest_entities.AssignItemEventSuccess;
import com.quellevo.quellevo.web_services.rest_entities.BuyItemRequestBody;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.EventItem;
import com.quellevo.quellevo.web_services.rest_entities.UnassingItemEventSuccess;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicolas on 1/7/2017.
 */

public class GuestViewActivity extends AbstractActivity {

    @BindView(R.id.guest_name_title_id)
    TextView guestTitle;

    private UserEvent user;

    @BindView(R.id.save_info_event_id)
    TextView saveInfo;
    @BindView(R.id.items_list_view_id)
    ListView listBoughtItems;
    @BindView(R.id.added_items_list_view_id)
    ListView listPendingBoughtItems;
    @BindView(R.id.possible_items_to_assing_list_id)
    ListView possibleItemsForAssingList;

    private ArrayList<EventItem> boughtItems;
    private ArrayList<EventItem> itemsPendingToBought;
    private ArrayList<EventItem> itemsForAssign;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.guest_activity);
        ButterKnife.bind(this);
        registerOnEventBus(this);
        boughtItems = new ArrayList<>();
        itemsForAssign = new ArrayList<>();
        itemsPendingToBought = new ArrayList<>();
        getGuest();
        getItemsToAssign();
        initValue();

    }

    @OnClick(R.id.save_info_event_id)
    public void clickSave() {

    }

    private void getItemsToAssign() {
        Gson gson = new Gson();
        itemsForAssign = gson.fromJson(getIntent().getStringExtra(Constants.ITEMS_TO_ASSIGN_KEY), new TypeToken<ArrayList<EventItem>>() {
        }.getType());

    }


    private void initListAdapter() {
        ListViewAdapter<EventItem> listBoughtItemsAdapter = new ListViewAdapter<EventItem>(this, android.R.layout.simple_dropdown_item_1line, boughtItems);
        ListViewAdapter<EventItem> listPendingBoughtItemsAdapter = new ListViewAdapter<EventItem>(this, android.R.layout.simple_dropdown_item_1line, itemsPendingToBought);
        ListViewAdapter<EventItem> possibleItemsForAssingListAdapter = new ListViewAdapter<EventItem>(this, android.R.layout.simple_dropdown_item_1line, itemsForAssign);

        listBoughtItemsAdapter.configElements(false, null, null);
        listPendingBoughtItemsAdapter.configElements(true, new ListViewAdapter.ClickDeleteItem<EventItem>() {
            @Override
            public void onItemClickDeleted(EventItem item) {
                unassignItem(item);
            }
        }, new ListViewAdapter.ClickTickItem<EventItem>() {
            @Override
            public void onItemClickTick(final EventItem item) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GuestViewActivity.this);
                View view = LayoutInflater.from(GuestViewActivity.this).inflate(R.layout.dialog_buy_item, listBoughtItems, false);
                dialogBuilder.setTitle("Confirm buy").setCancelable(true).
                        setView(R.layout.dialog_buy_item);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                final EditText costText = (EditText) alertDialog.findViewById(R.id.cost_item_bought_id);
                TextView buyButton = (TextView) alertDialog.findViewById(R.id.buy_button_id);
                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (costText.getText() != null && !costText.getText().toString().isEmpty()) {
                            alertDialog.dismiss();
                            item.setBought(true);
                            item.setCost(Float.valueOf(costText.getText().toString()));
                            buyItem(item);
                            ((ListViewAdapter) listBoughtItems.getAdapter()).add(item);
                            ((ListViewAdapter) listPendingBoughtItems.getAdapter()).remove(item);
                        } else {
                            Toast.makeText(GuestViewActivity.this, "complete cost!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        possibleItemsForAssingListAdapter.configElements(true, new ListViewAdapter.ClickDeleteItem<EventItem>() {
            @Override
            public void onItemClickDeleted(EventItem item) {
                deleteItemOfEvent(item);
            }
        }, new ListViewAdapter.ClickTickItem<EventItem>() {
            @Override
            public void onItemClickTick(EventItem item) {
                assignNewItemToUser(item);
            }
        });

        listBoughtItems.setAdapter(listBoughtItemsAdapter);
        listPendingBoughtItems.setAdapter(listPendingBoughtItemsAdapter);
        possibleItemsForAssingList.setAdapter(possibleItemsForAssingListAdapter);
    }

    private void unassignItem(EventItem item) {
        showProgressDialog();
        EventBus.getDefault().post(new UnassignItemEvent(item));
    }


    @Subscribe
    public void unassingItemSuccess(UnassingItemEventSuccess event) {
        ((ListViewAdapter) listPendingBoughtItems.getAdapter()).remove(event.getEventItem());
        ((ListViewAdapter) possibleItemsForAssingList.getAdapter()).add(event.getEventItem());
        stopDialog();
    }

    private void assignNewItemToUser(EventItem item) {
        showProgressDialog();
        EventBus.getDefault().post(new AssignItemEvent(item, new AssignItemBody(user.getId())));
    }

    @Subscribe
    public void assingItemSuccess(AssignItemEventSuccess event) {
        ((ListViewAdapter) listPendingBoughtItems.getAdapter()).add(event.getEventItem());
        ((ListViewAdapter) possibleItemsForAssingList.getAdapter()).remove(event.getEventItem());
        stopDialog();
    }

    private void buyItem(EventItem item) {
        EventBus.getDefault().post(new BuyItemEventRequest(new BuyItemRequestBody(item.getCost()), item.getId()));
    }

    private void deleteItemOfEvent(EventItem item) {
        showProgressDialog();
        EventBus.getDefault().post(new DeleteEventRequest(item.getEvent_id(), item.getId()));
    }

    @Subscribe
    public void deleteItemSuccess(DeleteEventItemSuccess event) {
        Toast.makeText(this, "Item deleted from event successfuly", Toast.LENGTH_SHORT).show();
        stopDialog();
    }

    private void initValue() {
        guestTitle.setText(user.getName() + " " + user.getSurname());
        if (user.getEvent_items() != null) {
            for (EventItem eventItem : user.getEvent_items()) {
                if (eventItem.getBought()) {
                    boughtItems.add(eventItem);
                } else {
                    itemsPendingToBought.add(eventItem);
                }
            }
        }
        initListAdapter();
        //     notifyDataSetChangedForEveryLists();
    }

    private void notifyDataSetChangedForEveryLists() {
        ((ListViewAdapter) listPendingBoughtItems.getAdapter()).notifyDataSetChanged();
        ((ListViewAdapter) listBoughtItems.getAdapter()).notifyDataSetChanged();
        ((ListViewAdapter) possibleItemsForAssingList.getAdapter()).notifyDataSetChanged();
    }

    private void getGuest() {
        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra(Constants.GUEST_INFO_KEY), UserEvent.class);
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
