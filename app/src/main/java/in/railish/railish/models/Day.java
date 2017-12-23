package in.railish.railish.models;

public class Day {
    private String mRun, mDayCode;
    public Day (String run, String dayCode) {
        mRun = run;
        mDayCode = dayCode;
    }
    public void setRun(String run) {
        mRun = run;
    }
    public void setDayCode(String dayCode) {
        mDayCode = dayCode;
    }
    public String getRun() {
        return mRun;
    }
    public String getDayCode() {
        return mDayCode;
    }
}
