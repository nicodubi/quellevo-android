package com.quellevo.quellevo.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quellevo.quellevo.R;

import java.util.List;

/**
 * Created by Nicolas on 29/6/2017.
 */


public class ListViewAdapter<T> extends ArrayAdapter<T> {
    private List<T> objects;
    private boolean itemsRemovables;
    private ClickTickItem<T> clickTickItem;
    private ClickDeleteItem<T> clickDeleteItem;

    public ListViewAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.itemsRemovables = true;
    }

    public void configElements(boolean removables, ClickDeleteItem<T> deleteItemListener, ClickTickItem<T> clickTickItem) {
        this.itemsRemovables = removables;
        this.clickTickItem = clickTickItem;
        this.clickDeleteItem = deleteItemListener;
    }

    public void clear() {
        objects.clear();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_item_list_autocomplete, parent, false);
        }
        TextView t = (TextView) convertView.findViewById(R.id.textAdapterItem);
        t.setText(getItem(position).toString());
        ImageView delete = (ImageView) convertView.findViewById(R.id.deleteItemList);
        ImageView tick = (ImageView) convertView.findViewById(R.id.tickIconId);
        if (itemsRemovables) {
            delete.setTag(position);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag() != null) {
                        if (clickDeleteItem != null) {
                            clickDeleteItem.onItemClickDeleted(objects.get((int) view.getTag()));
                        }
                        objects.remove((int) view.getTag());
                        notifyDataSetChanged();
                    }
                }
            });
        } else {
            delete.setVisibility(View.GONE);
        }
        if (this.clickTickItem != null) {
            tick.setTag(position);
            tick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        clickTickItem.onItemClickTick(objects.get((int) v.getTag()));

                    }
                }
            });
        } else {
            tick.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public void remove(T dto) {
        objects.remove(dto);
        notifyDataSetChanged();
    }

    @Override
    public void add(T dto) {
        objects.add(dto);
        notifyDataSetChanged();
    }
    public interface ClickTickItem<T> {

        public void onItemClickTick(T item);
    }

    public interface ClickDeleteItem<T> {
        /**
         * @param item
         * @return return if do you want to be removed item from list
         */
        public void onItemClickDeleted(T item);
    }
}

