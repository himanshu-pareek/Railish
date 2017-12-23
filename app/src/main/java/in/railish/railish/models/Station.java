package in.railish.railish.models;


public class Station {

    private String mCode, mName, mSchDep, mState, mSchArr;
    private int mHalt, mRoute, mDay, mNo, mDistance;
    double mLng, mLat;

    public Station(String code, String name) {
        mCode = code;
        mName = name;
    }

    public Station(String code, String name, String schDep, String state, String schArr,
                   int halt, int route, int day, int no, int distance,
                   double lng, double lat ) {
        mCode = code;
        mName = name;
        mSchDep = schDep;
        mState = state;
        mSchArr = schArr;
        mHalt = halt;
        mRoute = route;
        mDay = day;
        mNo = no;
        mDistance = distance;
        mLng = lng;
        mLat = lat;
    }

    public String getCode() {
        return mCode;
    }
    public String getName() {
        return mName;
    }
    public String getSchDep() {
        return mSchDep;
    }
    public String getState() {
        return mState;
    }
    public String getSchArr() {
        return mSchArr;
    }

    public int getHalt() {
        return mHalt;
    }
    public int getRoute() {
        return mRoute;
    }
    public int getDay() {
        return mDay;
    }
    public int getNo() {
        return mNo;
    }
    public int getDistance() {
        return mDistance;
    }

    public double getLng() {
        return mLng;
    }
    public double getLat() {
        return mLat;
    }

    @Override
    public String toString() {
        return mName;
    }
}
