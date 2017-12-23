package in.railish.railish.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.railish.railish.R;
import in.railish.railish.models.Station;

public class StationAdapter extends ArrayAdapter<Station> {

    public StationAdapter (Context context, ArrayList<Station> stations) {
        super(context, 0, stations);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView  = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_route_station_item, parent, false
            );
        }

        Station station = getItem(position);

        TextView stationNameTextView = (TextView) listView.findViewById(R.id.route_station_name_text_view);
        TextView stationDayTextView = (TextView) listView.findViewById(R.id.route_day_text_view);
        TextView stationArrTextView = (TextView) listView.findViewById(R.id.route_station_arr_text_view);
        TextView stationDepTextView = (TextView) listView.findViewById(R.id.route_station_dep_text_view);
        TextView stationHaltTextView = (TextView) listView.findViewById(R.id.route_station_halt_text_view);
        TextView stationDistTextView = (TextView) listView.findViewById(R.id.route_station_dist_text_view);

        stationNameTextView.setText(station.getName());
        stationDayTextView.setText(String.valueOf(station.getDay()));
        stationArrTextView.setText(station.getSchArr());
        stationDepTextView.setText(station.getSchDep());
        stationHaltTextView.setText(String.valueOf(station.getHalt()) + "min");
        stationDistTextView.setText(String.valueOf(station.getDistance()) + "Km");

        return listView;
    }
}
