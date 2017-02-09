package com.oil.vivek.oilmillcalc.notification;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.oil.vivek.oilmillcalc.R;


import java.util.List;


public class notification_adapter extends RecyclerView.Adapter<notification_adapter.MyViewHolder> {

    private Context mContext;
    private List<notification_model> notificationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message, time;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            message = (TextView) view.findViewById(R.id.message);
            thumbnail = (ImageView) view.findViewById(R.id.imageNotification);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            time = (TextView) view.findViewById(R.id.time);
        }
    }


    public notification_adapter(Context mContext, List<notification_model> notificationList) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        notification_model notification = notificationList.get(position);
        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        holder.time.setText(notification.getTime());
        // loading notification cover using Glide library
        if(notification.getImage() != -1)
        Glide.with(mContext).load(notification.getImage()).into(holder.thumbnail);
        else
        holder.thumbnail.setVisibility(View.GONE);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_notification_overflow, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}