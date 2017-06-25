package com.quellevo.quellevo.utils.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quellevo.quellevo.R;
import com.quellevo.quellevo.home.EventInfoActivity;
import com.quellevo.quellevo.utils.Constants;
import com.quellevo.quellevo.web_services.rest_entities.Event;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicolas on 17/5/2017.
 */

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventViewHolder> {

    private ArrayList<Event> events;


    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public EventViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_list, parent, false);
        EventViewHolder providerViewHolder = new EventViewHolder(view, new EventViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(EventViewHolder view, int position) {
                //TODO CLICK EN EVENT IR A LA ACTIVITY DE INFO DEL EVENT
                Intent intent = new Intent(parent.getContext(), EventInfoActivity.class);
                intent.putExtra(Constants.EVENT_ID_KEY, events.get(position).getId());
                parent.getContext().startActivity(intent);
            }
        });
        return providerViewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.title.setText(event.getName());
        String dateString = event.getDateString();
        holder.date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return (events != null) ? events.size() : 0;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        private ItemClickListener listener;
        @BindView(R.id.title_event)
        public TextView title;
        @BindView(R.id.date_event_id)
        public TextView date;


        public EventViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (EventViewHolder.this.listener != null) {
                        EventViewHolder.this.listener.onItemClick(EventViewHolder.this, getAdapterPosition());
                    }
                }
            });
        }


        interface ItemClickListener {
            void onItemClick(EventViewHolder view, int position);
        }
    }

}
