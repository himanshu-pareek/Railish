package in.railish.railish.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import in.railish.railish.R;
import in.railish.railish.models.Day;
import in.railish.railish.models.Quota;
import in.railish.railish.models.Route;
import in.railish.railish.models.Station;
import in.railish.railish.models.Train;
import in.railish.railish.models.TrainClass;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class AvailabilityActivity extends AppCompatActivity {

    private String mRouteJSONString;
    private Route mRoute;
    private Train mTrain;

    // Spinners
    private Spinner mFromStationSpinner, mToStationSpinner, mClassSpinner, mQuotaSpinner;
    private ArrayList<Station> mStations;
    private ArrayAdapter<Station> mFromStationAdapter, mToStationAdapter;
    private ArrayAdapter<String> mClassAdapter;
    private ArrayAdapter<Quota> mQuotaAdapter;

    // Date of journey
    private TextView mDojTextView;
    private String mDateString;
    private ImageView mCalendarImageView;
    private int mDay, mMonth, mYear;

    // Buttons
    private Button mGetAvailabilityButton, mGetPredictionsButton;

    // Toolbar stuff
    private ImageView mBackArrowImage;
    private TextView mTrainNumberNameTextView;
    private LinearLayout mDayNameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBackArrowImage = (ImageView) findViewById(R.id.availability_activity_back_arrow);
        mTrainNumberNameTextView = (TextView) findViewById(R.id.train_number_name_text_view_on_toolbar_2);
        mDayNameLayout = (LinearLayout) findViewById(R.id.train_run_day_on_toolbar_2);

        mFromStationSpinner = (Spinner) findViewById(R.id.choose_from_station_spinner);
        mToStationSpinner = (Spinner) findViewById(R.id.choose_to_station_spinner);
        mClassSpinner = (Spinner) findViewById(R.id.choose_class_spinner_2);
        mQuotaSpinner = (Spinner) findViewById(R.id.choose_quota_spinner_2);

        mDojTextView = (TextView) findViewById(R.id.date_string2);
        mCalendarImageView = (ImageView) findViewById(R.id.date_chooser2);

        mGetAvailabilityButton = (Button) findViewById(R.id.get_availability_button);
        mGetPredictionsButton = (Button) findViewById(R.id.get_predictions_for_bd_button);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        mRouteJSONString = settings.getString(Constants.TRAIN_ROUTE_JSON_STRING_STRING, "");

        mRoute = QueryUtils.getRouteFromJSONString(mRouteJSONString);

        mTrain = mRoute.getTrain();

        initializeToolBar();
        initializeSpinners();
        initializeDate();

        mFromStationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Station s = (Station) parent.getItemAtPosition(position);
                int j = s.getNo();
                mToStationAdapter.clear();
                for (int i = j; i < mStations.size(); i++) {
                    mToStationAdapter.add(mStations.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCalendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AvailabilityActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month + 1;
                                mDay = dayOfMonth;

                                mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
                                mDojTextView.setText(mDateString);
                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }
        });

        mBackArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TrainActivity.class));
            }
        });

    }

    private void initializeDate() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
        mDojTextView.setText(mDateString);
    }

    private void initializeSpinners() {
        mStations = mRoute.getStations();

        mFromStationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < mStations.size() - 1; i++) {
            mFromStationAdapter.add(mStations.get(i));
        }
        mFromStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFromStationSpinner.setAdapter(mFromStationAdapter);

        mToStationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (int i = 1; i < mStations.size(); i++) {
            mToStationAdapter.add(mStations.get(i));
        }
        mToStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mToStationSpinner.setAdapter(mToStationAdapter);

        mClassAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        mClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayList<TrainClass> classes = mTrain.getClasses();
        for (TrainClass c : classes) {
            if (c.getAvailable().equals("Y") && !classes.contains("CLASS - " + c.getClassCode())) {
                mClassAdapter.add("CLASS - " + c.getClassCode());
            }
        }
        mClassSpinner.setAdapter(mClassAdapter);

        ArrayList<Quota> quotas = QueryUtils.getQuotaList();
        mQuotaAdapter = new ArrayAdapter<Quota>(this, android.R.layout.simple_spinner_item, quotas);
        mQuotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mQuotaSpinner.setAdapter(mQuotaAdapter);

    }

    private void initializeToolBar() {
        mTrainNumberNameTextView.setText(mTrain.getNumber() + "  " + mTrain.getName());

        for (Day d : mTrain.getDays()) {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(d.getDayCode().substring(0, 1) + " ");
            if (d.getRun().equals("Y")) {
                tv.setTextColor(Color.WHITE);
            } else {
                tv.setTextColor(Color.BLACK);
            }
            mDayNameLayout.addView(tv);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TrainActivity.class));
    }
}
