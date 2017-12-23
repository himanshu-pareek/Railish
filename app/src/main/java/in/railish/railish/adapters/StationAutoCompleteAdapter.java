package in.railish.railish.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.railish.railish.R;
import in.railish.railish.models.Station;
import in.railish.railish.utils.Constants;
import in.railish.railish.utils.QueryUtils;

public class StationAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<Station> resultList = new ArrayList<>();
    private ArrayList<Station> mStations = new ArrayList<>();
    private RequestQueue mRequestQueue;

    public StationAutoCompleteAdapter (Context context, RequestQueue queue) {
        mContext = context;
        mRequestQueue = queue;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Station getItem (int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate (R.layout.simple_dropdown_item_2line, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.station_name_text_view)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.station_code_text_view)).setText(getItem(position).getCode());

        return convertView;

    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected Filter.FilterResults performFiltering (CharSequence constraint) {
                Filter.FilterResults filterResults = new Filter.FilterResults();
                if (constraint != null) {
                    List<Station> stations = findStations (mContext, constraint.toString());

                    filterResults.values = stations;
                    filterResults.count = stations.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults (CharSequence constraint, Filter.FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Station>) results.values;
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<Station> findStations (Context context, final String stationTitle) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.STATION_AUTOCOMPLETE_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Response auto", response);
                        try {
                            mStations = QueryUtils.parseStationsFromAutoCompleteJSON(response);
                        } catch (JSONException e) {
                            mStations = null;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Error", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // return super.getParams();
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", stationTitle);
                return params;
            }
        };

        mRequestQueue.add(stringRequest);
        return mStations;
    }
}
