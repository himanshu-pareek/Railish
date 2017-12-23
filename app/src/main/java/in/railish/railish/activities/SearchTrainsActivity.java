package in.railish.railish.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.railish.railish.R;
import in.railish.railish.adapters.StationAutoCompleteAdapter;
import in.railish.railish.models.Station;
import in.railish.railish.ui.DelayAutoCompleteTextView;
import in.railish.railish.utils.Constants;

public class SearchTrainsActivity extends AppCompatActivity implements View.OnClickListener {

    private DelayAutoCompleteTextView mFromStationTextView, mToStationTextView;
    private Station mSourceStation, mDestinationStation;
    private ImageView mDateChooserImageView;
    private TextView mDateStringTextView;
    private String mDateString;
    private int mYear, mMonth, mDay;
    private RequestQueue mRequestQueue;
    private Button mShowTrainsButton;
    // private String[] mLanguages = {"Android ","java","IOS","SQL","JDBC","Web services"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trains);

        mDateChooserImageView = (ImageView) findViewById(R.id.date_chooser1);
        mDateStringTextView = (TextView) findViewById(R.id.date_string1);
        mShowTrainsButton = (Button) findViewById(R.id.show_trains_button);

        mRequestQueue = Volley.newRequestQueue(this);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);

        mDateStringTextView.setText(mDateString);

        mFromStationTextView = (DelayAutoCompleteTextView) findViewById(R.id.from_station_edit_text);
        mFromStationTextView.setThreshold(3);
        mFromStationTextView.setAdapter(new StationAutoCompleteAdapter(this, mRequestQueue));
        mFromStationTextView.setLoadingIndicator((android.widget.ProgressBar) findViewById(R.id.pb_from_station_loading_indicator));
        mFromStationTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSourceStation = (Station) parent.getItemAtPosition(position);
                mFromStationTextView.setText(mSourceStation.getName() + " [ " + mSourceStation.getCode() + " ]");
            }
        });

        mToStationTextView = (DelayAutoCompleteTextView) findViewById(R.id.to_station_edit_text);
        mToStationTextView.setThreshold(3);
        mToStationTextView.setAdapter(new StationAutoCompleteAdapter(this, mRequestQueue));
        mToStationTextView.setLoadingIndicator((android.widget.ProgressBar) findViewById(R.id.pb_to_station_loading_indicator));
        mToStationTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDestinationStation = (Station) parent.getItemAtPosition(position);
                mToStationTextView.setText(mDestinationStation.getName() + " [ " + mDestinationStation.getCode() + " ]");
            }
        });

        mDateChooserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchTrainsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month + 1;
                                mDay = dayOfMonth;

                                mDateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
                                mDateStringTextView.setText(mDateString);
                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }
        });

        mShowTrainsButton.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_trains_button:
                showTrains();
                break;
        }
    }

    private void showTrains() {
        String jsonString = "{\"total\": 8, \"response_code\": 200, \"train\": [{\"number\": \"12303\", \"dest_arrival_time\": \"00:05\", \"no\": 1, \"classes\": [{\"available\": \"Y\", \"class-code\": \"2A\"}, {\"available\": \"Y\", \"class-code\": \"1A\"}, {\"available\": \"N\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"N\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"Y\", \"class-code\": \"SL\"}], \"travel_time\": \"16:00\", \"days\": [{\"runs\": \"Y\", \"day-code\": \"MON\"}, {\"runs\": \"Y\", \"day-code\": \"TUE\"}, {\"runs\": \"N\", \"day-code\": \"WED\"}, {\"runs\": \"N\", \"day-code\": \"THU\"}, {\"runs\": \"Y\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"N\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"08:05\", \"name\": \"POORVA EXPRESS\", \"from\": {\"name\": \"HOWRAH JN\", \"code\": \"HWH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}, {\"number\": \"13007\", \"dest_arrival_time\": \"08:25\", \"no\": 2, \"classes\": [{\"available\": \"N\", \"class-code\": \"2A\"}, {\"available\": \"N\", \"class-code\": \"1A\"}, {\"available\": \"N\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"N\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"Y\", \"class-code\": \"SL\"}], \"travel_time\": \"22:50\", \"days\": [{\"runs\": \"Y\", \"day-code\": \"MON\"}, {\"runs\": \"Y\", \"day-code\": \"TUE\"}, {\"runs\": \"Y\", \"day-code\": \"WED\"}, {\"runs\": \"Y\", \"day-code\": \"THU\"}, {\"runs\": \"Y\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"Y\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"09:35\", \"name\": \"U ABHATOOFAN EXP\", \"from\": {\"name\": \"HOWRAH JN\", \"code\": \"HWH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}, {\"number\": \"12313\", \"dest_arrival_time\": \"05:10\", \"no\": 3, \"classes\": [{\"available\": \"Y\", \"class-code\": \"2A\"}, {\"available\": \"Y\", \"class-code\": \"1A\"}, {\"available\": \"N\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"N\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"N\", \"class-code\": \"SL\"}], \"travel_time\": \"12:20\", \"days\": [{\"runs\": \"Y\", \"day-code\": \"MON\"}, {\"runs\": \"Y\", \"day-code\": \"TUE\"}, {\"runs\": \"Y\", \"day-code\": \"WED\"}, {\"runs\": \"Y\", \"day-code\": \"THU\"}, {\"runs\": \"Y\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"Y\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"16:50\", \"name\": \"SDAH RAJDHANI EXPRESS\", \"from\": {\"name\": \"SEALDAH\", \"code\": \"SDAH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}, {\"number\": \"12301\", \"dest_arrival_time\": \"04:45\", \"no\": 4, \"classes\": [{\"available\": \"Y\", \"class-code\": \"2A\"}, {\"available\": \"Y\", \"class-code\": \"1A\"}, {\"available\": \"N\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"N\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"N\", \"class-code\": \"SL\"}], \"travel_time\": \"11:50\", \"days\": [{\"runs\": \"Y\", \"day-code\": \"MON\"}, {\"runs\": \"Y\", \"day-code\": \"TUE\"}, {\"runs\": \"Y\", \"day-code\": \"WED\"}, {\"runs\": \"Y\", \"day-code\": \"THU\"}, {\"runs\": \"Y\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"N\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"16:55\", \"name\": \"KOLKATA RAJDHANI EXPRESS\", \"from\": {\"name\": \"HOWRAH JN\", \"code\": \"HWH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}, {\"number\": \"12249\", \"dest_arrival_time\": \"06:20\", \"no\": 5, \"classes\": [{\"available\": \"Y\", \"class-code\": \"2A\"}, {\"available\": \"N\", \"class-code\": \"1A\"}, {\"available\": \"Y\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"N\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"N\", \"class-code\": \"SL\"}], \"travel_time\": \"11:40\", \"days\": [{\"runs\": \"N\", \"day-code\": \"MON\"}, {\"runs\": \"N\", \"day-code\": \"TUE\"}, {\"runs\": \"N\", \"day-code\": \"WED\"}, {\"runs\": \"N\", \"day-code\": \"THU\"}, {\"runs\": \"N\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"N\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"18:40\", \"name\": \"HWH-ANVT YUVA EXP\", \"from\": {\"name\": \"HOWRAH JN\", \"code\": \"HWH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}, {\"number\": \"12311\", \"dest_arrival_time\": \"12:35\", \"no\": 6, \"classes\": [{\"available\": \"Y\", \"class-code\": \"2A\"}, {\"available\": \"Y\", \"class-code\": \"1A\"}, {\"available\": \"N\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"Y\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"Y\", \"class-code\": \"SL\"}], \"travel_time\": \"16:55\", \"days\": [{\"runs\": \"Y\", \"day-code\": \"MON\"}, {\"runs\": \"Y\", \"day-code\": \"TUE\"}, {\"runs\": \"Y\", \"day-code\": \"WED\"}, {\"runs\": \"Y\", \"day-code\": \"THU\"}, {\"runs\": \"Y\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"Y\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"19:40\", \"name\": \"HWH DLIKLK MAIL\", \"from\": {\"name\": \"HOWRAH JN\", \"code\": \"HWH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}, {\"number\": \"12987\", \"dest_arrival_time\": \"14:30\", \"no\": 7, \"classes\": [{\"available\": \"Y\", \"class-code\": \"2A\"}, {\"available\": \"N\", \"class-code\": \"1A\"}, {\"available\": \"N\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"N\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"Y\", \"class-code\": \"SL\"}], \"travel_time\": \"15:25\", \"days\": [{\"runs\": \"Y\", \"day-code\": \"MON\"}, {\"runs\": \"Y\", \"day-code\": \"TUE\"}, {\"runs\": \"Y\", \"day-code\": \"WED\"}, {\"runs\": \"Y\", \"day-code\": \"THU\"}, {\"runs\": \"Y\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"Y\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"23:05\", \"name\": \"SDAH AII  SF EXP\", \"from\": {\"name\": \"SEALDAH\", \"code\": \"SDAH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}, {\"number\": \"12307\", \"dest_arrival_time\": \"15:05\", \"no\": 8, \"classes\": [{\"available\": \"N\", \"class-code\": \"2A\"}, {\"available\": \"N\", \"class-code\": \"1A\"}, {\"available\": \"N\", \"class-code\": \"CC\"}, {\"available\": \"N\", \"class-code\": \"FC\"}, {\"available\": \"N\", \"class-code\": \"3E\"}, {\"available\": \"N\", \"class-code\": \"2S\"}, {\"available\": \"Y\", \"class-code\": \"3A\"}, {\"available\": \"Y\", \"class-code\": \"SL\"}], \"travel_time\": \"15:25\", \"days\": [{\"runs\": \"Y\", \"day-code\": \"MON\"}, {\"runs\": \"Y\", \"day-code\": \"TUE\"}, {\"runs\": \"Y\", \"day-code\": \"WED\"}, {\"runs\": \"Y\", \"day-code\": \"THU\"}, {\"runs\": \"Y\", \"day-code\": \"FRI\"}, {\"runs\": \"Y\", \"day-code\": \"SAT\"}, {\"runs\": \"Y\", \"day-code\": \"SUN\"}], \"src_departure_time\": \"23:40\", \"name\": \"HWH-JU EXP\", \"from\": {\"name\": \"HOWRAH JN\", \"code\": \"HWH\"}, \"to\": {\"name\": \"KANPUR CENTRAL\", \"code\": \"CNB\"}}], \"error\": \"\"}";

        long dateims = getDateInMilliSecond(mDateString);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constants.TRAIN_LIST_JSON_STRING, jsonString);
        editor.putLong(Constants.DOJ_STRING, dateims);
        editor.apply();

        startActivity(new Intent(this, TrainListActivity.class));
//        final String sourceCode = mSourceStation.getCode();
//        final String destCode = mDestinationStation.getCode();
//        final String dateString = String.valueOf(mDay) + "-" + String.valueOf(mMonth);
//        StringRequest request = new StringRequest(Request.Method.POST, Constants.GET_TRAINS_BETWEEN_STATIONS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        ((TextView) findViewById(R.id.json_text)).setText(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                ((TextView) findViewById(R.id.json_text)).setText(error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("source_station_code", sourceCode);
//                params.put("destination_station_code", destCode);
//                params.put("doj", dateString);
//                return params;
//            }
//        };
//        mRequestQueue.add(request);
    }

    private long getDateInMilliSecond(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy");
        try {
            Date d = formatter.parse(s);
            return d.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
