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
import in.railish.railish.models.Day;
import in.railish.railish.models.Train;
import in.railish.railish.models.TrainClass;

public class TrainAdapter extends ArrayAdapter<Train> {

    public TrainAdapter(Context context, ArrayList<Train> trains) {
        super(context, 0, trains);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_item_train, parent, false
            );
        }

        Train train = getItem(position);

        String number, name, duration, timing, classString, dayString;
        number = "( " + train.getNumber() + " )";
        name = train.getName();
        duration = train.getTravelTime();
        timing = train.getFromStation().getCode() + " ( " + train.getSrcDepartureTime() +
                " ) - " + train.getToStation().getCode() + " ( " + train.getDestArrivalTime() + " )";
        ArrayList<TrainClass> classes = train.getClasses();
        classString = "";
        for (int i = 0; i < classes.size(); i++) {
            classString += (classes.get(i).getAvailable().equals("Y")) ? classes.get(i).getClassCode() + "  " : "";
        }

        dayString = "";
        ArrayList<Day> days = train.getDays();
        for (int i = 0; i < days.size(); i++) {
            dayString += (days.get(i).getRun().equals("Y")) ? days.get(i).getDayCode() + "  " : "";
            //dayString += days.get(i).getRun() + "  ";
        }

        ((TextView) listItemView.findViewById(R.id.train_number_text_view_1)).setText(number);
        ((TextView) listItemView.findViewById(R.id.train_name_text_view_1)).setText(name);
        ((TextView) listItemView.findViewById(R.id.train_classes_text_view_1)).setText(classString);
        ((TextView) listItemView.findViewById(R.id.train_days_text_view_1)).setText(dayString);
        ((TextView) listItemView.findViewById(R.id.train_dime_duration_text_view_1)).setText(duration);
        ((TextView) listItemView.findViewById(R.id.train_timing_text_view_1)).setText(timing);

        return listItemView;
    }
}
