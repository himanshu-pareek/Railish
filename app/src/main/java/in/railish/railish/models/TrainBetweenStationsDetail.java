package in.railish.railish.models;

import java.util.ArrayList;

public class TrainBetweenStationsDetail {

    private int mResponseCode, mTotal;
    private String mError;
    private ArrayList<Train> mTrains;

    public TrainBetweenStationsDetail (int responseCode, String error, int total, ArrayList<Train> trains) {
        mResponseCode = responseCode;
        mError = error;
        mTotal = total;
        mTrains = trains;
    }

    public int getResponseCode() {
        return mResponseCode;
    }
    public String getError() {
        return mError;
    }
    public int getTotal() {
        return mTotal;
    }
    public ArrayList<Train> getTrains() {
        return mTrains;
    }
}
