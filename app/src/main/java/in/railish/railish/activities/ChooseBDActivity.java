package in.railish.railish.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.railish.railish.R;
import in.railish.railish.models.Quota;
import in.railish.railish.models.Train;
import in.railish.railish.models.TrainBetweenStationsDetail;
import in.railish.railish.models.TrainClass;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class ChooseBDActivity extends AppCompatActivity {

    private Spinner mClassSpinner, mQuotaSpinner;
    private ArrayAdapter<String> mClassNameAdapter;
    public ArrayAdapter<Quota> mQuotaAdapter;
    private int mDay, mMonth, mYear;
    private String mDateString;
    private TextView mBDTextView;
    private ImageView mBDSelectImage;
    private long mDOJTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bd);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        String jsonString = settings.getString(Constants.TRAIN_LIST_JSON_STRING, "");
        mDOJTime = settings.getLong(Constants.DOJ_STRING, 0);

        TrainBetweenStationsDetail detail = QueryUtils.parseTrainBetweenStationDetailFromJSONString(jsonString);
        ArrayList<Train> trains = detail.getTrains();

        mClassSpinner = (Spinner) findViewById(R.id.choose_class_spinner);
        mQuotaSpinner = (Spinner) findViewById(R.id.choose_quota_spinner);
        mBDTextView = (TextView) findViewById(R.id.booking_date_text_view);
        mBDSelectImage = (ImageView) findViewById(R.id.booking_date_calendar_image_view);

        initializeBookingDate();

        ArrayList<String> classes = new ArrayList<>();
        for (Train t: trains) {
            ArrayList<TrainClass> classes1 = t.getClasses();
            for (TrainClass c : classes1) {
                if (c.getAvailable().equals("Y") && !classes.contains("CLASS - " + c.getClassCode())) {
                    classes.add("CLASS - " + c.getClassCode());
                }
            }
        }

        ArrayList<Quota> quotas = QueryUtils.getQuotaList();

        mClassNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classes);
        mQuotaAdapter = new ArrayAdapter<Quota>(this, android.R.layout.simple_spinner_item, quotas);

        mClassNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mQuotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mClassSpinner.setAdapter(mClassNameAdapter);
        mQuotaSpinner.setAdapter(mQuotaAdapter);

        String labelString = "";
        Train t = trains.get(0);
        if (t != null) {
            labelString += t.getFromStation().getCode() + " - " + t.getToStation().getCode() + "  ";
        }
        if (mDOJTime != 0) {
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
            labelString += "( " +  format.format(new Date(mDOJTime)) + " )";
        }
        if (labelString.equals("")) {
            labelString = "Choose Booking Date";
        }

        setTitle(labelString);

        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                String strToDisplay = s.substring(8);
                Toast.makeText(parent.getContext(), strToDisplay, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBDSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseBDActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month + 1;
                                mDay = dayOfMonth;

                                mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
                                mBDTextView.setText(mDateString);
                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void initializeBookingDate() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
        mBDTextView.setText(mDateString);
    }

}
