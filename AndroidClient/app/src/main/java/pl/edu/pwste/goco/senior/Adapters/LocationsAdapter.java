package pl.edu.pwste.goco.senior.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.edu.pwste.goco.senior.Entity.SavedLocalization;
import pl.edu.pwste.goco.senior.R;

/**
 * Created by goco on 03.03.2018.
 */

public class LocationsAdapter extends ArrayAdapter<SavedLocalization> {
    private final Context context;
    private final List<SavedLocalization> locationList;


    public LocationsAdapter(Context context, List<SavedLocalization> locationList) {
        super(context, R.layout.location_row, locationList);
        this.locationList = locationList;
        this.context = context;
        sortLocationList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.location_row, parent, false);
        TextView locationButton = (TextView) rowView.findViewById(R.id.location_label);
        locationButton.setText(locationList.get(position).toString());
        return rowView;
    }

    private void sortLocationList() {
        if (locationList.size() > 0) {
            Collections.sort(locationList, new Comparator<SavedLocalization>() {
                @Override
                public int compare(final SavedLocalization object1, final SavedLocalization object2) {
                    return object1.getName().toLowerCase().compareTo(object2.getName().toLowerCase());
                }
            });
        }


    }

}
