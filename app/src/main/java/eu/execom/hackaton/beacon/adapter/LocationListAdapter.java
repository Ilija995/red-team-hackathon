package eu.execom.hackaton.beacon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import eu.execom.hackaton.beacon.R;
import eu.execom.hackaton.beacon.model.Location;

@EBean
public class LocationListAdapter extends BaseAdapter {

    private final List<Location> locations = new ArrayList<>();

    @RootContext
    Context context;

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Location getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
     public View getView(int position, View convertView, ViewGroup parent) {
        LocationItemView locationItemView;
        if (convertView == null) {
            locationItemView = LocationListAdapter_.LocationItemView_.build(context);
        } else {
            locationItemView = (LocationItemView) convertView;
        }
        locationItemView.bind(getItem(position));
        return locationItemView;
    }

    public void update(List<Location> locations) {
        this.locations.clear();
        this.locations.addAll(locations);
        notifyDataSetChanged();
    }

    @EViewGroup(R.layout.item_location)
    public static class LocationItemView extends LinearLayout {

        @ViewById
        TextView uuid;

        @ViewById
        TextView signalStrength;

        public LocationItemView(Context context) {
            super(context);
        }

        public void bind(Location location) {
            uuid.setText(location.uuid);
            signalStrength.setText(location.getSignalStrength());
        }

    }

}
