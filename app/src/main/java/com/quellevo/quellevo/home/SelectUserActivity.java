package com.quellevo.quellevo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quellevo.quellevo.R;
import com.quellevo.quellevo.entities.User;
import com.quellevo.quellevo.events.GetAllUsersEvent;
import com.quellevo.quellevo.events.GetAllUsersEventSuccess;
import com.quellevo.quellevo.login.LoginActivity;
import com.quellevo.quellevo.utils.AbstractActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.utils.CustomSharedPreferences;
import com.quellevo.quellevo.utils.adapters.DividerItemDecoration;
import com.quellevo.quellevo.utils.adapters.SelectGuestRecyclerAdapter;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicolas on 25/6/2017.
 */

public class SelectUserActivity extends AbstractActivity {


    @BindView(R.id.recycler_provider)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.provider_select_done_button_id)
    TextView doneButton;

    private SelectGuestRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachRoot(R.layout.select_guest_activity);
        ButterKnife.bind(this);
        adapter = new SelectGuestRecyclerAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_recycler_view));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerOnEventBus(this);
        getGuests();

    }


    @OnClick(R.id.provider_select_done_button_id)
    public void doneClick() {
        ArrayList<UserEvent> selectedGuests = adapter.getSelectedUserEvent();
        if(selectedGuests!=null && !selectedGuests.isEmpty()){
            Intent data = new Intent();
            Gson gson = new Gson();
            String arrayString  = gson.toJson(selectedGuests);
            data.putExtra(Constants.SELECTED_GUESTS_ARRAY_KEY,arrayString);
            setResult(RESULT_OK,data);
            finish();
        }else{
            Toast.makeText(SelectUserActivity.this,R.string.select_one_guest,Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void getAllUsersEventSuccess(GetAllUsersEventSuccess event) {
        ArrayList<UserEvent> response = event.getUsers();
        if (response != null && !response.isEmpty()) {
            adapter.setUserEvents(response);
        } else {
            Toast.makeText(this, R.string.no_guests, Toast.LENGTH_SHORT).show();
        }
        stopDialog();
    }

    private void getGuests() {
        EventBus.getDefault().post(new GetAllUsersEvent());
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
