package in.railish.railish.utils;

/**
 * class to contain all constants
 */
public class Constants {
    public static final int SPLASH_TIME_OUT = 3000; // splash screen server time

    public static final String METHOD_PNR_STATUS = "pnr_status";
    public static final String METHOD_STATION_AUTOCOMPLETE = "station_autocomplete";

    public static final String PNR_STATUS_REQUEST_URL = "http://himanshuiitkgp.webuda.com/railish/get_pnr.php";
    public static final String STATION_AUTOCOMPLETE_REQUEST_URL = "http://himanshuiitkgp.webuda.com/railish/station_autocomplete_suggest.php/";
    public static final String GET_TRAINS_BETWEEN_STATIONS = "http://himanshuiitkgp.webuda.com/railish/get_trains_between_stations.php/";
    public static final String PREFS_NAME = "my_prefs";
    public static final String TRAIN_LIST_JSON_STRING = "train_list_json_string";
    public static final String TRAIN_INDEX_STRING = "train_index";
    public static final String TRAIN_ACTIVITY_BACK_ACTIVITY = "train_activity_back_activity";
    public static final String TRAIN_JSON_STRING_STRING = "train_json_string_string";
    public static final String TRAIN_ROUTE_JSON_STRING_STRING = "train_route_json_string_string";
    public static final String DOJ_STRING = "doj_string";


    public static final String[] quotaCodes = {"GN", "TQ", "PT", "LD", "DF", "FT",
            "DP", "HP", "PH", "SS", "YU", "LB"};

    public static final String[] quotaNames = {"General", "Tatkal", "Premium Tatkal", "Ladies", "Defence", "Foreign Tourist",
            "Duty Pass", "Physically Handicapped", "Parliament House", "Senior Citizen", "Yuva", "Lower Berth"};
}
