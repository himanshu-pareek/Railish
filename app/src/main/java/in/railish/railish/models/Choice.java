package in.railish.railish.models;


public class Choice {
    private String mName;
    private int mImageResId;
    private Class mNextClass;

    public Choice(String name, int imageResId, Class nextClass) {
        mName = name;
        mImageResId = imageResId;
        mNextClass = nextClass;
    }

    public String getName() {
        return mName;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public Class getNextClass() {
        return mNextClass;
    }

}
