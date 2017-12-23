package in.railish.railish.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.railish.railish.R;
import in.railish.railish.adapters.TrainAdapter;
import in.railish.railish.models.Train;
import in.railish.railish.models.TrainBetweenStationsDetail;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class TrainListActivity extends AppCompatActivity implements View.OnClickListener {

    private TrainAdapter mTrainAdapter;
    private TrainBetweenStationsDetail mTrainsDetail;
    private Button mPredictTrainsButton;
    private long mDOJTime;
    // private AdapterView.OnItemClickListener mTrainClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        String jsonString = settings.getString(Constants.TRAIN_LIST_JSON_STRING, "");
        mDOJTime = settings.getLong(Constants.DOJ_STRING, 0);

        mTrainAdapter = new TrainAdapter(this, new ArrayList<Train>());
        ListView trainList = (ListView) findViewById(R.id.list_trains1);

        trainList.setAdapter(mTrainAdapter);

        mTrainsDetail = QueryUtils.parseTrainBetweenStationDetailFromJSONString(jsonString);
        mTrainAdapter.addAll(mTrainsDetail.getTrains());

        String labelString = "";
        Train t = mTrainsDetail.getTrains().get(0);
        if (t != null) {
            labelString += t.getFromStation().getCode() + " - " + t.getToStation().getCode() + "  ";
        }
        if (mDOJTime != 0) {
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
            labelString += "( " +  format.format(new Date(mDOJTime)) + " )";
        }
        if (labelString.equals("")) {
            labelString = "List of Trains";
        }

        setTitle(labelString);

        mPredictTrainsButton = (Button) findViewById(R.id.get_predictions_for_trains_button);
        mPredictTrainsButton.setOnClickListener(this);

        trainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Train t = mTrainAdapter.getItem(position);
                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(Constants.TRAIN_INDEX_STRING, t.getNo());
                editor.putInt(Constants.TRAIN_ACTIVITY_BACK_ACTIVITY, 0);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), TrainActivity.class));
            }
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_predictions_for_trains_button:
                startActivity(new Intent(this, ChooseBDActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SearchTrainsActivity.class));
    }
}
