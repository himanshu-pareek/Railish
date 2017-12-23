package in.railish.railish.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.railish.railish.R;
import in.railish.railish.models.Choice;
import in.railish.railish.models.Passenger;

public class PassengerAdapter extends ArrayAdapter<Passenger> {

    public PassengerAdapter(Context context, ArrayList<Passenger> passengers) {
        super(context, 0, passengers);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.passenger_item_view, parent, false
            );
        }

        Passenger currentPassenger = getItem(position);

        TextView passengerNumberTextView = (TextView) listItemView.findViewById(R.id.passenger_number_text_view);
        TextView bookingStatusTextView = (TextView) listItemView.findViewById(R.id.passenger_booking_status);
        TextView currentStatusTextView = (TextView) listItemView.findViewById(R.id.passenger_current_status);

        passengerNumberTextView.setText("Passenger " + String.valueOf(currentPassenger.getNo()));
        bookingStatusTextView.setText(currentPassenger.getBookingStatus());
        currentStatusTextView.setText(currentPassenger.getCurrentStatus());

        return listItemView;
    }

}
