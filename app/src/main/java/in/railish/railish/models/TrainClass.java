package in.railish.railish.models;

public class TrainClass {

    private String mClassCode, mAvailable;

    public TrainClass(String classCode, String available) {
        mClassCode = classCode;
        mAvailable = available;
    }

    public void setClassCode(String classCode) {
        mClassCode = classCode;
    }

    public void setAvailable(String available) {
        mAvailable = available;
    }

    public String getClassCode() {
        return mClassCode;
    }

    public String getAvailable() {
        return mAvailable;
    }

}
