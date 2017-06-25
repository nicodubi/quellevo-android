package com.quellevo.quellevo.utils.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quellevo.quellevo.R;
import com.quellevo.quellevo.home.EventInfoActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.web_services.rest_entities.Event;
import com.quellevo.quellevo.web_services.rest_entities.UserEvent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicolas on 25/6/2017.
 */


public class GuestInvitedAdapter extends RecyclerView.Adapter<GuestInvitedAdapter.GuestsInvitedViewHolder> {

    private ArrayList<UserEvent> guests;


    public void setGusets(ArrayList<UserEvent> guests) {
        this.guests = guests;
        notifyDataSetChanged();
    }

    @Override
    public GuestsInvitedViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_list, parent, false);
        GuestsInvitedViewHolder providerViewHolder = new GuestsInvitedViewHolder(view, new GuestsInvitedViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(GuestsInvitedViewHolder view, int position) {
                //TODO CLICK EN EVENT IR A LA ACTIVITY DE INFO DEL EVENT

            }
        });
        return providerViewHolder;
    }

    @Override
    public void onBindViewHolder(GuestsInvitedViewHolder holder, int position) {
        UserEvent guest = guests.get(position);
        holder.title.setText(guest.getName());
        String dateString = guest.getSurname();
        holder.date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return (guests != null) ? guests.size() : 0;
    }

    public static class GuestsInvitedViewHolder extends RecyclerView.ViewHolder {

        private ItemClickListener listener;
        @BindView(R.id.title_event)
        public TextView title;
        @BindView(R.id.date_event_id)
        public TextView date;


        public GuestsInvitedViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GuestsInvitedViewHolder.this.listener != null) {
                        GuestsInvitedViewHolder.this.listener.onItemClick(GuestsInvitedViewHolder.this, getAdapterPosition());
                    }
                }
            });
        }


        interface ItemClickListener {
            void onItemClick(GuestsInvitedViewHolder view, int position);
        }
    }
}