package in.railish.railish.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.railish.railish.R;
import in.railish.railish.adapters.StationAdapter;
import in.railish.railish.models.Route;
import in.railish.railish.models.Station;
import in.railish.railish.models.Train;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class RouteActivity extends AppCompatActivity {

    private String mRouteJSONString;
    private TextView mTrainNumberNameTextView;
    private Train mTrain;
    private StationAdapter mStationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        mRouteJSONString = settings.getString(Constants.TRAIN_ROUTE_JSON_STRING_STRING, "");

        mTrainNumberNameTextView = (TextView) findViewById(R.id.train_number_name_text_view);

        Route route = QueryUtils.getRouteFromJSONString(mRouteJSONString);
        mTrain = route.getTrain();
        ArrayList<Station> stations = route.getStations();

        mTrainNumberNameTextView.setText(mTrain.getNumber() + "  " + mTrain.getName());

        mStationAdapter = new StationAdapter(this, stations);
        ListView stationList = (ListView) findViewById(R.id.station_list_view_route);

        stationList.setAdapter(mStationAdapter);

    }

}
