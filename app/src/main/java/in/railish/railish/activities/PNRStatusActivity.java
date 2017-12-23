package in.railish.railish.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import in.railish.railish.R;
import in.railish.railish.adapters.PassengerAdapter;
import in.railish.railish.models.PNRStatus;
import in.railish.railish.models.Passenger;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class PNRStatusActivity extends AppCompatActivity implements
        View.OnClickListener{

    private Button mGetPNRStatusButton;
    private TextView mPNRStatusResponseTextView;
    private EditText mPNREditText;
    private PNRStatus mPNRStatus = null;
    private String pnrString;
    private LinearLayout mPNRDetailsLayout;
    private ArrayList<View> mPassengerViews;
    // private PassengerAdapter mPassengerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnrstatus);

        mPNRDetailsLayout = (LinearLayout) findViewById(R.id.pnr_status_details_layout);
        mPNRDetailsLayout.setVisibility(View.GONE);

        mPassengerViews = new ArrayList<>();

        mGetPNRStatusButton = (Button) findViewById(R.id.get_pnr_status_button);
        mPNRStatusResponseTextView = (TextView) findViewById(R.id.pnr_status_response_text_view);
        mPNREditText = (EditText) findViewById(R.id.pnr_edit_text);

        mGetPNRStatusButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_pnr_status_button:
                getPNRStatus();
                break;
            default:

        }
    }

    private void getPNRStatus() {
        pnrString = mPNREditText.getText().toString().trim();
        if (pnrString.length() == 10) {
            for (int i = 0; i < mPassengerViews.size(); i++) {
                mPNRDetailsLayout.removeView(mPassengerViews.get(i));
            }
            mPassengerViews.clear();
            PNRAsyncTask pnrAsyncTask = new PNRAsyncTask(this);
            pnrAsyncTask.execute(Constants.METHOD_PNR_STATUS, pnrString);
        } else {
            Toast.makeText(this, "Please enter a valid 10-digit train pnr number", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(PNRStatus pnrStatus, String s) {
//        if (pnrStatus != null) {
//            mPNRStatusResponseTextView.setText(pnrStatus.toString());
//        } else {
//            mPNRStatusResponseTextView.setText(s);
//        }
        mPNRDetailsLayout = (LinearLayout) findViewById(R.id.pnr_status_details_layout);
        if (pnrStatus == null) {
            mPNRStatusResponseTextView.setText("Unexpected error occured, try again");
            mPNRDetailsLayout.setVisibility(View.GONE);
            return;
        }

        switch (pnrStatus.getResponseCode()) {
            case 204:
                mPNRDetailsLayout.setVisibility(View.GONE);
                if (pnrStatus.getFailureRate() <= 35.0) {
                    PNRAsyncTask pnrAsyncTask = new PNRAsyncTask(this);
                    pnrAsyncTask.execute(Constants.METHOD_PNR_STATUS, pnrString);
                    return;
                } else {
                    mPNRStatusResponseTextView.setText("PNR Source is down, try again");
                }
                break;
            case 410:
                mPNRDetailsLayout.setVisibility(View.GONE);
                mPNRStatusResponseTextView.setText("Flushed PNR / PNR not yet generated");
                break;
            case 404:
                mPNRDetailsLayout.setVisibility(View.GONE);
                mPNRStatusResponseTextView.setText("Service Down / Source not responding");
                break;
            case 200:
                mPNRStatusResponseTextView.setText("Here is PNR Status");
                mPNRDetailsLayout.setVisibility(View.VISIBLE);

                // mPassengerAdapter = new PassengerAdapter(this, new ArrayList<Passenger>());
                // ListView passengerList = (ListView) findViewById(R.id.pnr_passengers_list);

                // passengerList.setAdapter(mPassengerAdapter);

                TextView pnrNumberTextView, dojTextView, fromTextView, toTextView,
                        boardingPointTextView, reservationUptoTextView, trainNumberTextView,
                        trainNameTextView, chartPreparedTextView, classTextView,
                        totalPassengersTextView;

                pnrNumberTextView = (TextView) findViewById(R.id.pnr_number_text_view);
                dojTextView = (TextView) findViewById(R.id.doj_text_view);
                fromTextView = (TextView) findViewById(R.id.from_station_text_view);
                toTextView = (TextView) findViewById(R.id.to_station_text_view);
                boardingPointTextView = (TextView) findViewById(R.id.boarding_point_text_view);
                reservationUptoTextView = (TextView) findViewById(R.id.reservation_upto_text_view);
                trainNumberTextView = (TextView) findViewById(R.id.train_number_text_view);
                trainNameTextView = (TextView) findViewById(R.id.train_name_text_view);
                chartPreparedTextView = (TextView) findViewById(R.id.chart_prepared_text_view);
                classTextView = (TextView) findViewById(R.id.class_text_view);
                totalPassengersTextView = (TextView) findViewById(R.id.total_passengers_text_view);

                pnrNumberTextView.setText("PNR: " + pnrStatus.getPnr());
                dojTextView.setText("Date of Journey: " + pnrStatus.getDoj());
                fromTextView.setText("From: " + pnrStatus.getFromStation().getName() + " [ "
                        + pnrStatus.getFromStation().getCode() + " ]");
                toTextView.setText("To: " + pnrStatus.getToStation().getName() + " [ "
                        + pnrStatus.getToStation().getCode() + " ]");
                boardingPointTextView.setText("Boarding Point: " + pnrStatus.getBoardingPoint().getName() + " [ "
                        + pnrStatus.getBoardingPoint().getCode() + " ]");
                reservationUptoTextView.setText("Reservation Upto: " + pnrStatus.getReservationUpto().getName() + " [ "
                        + pnrStatus.getReservationUpto().getCode() + " ]");

                trainNumberTextView.setText("Train Number: " + pnrStatus.getTrainNumber());
                trainNameTextView.setText("Train Name: " + pnrStatus.getTrainName());
                String cpt = pnrStatus.getChartPrepared().equals("Y") ? "Yes" : "No";
                chartPreparedTextView.setText("Chart Prepared: " + cpt);
                classTextView.setText("Class: " + pnrStatus.getTrainClass());
                totalPassengersTextView.setText("Total Passengers: " + String.valueOf(pnrStatus.getTotalPassengers()));



                // mPassengerAdapter.addAll(pnrStatus.getPassengers());
                for (Passenger p: pnrStatus.getPassengers()) {
                    View pv = LayoutInflater.from(this).inflate(R.layout.passenger_item_view, null);
                    ((TextView)pv.findViewById(R.id.passenger_number_text_view)).setText("Passenger: "
                            + String.valueOf(p.getNo()));
                    ((TextView)pv.findViewById(R.id.passenger_booking_status)).setText(p.getBookingStatus());
                    ((TextView)pv.findViewById(R.id.passenger_current_status)).setText(p.getCurrentStatus());
                    mPNRDetailsLayout.addView(pv);
                    mPassengerViews.add(pv);
                }

                break;
            default:

        }
    }

    private class PNRAsyncTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        private ProgressDialog mProgressDialog;

        public PNRAsyncTask(Context ctx) {
            mContext = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(PNRStatusActivity.this,
                    "Please wait ...",
                    "Getting PNR Status");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals(Constants.METHOD_PNR_STATUS)) {
                String requestUrl = Constants.PNR_STATUS_REQUEST_URL;
                String pnrString = params[1];
                String response = "";
                HttpURLConnection httpURLConnection = null;
                OutputStream outputStream = null;
                InputStream inputStream = null;
                try {
                    URL url = new URL(requestUrl);

                    httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setReadTimeout(10000);
//                    httpURLConnection.setConnectTimeout(15000);
                    httpURLConnection.setRequestMethod("POST");

                    outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter writer = new BufferedWriter(outputStreamWriter);

                    String data = URLEncoder.encode("pnr_number", "UTF-8") + "=" + URLEncoder.encode(pnrString, "UTF-8");

                    writer.write(data);
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    httpURLConnection.connect();

                    inputStream = httpURLConnection.getInputStream();

                    StringBuilder output = new StringBuilder();
                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                        BufferedReader reader = new BufferedReader(inputStreamReader);
                        String line = reader.readLine();
                        while (line != null) {
                            output.append(line);
                            line = reader.readLine();
                        }
                    }

                    response = output.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return response;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
            try {
                mPNRStatus = QueryUtils.getPnrStatusFromJSONString(s);
//                if (mPNRStatus == null) {
//                    Toast.makeText(mContext, "PNR status is null", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(mContext, "PNR status is not null", Toast.LENGTH_SHORT).show();
//                }
            } catch (JSONException e) {
                // Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                updateUI(null, "");
                return;
            }
            updateUI(mPNRStatus, s);
            super.onPostExecute(s);
        }
    }
}
