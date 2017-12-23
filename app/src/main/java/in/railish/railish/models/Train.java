package in.railish.railish.models;

import java.util.ArrayList;

public class Train {
    private String mNumber, mDestArrivalTime, mTravelTime, mSrcDepartureTime, mName;
    private int mNo;
    private ArrayList<TrainClass> mClasses;
    private ArrayList<Day> mDays;
    private Station mFromStation, mToStation;

    public Train(
            String number,
            String destArrivalTime,
            int no,
            ArrayList<TrainClass> classes,
            String travelTime,
            ArrayList<Day> days,
            String srcDepartureTime,
            String name,
            Station fromStation,
            Station toStation
    ) {

        mNumber = number;
        mDestArrivalTime = destArrivalTime;
        mNo = no;
        mClasses = classes;
        mTravelTime = travelTime;
        mDays = days;
        mSrcDepartureTime = srcDepartureTime;
        mName = name;
        mFromStation = fromStation;
        mToStation = toStation;

    }

    public String getNumber() {
        return mNumber;
    }
    public String getDestArrivalTime() {
        return mDestArrivalTime;
    }
    public int getNo() {
        return mNo;
    }
    public ArrayList<TrainClass> getClasses() {
        return mClasses;
    }
    public String getTravelTime() {
        return mTravelTime;
    }
    public ArrayList<Day> getDays() {
        return mDays;
    }
    public String getSrcDepartureTime() {
        return mSrcDepartureTime;
    }
    public String getName() {
        return mName;
    }
    public Station getFromStation() {
        return mFromStation;
    }
    public Station getToStation() {
        return mToStation;
    }

}


