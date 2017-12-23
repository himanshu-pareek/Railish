package in.railish.railish.models;

import java.util.ArrayList;

public class Route {
    private int mResponseCode;
    private ArrayList<Station> mStations;
    private Train mTrain;

    public Route(int responseCode, ArrayList<Station> stations, Train train) {
        mResponseCode = responseCode;
        mStations = stations;
        mTrain = train;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public ArrayList<Station> getStations() {
        return mStations;
    }

    public Train getTrain() {
        return mTrain;
    }
}
