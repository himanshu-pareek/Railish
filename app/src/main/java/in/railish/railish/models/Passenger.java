package in.railish.railish.models;

public class Passenger {

    private int mNo, mCoachPosition;
    private String mBookingStatus, mCurrentStatus;

    public Passenger(int no, String bookingStatus, String currentStatus, int coachPosition) {
        mNo = no;
        mBookingStatus = bookingStatus;
        mCurrentStatus = currentStatus;
        mCoachPosition = coachPosition;
    }

    public int getNo() { return mNo; }
    public int getCoachPosition() { return mCoachPosition; }
    public String getBookingStatus() { return mBookingStatus; }
    public String getCurrentStatus() { return mCurrentStatus; }

}
