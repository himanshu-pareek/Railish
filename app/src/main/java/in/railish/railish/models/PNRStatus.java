package in.railish.railish.models;

import java.util.ArrayList;

public class PNRStatus {

    private int mResponseCode, mTotalPassengers;
    private String mTrainName, mTrainNumber, mPnr, mChartPrepared, mClass;
    private boolean mError;
    private double mFailureRate;
    private String mDoj, mTrainStartDate;
    private Station mFromStation, mBoardingPoint, mToStation, mReservationUpto;
    private ArrayList<Passenger> mPassengers;

    public PNRStatus(
            int responseCode,
            boolean error,
            String trainName,
            String trainNumber,
            String pnr,
            double failureRate,
            String doj,
            String chartPrepared,
            String trainClass,
            int totalPassengers,
            String trainStartDate,
            Station fromStation,
            Station boardingPoint,
            Station toStation,
            Station reservationUpto,
            ArrayList<Passenger> passengers
    ) {
        mResponseCode = responseCode;
        mError = error;
        mTrainName = trainName;
        mTrainNumber = trainNumber;
        mPnr = pnr;
        mFailureRate = failureRate;
        mDoj = doj;
        mChartPrepared = chartPrepared;
        mClass = trainClass;
        mTotalPassengers = totalPassengers;
        mTrainStartDate = trainStartDate;
        mFromStation = fromStation;
        mBoardingPoint = boardingPoint;
        mToStation = toStation;
        mReservationUpto = reservationUpto;
        mPassengers = passengers;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public boolean getError() {
        return mError;
    }

    public String getTrainName() {
        return mTrainName;
    }

    public String getTrainNumber() {
        return mTrainNumber;
    }

    public String getPnr() {
        return mPnr;
    }

    public double getFailureRate() {
        return mFailureRate;
    }

    public String getDoj() {
        return mDoj;
    }

    public String getChartPrepared() {
        return mChartPrepared;
    }

    public String getTrainClass() {
        return mClass;
    }

    public int getTotalPassengers() {
        return mTotalPassengers;
    }

    public String getTrainStartDate() {
        return mTrainStartDate;
    }

    public Station getFromStation() {
        return mFromStation;
    }

    public Station getBoardingPoint() {
        return mBoardingPoint;
    }

    public Station getToStation() {
        return mToStation;
    }

    public Station getReservationUpto() {
        return mReservationUpto;
    }

    public ArrayList<Passenger> getPassengers() {
        return mPassengers;
    }

}
