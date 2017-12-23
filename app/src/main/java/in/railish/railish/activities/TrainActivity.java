package in.railish.railish.activities;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.railish.railish.R;
import in.railish.railish.adapters.ChoiceAdapter;
import in.railish.railish.models.Choice;
import in.railish.railish.models.Day;
import in.railish.railish.models.Train;
import in.railish.railish.models.TrainBetweenStationsDetail;
import in.railish.railish.models.TrainClass;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class TrainActivity extends AppCompatActivity {

    private LinearLayout mDaysLayout;
    private TextView mTrainNameNumberTextView;
    private ChoiceAdapter mChoiceAdapter;
    private ImageView mBackArrowImage;
    private Train mTrain;
    private int mBackClass;

    private String mTrainRouteJSONString = "{\n" +
            "    \"response_code\": 200,\n" +
            "    \"route\": [\n" +
            "        {\n" +
            "            \"code\": \"HWH\",\n" +
            "            \"schdep\": \"08:05\",\n" +
            "            \"halt\": 0,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"West Bengal 711101\",\n" +
            "            \"scharr\": \"Source\",\n" +
            "            \"no\": 1,\n" +
            "            \"lng\": 88.3429024,\n" +
            "            \"distance\": 0,\n" +
            "            \"fullname\": \"HOWRAH JN\",\n" +
            "            \"lat\": 22.5834126\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"BWN\",\n" +
            "            \"schdep\": \"09:13\",\n" +
            "            \"halt\": 3,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"West Bengal 713103\",\n" +
            "            \"scharr\": \"09:10\",\n" +
            "            \"no\": 2,\n" +
            "            \"lng\": 87.869508,\n" +
            "            \"distance\": 95,\n" +
            "            \"fullname\": \"BARDDHAMAN\",\n" +
            "            \"lat\": 23.2498319\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"DGR\",\n" +
            "            \"schdep\": \"10:06\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"West Bengal 713201\",\n" +
            "            \"scharr\": \"10:04\",\n" +
            "            \"no\": 3,\n" +
            "            \"lng\": 87.3174726,\n" +
            "            \"distance\": 158,\n" +
            "            \"fullname\": \"DURGAPUR\",\n" +
            "            \"lat\": 23.4954888\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"ASN\",\n" +
            "            \"schdep\": \"10:40\",\n" +
            "            \"halt\": 5,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"West Bengal 713301\",\n" +
            "            \"scharr\": \"10:35\",\n" +
            "            \"no\": 4,\n" +
            "            \"lng\": 86.975235,\n" +
            "            \"distance\": 200,\n" +
            "            \"fullname\": \"ASANSOL JN\",\n" +
            "            \"lat\": 23.6911108\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"CRJ\",\n" +
            "            \"schdep\": \"11:05\",\n" +
            "            \"halt\": 1,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"West Bengal 815354\",\n" +
            "            \"scharr\": \"11:04\",\n" +
            "            \"no\": 5,\n" +
            "            \"lng\": 86.8782485,\n" +
            "            \"distance\": 225,\n" +
            "            \"fullname\": \"CHITTARANJAN\",\n" +
            "            \"lat\": 23.8573785\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"MDP\",\n" +
            "            \"schdep\": \"11:46\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Jharkhand 815353\",\n" +
            "            \"scharr\": \"11:44\",\n" +
            "            \"no\": 6,\n" +
            "            \"lng\": 86.6426876,\n" +
            "            \"distance\": 282,\n" +
            "            \"fullname\": \"MADHUPUR JN\",\n" +
            "            \"lat\": 24.2706857\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"JSME\",\n" +
            "            \"schdep\": \"12:15\",\n" +
            "            \"halt\": 4,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Jharkhand 814142\",\n" +
            "            \"scharr\": \"12:11\",\n" +
            "            \"no\": 7,\n" +
            "            \"lng\": 86.6442623,\n" +
            "            \"distance\": 311,\n" +
            "            \"fullname\": \"JASIDIH JN\",\n" +
            "            \"lat\": 24.5147788\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"JAJ\",\n" +
            "            \"schdep\": \"13:17\",\n" +
            "            \"halt\": 5,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 811308\",\n" +
            "            \"scharr\": \"13:12\",\n" +
            "            \"no\": 8,\n" +
            "            \"lng\": 86.38613459999999,\n" +
            "            \"distance\": 355,\n" +
            "            \"fullname\": \"JHAJHA\",\n" +
            "            \"lat\": 24.772899\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"JMU\",\n" +
            "            \"schdep\": \"13:37\",\n" +
            "            \"halt\": 1,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 811307\",\n" +
            "            \"scharr\": \"13:36\",\n" +
            "            \"no\": 9,\n" +
            "            \"lng\": 86.2551484,\n" +
            "            \"distance\": 381,\n" +
            "            \"fullname\": \"JAMUI\",\n" +
            "            \"lat\": 24.9722861\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"KIUL\",\n" +
            "            \"schdep\": \"14:02\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 811310\",\n" +
            "            \"scharr\": \"14:00\",\n" +
            "            \"no\": 10,\n" +
            "            \"lng\": 86.1063063,\n" +
            "            \"distance\": 409,\n" +
            "            \"fullname\": \"KIUL JN\",\n" +
            "            \"lat\": 25.171812\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"MKA\",\n" +
            "            \"schdep\": \"14:35\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"\",\n" +
            "            \"scharr\": \"14:33\",\n" +
            "            \"no\": 11,\n" +
            "            \"lng\": 0.0,\n" +
            "            \"distance\": 443,\n" +
            "            \"fullname\": \"MOKAMEH JN\",\n" +
            "            \"lat\": 0.0\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"BARH\",\n" +
            "            \"schdep\": \"14:52\",\n" +
            "            \"halt\": 1,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 803213\",\n" +
            "            \"scharr\": \"14:51\",\n" +
            "            \"no\": 12,\n" +
            "            \"lng\": 85.70968239999999,\n" +
            "            \"distance\": 468,\n" +
            "            \"fullname\": \"BARH\",\n" +
            "            \"lat\": 25.4622513\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"BKP\",\n" +
            "            \"schdep\": \"15:12\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 803212\",\n" +
            "            \"scharr\": \"15:10\",\n" +
            "            \"no\": 13,\n" +
            "            \"lng\": 85.5380467,\n" +
            "            \"distance\": 486,\n" +
            "            \"fullname\": \"BAKHTIYARPUR JN\",\n" +
            "            \"lat\": 25.4561375\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"PNBE\",\n" +
            "            \"schdep\": \"16:15\",\n" +
            "            \"halt\": 10,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 800001\",\n" +
            "            \"scharr\": \"16:05\",\n" +
            "            \"no\": 14,\n" +
            "            \"lng\": 85.137137,\n" +
            "            \"distance\": 532,\n" +
            "            \"fullname\": \"PATNA JN\",\n" +
            "            \"lat\": 25.6033028\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"DNR\",\n" +
            "            \"schdep\": \"16:32\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 801105\",\n" +
            "            \"scharr\": \"16:30\",\n" +
            "            \"no\": 15,\n" +
            "            \"lng\": 85.04362789999999,\n" +
            "            \"distance\": 541,\n" +
            "            \"fullname\": \"DANAPUR\",\n" +
            "            \"lat\": 25.5822775\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"ARA\",\n" +
            "            \"schdep\": \"17:03\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 802301\",\n" +
            "            \"scharr\": \"17:01\",\n" +
            "            \"no\": 16,\n" +
            "            \"lng\": 84.6613775,\n" +
            "            \"distance\": 581,\n" +
            "            \"fullname\": \"ARA\",\n" +
            "            \"lat\": 25.5487958\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"BXR\",\n" +
            "            \"schdep\": \"17:48\",\n" +
            "            \"halt\": 2,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Bihar 802101\",\n" +
            "            \"scharr\": \"17:46\",\n" +
            "            \"no\": 17,\n" +
            "            \"lng\": 83.9810882,\n" +
            "            \"distance\": 649,\n" +
            "            \"fullname\": \"BUXAR\",\n" +
            "            \"lat\": 25.5612101\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"MGS\",\n" +
            "            \"schdep\": \"19:47\",\n" +
            "            \"halt\": 10,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Uttar Pradesh 232101\",\n" +
            "            \"scharr\": \"19:37\",\n" +
            "            \"no\": 18,\n" +
            "            \"lng\": 83.1197011,\n" +
            "            \"distance\": 744,\n" +
            "            \"fullname\": \"MUGHAL SARAI JN\",\n" +
            "            \"lat\": 25.2783436\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"ALD\",\n" +
            "            \"schdep\": \"21:55\",\n" +
            "            \"halt\": 10,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 1,\n" +
            "            \"state\": \"Uttar Pradesh 211001\",\n" +
            "            \"scharr\": \"21:45\",\n" +
            "            \"no\": 19,\n" +
            "            \"lng\": 81.82595529999999,\n" +
            "            \"distance\": 896,\n" +
            "            \"fullname\": \"ALLAHABAD JN\",\n" +
            "            \"lat\": 25.4455992\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"CNB\",\n" +
            "            \"schdep\": \"00:30\",\n" +
            "            \"halt\": 5,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 2,\n" +
            "            \"state\": \"Uttar Pradesh 244235\",\n" +
            "            \"scharr\": \"00:25\",\n" +
            "            \"no\": 20,\n" +
            "            \"lng\": 78.24845739999999,\n" +
            "            \"distance\": 1090,\n" +
            "            \"fullname\": \"KANPUR CENTRAL\",\n" +
            "            \"lat\": 28.8365997\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"ETW\",\n" +
            "            \"schdep\": \"02:03\",\n" +
            "            \"halt\": 1,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 2,\n" +
            "            \"state\": \"Uttar Pradesh 206001\",\n" +
            "            \"scharr\": \"02:02\",\n" +
            "            \"no\": 21,\n" +
            "            \"lng\": 79.0214736,\n" +
            "            \"distance\": 1228,\n" +
            "            \"fullname\": \"ETAWAH\",\n" +
            "            \"lat\": 26.7867033\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"TDL\",\n" +
            "            \"schdep\": \"03:40\",\n" +
            "            \"halt\": 5,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 2,\n" +
            "            \"state\": \"Uttar Pradesh 283204\",\n" +
            "            \"scharr\": \"03:35\",\n" +
            "            \"no\": 22,\n" +
            "            \"lng\": 78.2332144,\n" +
            "            \"distance\": 1320,\n" +
            "            \"fullname\": \"TUNDLA JN\",\n" +
            "            \"lat\": 27.2079996\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"ALJN\",\n" +
            "            \"schdep\": \"04:40\",\n" +
            "            \"halt\": 5,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 2,\n" +
            "            \"state\": \"Uttar Pradesh 202001\",\n" +
            "            \"scharr\": \"04:35\",\n" +
            "            \"no\": 23,\n" +
            "            \"lng\": 78.0880129,\n" +
            "            \"distance\": 1398,\n" +
            "            \"fullname\": \"ALIGARH JN\",\n" +
            "            \"lat\": 27.8973944\n" +
            "        },\n" +
            "        {\n" +
            "            \"code\": \"NDLS\",\n" +
            "            \"schdep\": \"Destination\",\n" +
            "            \"halt\": 0,\n" +
            "            \"route\": 1,\n" +
            "            \"day\": 2,\n" +
            "            \"state\": \"Delhi 110006\",\n" +
            "            \"scharr\": \"07:35\",\n" +
            "            \"no\": 24,\n" +
            "            \"lng\": 77.22081229999999,\n" +
            "            \"distance\": 1529,\n" +
            "            \"fullname\": \"NEW DELHI\",\n" +
            "            \"lat\": 28.6415494\n" +
            "        }\n" +
            "    ],\n" +
            "    \"train\": {\n" +
            "        \"classes\": [\n" +
            "            {\n" +
            "                \"class-code\": \"FC\",\n" +
            "                \"available\": \"N\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"class-code\": \"2A\",\n" +
            "                \"available\": \"Y\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"class-code\": \"2S\",\n" +
            "                \"available\": \"N\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"class-code\": \"1A\",\n" +
            "                \"available\": \"Y\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"class-code\": \"SL\",\n" +
            "                \"available\": \"Y\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"class-code\": \"3E\",\n" +
            "                \"available\": \"N\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"class-code\": \"3A\",\n" +
            "                \"available\": \"Y\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"class-code\": \"CC\",\n" +
            "                \"available\": \"N\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"name\": \"POORVA EXPRESS\",\n" +
            "        \"days\": [\n" +
            "            {\n" +
            "                \"day-code\": \"SUN\",\n" +
            "                \"runs\": \"N\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"day-code\": \"MON\",\n" +
            "                \"runs\": \"Y\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"day-code\": \"TUE\",\n" +
            "                \"runs\": \"Y\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"day-code\": \"WED\",\n" +
            "                \"runs\": \"N\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"day-code\": \"THU\",\n" +
            "                \"runs\": \"N\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"day-code\": \"FRI\",\n" +
            "                \"runs\": \"Y\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"day-code\": \"SAT\",\n" +
            "                \"runs\": \"Y\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"number\": \"12303\"\n" +
            "    }\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        String jsonString;
        mBackClass = settings.getInt(Constants.TRAIN_ACTIVITY_BACK_ACTIVITY, 0);

        if (mBackClass == 0) {
            jsonString = settings.getString(Constants.TRAIN_LIST_JSON_STRING, "");
            int trainIndex = settings.getInt(Constants.TRAIN_INDEX_STRING, 0);
            TrainBetweenStationsDetail detail = QueryUtils.parseTrainBetweenStationDetailFromJSONString(jsonString);
            mTrain = detail.getTrains().get(trainIndex);
        } else if (mBackClass == 1) {
            jsonString = settings.getString(Constants.TRAIN_JSON_STRING_STRING, ""  );
            mTrain = QueryUtils.parseJSONToGetTrain(jsonString);
        }

        mDaysLayout = (LinearLayout) findViewById(R.id.train_run_day_on_toolbar);
        mTrainNameNumberTextView = (TextView) findViewById(R.id.train_number_name_text_view_on_toolbar);
        mBackArrowImage = (ImageView) findViewById(R.id.train_activity_back_arrow);

        mBackArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackClass == 0) {
                    startActivity(new Intent(getApplicationContext(), TrainListActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), TrainsByNoActivity.class));
                }
            }
        });

        mChoiceAdapter = new ChoiceAdapter(this, new ArrayList<Choice>(), true);
        ListView choiceList = (ListView) findViewById(R.id.train_choices_list_view);

        choiceList.setAdapter(mChoiceAdapter);
        choiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Choice c = mChoiceAdapter.getItem(position);
                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(Constants.TRAIN_ROUTE_JSON_STRING_STRING, mTrainRouteJSONString);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), c.getNextClass()));
            }
        });

        addChoices();

        mTrainNameNumberTextView.setText(mTrain.getNumber() + "  " + mTrain.getName());

        for (Day d : mTrain.getDays()) {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(d.getDayCode().substring(0, 1) + " ");
            if (d.getRun().equals("Y")) {
                tv.setTextColor(Color.WHITE);
            } else {
                tv.setTextColor(Color.BLACK);
            }
            mDaysLayout.addView(tv);
        }

//        ArrayList<String> classes = new ArrayList<>();
//        for (TrainClass c : train.getClasses()) {
//            if (c.getAvailable().equals("Y")) {
//                classes.add("CLASS - " + c.getClassCode());
//            }
//        }
//
//        mClassNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classes);
//        mClassNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mClassSpinner.setAdapter(mClassNameAdapter);
//
//        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String s = parent.getItemAtPosition(position).toString();
//                String strToDisplay = s.substring(8);
//                Toast.makeText(parent.getContext(), strToDisplay, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private void addChoices() {
        mChoiceAdapter.add(new Choice("Availability", R.drawable.ic_check_black_24dp, AvailabilityActivity.class));
        mChoiceAdapter.add(new Choice("Route", R.drawable.ic_polymer_black_24dp, RouteActivity.class));
        mChoiceAdapter.add(new Choice("Running Status", R.drawable.running_status, RunningStatusActivity.class));
        mChoiceAdapter.add(new Choice("Fare", R.drawable.get_fare, FareActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (mBackClass == 0) {
            startActivity(new Intent(getApplicationContext(), TrainListActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), TrainsByNoActivity.class));
        }
    }
}
