package in.railish.railish.models;

public class Quota {
    private String mQuotaCode;
    private String mQuotaName;

    public Quota (String code, String name) {
        mQuotaCode = code;
        mQuotaName = name;
    }

    public String getQuotaCode() {
        return mQuotaCode;
    }

    public String getQuotaName() {
        return mQuotaName;
    }

    public String toString() {
        return mQuotaCode + " - " + mQuotaName;
    }
}
