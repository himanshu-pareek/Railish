package in.railish.railish.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import in.railish.railish.R;
import in.railish.railish.models.Route;
import in.railish.railish.models.Train;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class RunningStatusActivity extends AppCompatActivity {

    private String mRouteJSONString;
    private TextView mRouteTextView;
    private int mBackClassInt; // 0 means MainActivity and 1 means Train Activity
    private Train mTrain;
    private EditText mTrainNumberNameEditText;
    private TextView mStartDateTextView;
    private ImageView mDateChooser;
    private Button mGetLiveStatusButton;
    private int mDay, mMonth, mYear;
    private String mDateString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // mRouteTextView = (TextView) findViewById(R.id.route_text_view_1);
        mTrainNumberNameEditText = (EditText) findViewById(R.id.running_status_train_number_name_text);
        mStartDateTextView = (TextView) findViewById(R.id.running_status_start_date_text_view);
        mDateChooser = (ImageView) findViewById(R.id.running_status_start_date_calendar_image_view);
        mGetLiveStatusButton = (Button) findViewById(R.id.get_live_running_status_button);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        mRouteJSONString = settings.getString(Constants.TRAIN_ROUTE_JSON_STRING_STRING, "");

        if (mRouteJSONString.equals("")) {
            mBackClassInt = 0;  // from main class
            mTrain = null;
        } else {
            mBackClassInt = 1;  // from train class
            Route r = QueryUtils.getRouteFromJSONString(mRouteJSONString);
            mTrain = r.getTrain();
        }

        initializeTrainNameAndNumber();
        initializeStartDate();

        // mRouteTextView.setText(mRouteJSONString);

        mDateChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RunningStatusActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month + 1;
                                mDay = dayOfMonth;

                                mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
                                mStartDateTextView.setText(mDateString);
                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }
        });

        mGetLiveStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mTrainNumberNameEditText.getText().toString().trim();
                String s1;
                if (s.contains("-")) {
                    s1 = s.substring(0, s.indexOf("-") - 1);
                } else {
                    s1 = s;
                }
                Toast.makeText(RunningStatusActivity.this, "a" + s1 + "b", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeStartDate() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
        mStartDateTextView.setText(mDateString);
    }

    private void initializeTrainNameAndNumber() {
        if (mTrain != null) {
            mTrainNumberNameEditText.setText(mTrain.getNumber() + " - " + mTrain.getName());
        }
    }

    @Override
    public void onBackPressed() {
        Intent i;
        if (mBackClassInt == 0) {
            i = new Intent(this, MainActivity.class);
        } else {
            i = new Intent(this, TrainActivity.class);
        }
        startActivity(i);
    }
}
