package com.quellevo.quellevo.utils.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.quellevo.quellevo.R;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicolas on 17/5/2017.
 */

public class SelectGuestRecyclerAdapter extends RecyclerView.Adapter<SelectGuestRecyclerAdapter.GuestsViewHolder> {

    private ArrayList<UserEvent> userEvents;
    private ArrayList<UserEvent> selectedUserEvent;


    public SelectGuestRecyclerAdapter() {
        selectedUserEvent = null;
        selectedUserEvent = new ArrayList<>();
    }

    public ArrayList<UserEvent> getSelectedUserEvent() {
        return selectedUserEvent;
    }

    public void setUserEvents(ArrayList<UserEvent> userEvents) {
        this.userEvents = userEvents;
        notifyDataSetChanged();
    }

    @Override
    public GuestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guests_view_list, parent, false);
        GuestsViewHolder userEventViewHolder = new GuestsViewHolder(view, new GuestsViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(GuestsViewHolder view, int position) {
                if(selectedUserEvent.contains(userEvents.get(position))){
                    view.tickSelected.setVisibility(View.INVISIBLE);
                    selectedUserEvent.remove(userEvents.get(position));
                }else{
                    view.tickSelected.setVisibility(View.VISIBLE);
                    selectedUserEvent.add(userEvents.get(position));
                }
            }
        });
        return userEventViewHolder;
    }

    private boolean userIsAlreadySelected(UserEvent userEvent) {
        for(UserEvent user : selectedUserEvent){
            if(user.getId().equals(userEvent.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBindViewHolder(GuestsViewHolder holder, int position) {
        UserEvent userEvent = userEvents.get(position);
        holder.title.setText(userEvent.getName());
        holder.city.setText(userEvent.getSurname());
    }

    @Override
    public int getItemCount() {
        return (userEvents != null) ? userEvents.size() : 0;
    }

    public static class GuestsViewHolder extends RecyclerView.ViewHolder {

        private ItemClickListener listener;
        @BindView(R.id.title_provider)
        public TextView title;
        @BindView(R.id.city_provider_id)
        public TextView city;
        @BindView(R.id.tick_selected)
        public ImageView tickSelected;

        public GuestsViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GuestsViewHolder.this.listener != null) {
                        GuestsViewHolder.this.listener.onItemClick(GuestsViewHolder.this, getAdapterPosition());
                    }
                }
            });
        }


        interface ItemClickListener {
            void onItemClick(GuestsViewHolder view, int position);
        }
    }

}
