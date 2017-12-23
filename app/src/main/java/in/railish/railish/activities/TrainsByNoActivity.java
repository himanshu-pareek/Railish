package in.railish.railish.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.railish.railish.R;
import in.railish.railish.utils.Constants;

public class TrainsByNoActivity extends AppCompatActivity {

    private EditText mTrainNumberEditText;
    private Button mSearchTrainButton;

    private String trainJSONString = "{\n" +
            "    \"train\": {\n" +
            "        \"name\": \"POORVA EXPRESS\",\n" +
            "        \"number\": \"12303\",\n" +
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
            "        ]\n" +
            "    },\n" +
            "    \"response_code\": 200\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_by_no);

        mTrainNumberEditText = (EditText) findViewById(R.id.train_number_edit_text_1);
        mSearchTrainButton = (Button) findViewById(R.id.search_train_by_number_button);

        mSearchTrainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(Constants.TRAIN_JSON_STRING_STRING, trainJSONString);
                editor.putInt(Constants.TRAIN_ACTIVITY_BACK_ACTIVITY, 1);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), TrainActivity.class));
            }
        });

    }
}
