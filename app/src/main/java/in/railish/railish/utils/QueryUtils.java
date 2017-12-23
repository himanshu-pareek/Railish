package in.railish.railish.utils;

import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.railish.railish.models.Day;
import in.railish.railish.models.PNRStatus;
import in.railish.railish.models.Passenger;
import in.railish.railish.models.Quota;
import in.railish.railish.models.Route;
import in.railish.railish.models.Station;
import in.railish.railish.models.Train;
import in.railish.railish.models.TrainBetweenStationsDetail;
import in.railish.railish.models.TrainClass;

public class QueryUtils {

    @Nullable
    public static PNRStatus getPnrStatusFromJSONString(String pnrJSONString) throws JSONException {

        PNRStatus pnrStatus;

        JSONObject pnrJSONObject = new JSONObject(pnrJSONString);
        int responseCode = pnrJSONObject.getInt("response_code");
        double failureRate = pnrJSONObject.getDouble("failure_rate");
        if (responseCode == 204) {
            pnrStatus = new PNRStatus(responseCode, true, "", "", "", failureRate, "", "", "", 0, "", new Station("", ""),
                    new Station("", ""), new Station("", ""), new Station("", ""), null);
            return pnrStatus;
        }

        boolean error = pnrJSONObject.getBoolean("error");
        String trainName = pnrJSONObject.getString("train_name");
        String trainNumber = pnrJSONObject.getString("train_num");
        String pnr = pnrJSONObject.getString("pnr");
        String dojString = pnrJSONObject.getString("doj");
        String chartPrepared = pnrJSONObject.getString("chart_prepared");
        String trainClass = pnrJSONObject.getString("class");
        int totalPassengers = pnrJSONObject.getInt("total_passengers");
        JSONObject startDateJSONObject = pnrJSONObject.getJSONObject("train_start_date");

        String startDateString;
        if (!startDateJSONObject.has("day") || !startDateJSONObject.has("month") || !startDateJSONObject.has("year")) {
            startDateString = "";
        } else {
            startDateString = String.valueOf(startDateJSONObject.getInt("day"))
                    + "-" + String.valueOf(startDateJSONObject.getInt("month"))
                    + "-" + String.valueOf(startDateJSONObject.getInt("year"));
        }

        JSONObject fromStationJSONObject = pnrJSONObject.getJSONObject("from_station");
        Station fromStation = new Station(fromStationJSONObject.getString("code"), fromStationJSONObject.getString("name"));

        JSONObject boardingPointJSONObject = pnrJSONObject.getJSONObject("boarding_point");
        Station boardingPoint = new Station(boardingPointJSONObject.getString("code"), boardingPointJSONObject.getString("name"));

        JSONObject toStationJSONObject = pnrJSONObject.getJSONObject("to_station");
        Station toStation = new Station(toStationJSONObject.getString("code"), toStationJSONObject.getString("name"));

        JSONObject reservationUptoJSONObject = pnrJSONObject.getJSONObject("reservation_upto");
        Station reservationUpto = new Station(reservationUptoJSONObject.getString("code"), reservationUptoJSONObject.getString("name"));

        ArrayList<Passenger> passengers;

        if (totalPassengers == 0) {
            passengers = null;
        } else {
            passengers = new ArrayList<>();

            JSONArray passengersJSONArray = pnrJSONObject.getJSONArray("passengers");
            for (int i = 0; i < totalPassengers; i++) {
                JSONObject passengerJSONObject = passengersJSONArray.getJSONObject(i);
                int no = passengerJSONObject.getInt("no");
                String bookingStatus = passengerJSONObject.getString("booking_status");
                String currentStatus = passengerJSONObject.getString("current_status");
                int coachPosition = passengerJSONObject.getInt("coach_position");
                Passenger passenger = new Passenger(no, bookingStatus, currentStatus, coachPosition);
                passengers.add(passenger);
            }
        }

        pnrStatus = new PNRStatus(responseCode, error, trainName, trainNumber,
                pnr, failureRate, dojString, chartPrepared,
                trainClass, totalPassengers, startDateString, fromStation,
                boardingPoint, toStation, reservationUpto, passengers);

        return pnrStatus;
    }

    public static ArrayList<Station> parseStationsFromAutoCompleteJSON (String s) throws JSONException {
        ArrayList<Station> stations = new ArrayList<>();
        JSONObject mainObj  = new JSONObject(s);
        int responseCode = mainObj.getInt("response_code");
        if (responseCode == 200) {
            int totalStations = mainObj.getInt("total");
            JSONArray stationArray = mainObj.getJSONArray("station");
            for (int i = 0; i < totalStations; i++) {
                JSONObject stationObj = stationArray.getJSONObject(i);
                Station st = new Station(stationObj.getString("code"), stationObj.getString("fullname"));
                stations.add(st);
            }
        }
        return stations;
    }

    @Nullable
    public static TrainBetweenStationsDetail parseTrainBetweenStationDetailFromJSONString (String s) {
        TrainBetweenStationsDetail detail = null;
        JSONObject mainObj = null;
        try {
            mainObj = new JSONObject(s);
        } catch (JSONException e) {
            return null;
        }
        try {
            int responseCode = mainObj.getInt("response_code");
            String error = mainObj.getString("error");
            int total = mainObj.getInt("total");
            JSONArray trainArray = mainObj.getJSONArray("train");
            ArrayList<Train> trains = new ArrayList<>();
            for (int i = 0; i < total; i++) {
                JSONObject trainObj = trainArray.getJSONObject(i);
                String destArrivalTime, travelTime, number, srcDepartureTime, name;
                destArrivalTime = trainObj.getString("dest_arrival_time");
                travelTime = trainObj.getString("travel_time");
                number = trainObj.getString("number");
                srcDepartureTime = trainObj.getString("src_departure_time");
                name = trainObj.getString("name");
                // int no = trainObj.getInt("no");
                int no = i;
                JSONObject fromStationObject = trainObj.getJSONObject("from");
                Station fromStation = new Station(fromStationObject.getString("code"), fromStationObject.getString("name"));
                JSONObject toStationObject = trainObj.getJSONObject("to");
                Station toStation = new Station(toStationObject.getString("code"), toStationObject.getString("name"));
                JSONArray classArray = trainObj.getJSONArray("classes");
                ArrayList<TrainClass> classes = new ArrayList<>();
                for (int j = 0; j < classArray.length(); j++) {
                    JSONObject classObject = classArray.getJSONObject(j);
                    TrainClass c = new TrainClass(classObject.getString("class-code"), classObject.getString("available"));
                    classes.add(c);
                }
                JSONArray dayArray = trainObj.getJSONArray("days");
                ArrayList<Day> days = new ArrayList<>();
                for (int k = 0; k < dayArray.length(); k++) {
                    JSONObject dayObject = dayArray.getJSONObject(k);
                    Day day = new Day(dayObject.getString("runs"), dayObject.getString("day-code"));
                    days.add(day);
                }
                Train train = new Train(number, destArrivalTime, no, classes, travelTime, days, srcDepartureTime, name, fromStation, toStation);
                trains.add(train);
            }
            detail = new TrainBetweenStationsDetail(responseCode, error, total, trains);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detail;
    }

    public static Train parseJSONToGetTrain(String s) {
        try {
            JSONObject mainObj = new JSONObject(s);
            if (mainObj.getInt("response_code") != 200) {
                return null;
            }
            JSONObject trainObj = mainObj.getJSONObject("train");
            String name = trainObj.getString("name");
            String number = trainObj.getString("number");
            JSONArray daysArray = trainObj.getJSONArray("days");
            ArrayList<Day> days = new ArrayList<>();
            for (int i = 0; i < daysArray.length(); i++) {
                JSONObject dayObj = daysArray.getJSONObject(i);
                Day d = new Day(dayObj.getString("runs"), dayObj.getString("day-code"));
                days.add(d);
            }
            Train t = new Train(number, null, 0, null, null, days, null, name, null, null);
            return t;
        } catch (JSONException e) {
            return null;
        }
    }

    public static Route getRouteFromJSONString(String s) {
        Route route = null;
        try {
            JSONObject routeObj = new JSONObject(s);
            int responseCode = routeObj.getInt("response_code");
            if (responseCode != 200) {
                return new Route(responseCode, null, null);
            }
            JSONObject trainObj = routeObj.getJSONObject("train");
            String trainName = trainObj.getString("name");
            String trainNumber = trainObj.getString("number");

            JSONArray classArray = trainObj.getJSONArray("classes");
            ArrayList<TrainClass> classes = new ArrayList<>();
            for (int i = 0; i < classArray.length(); i++) {
                JSONObject classObj = classArray.getJSONObject(i);
                TrainClass c = new TrainClass(classObj.getString("class-code"), classObj.getString("available"));
                classes.add(c);
            }

            JSONArray daysArray = trainObj.getJSONArray("days");
            ArrayList<Day> days = new ArrayList<>();
            for (int i = 0; i < daysArray.length(); i++) {
                JSONObject dayObj = daysArray.getJSONObject(i);
                Day d = new Day(dayObj.getString("runs"), dayObj.getString("day-code"));
                days.add(d);
            }

            Train train = new Train(trainNumber, null, 0, classes, null, days, null, trainName, null, null);

            JSONArray stationArray = routeObj.getJSONArray("route");
            ArrayList<Station> stations = new ArrayList<>();
            for (int i = 0; i < stationArray.length(); i++) {
                JSONObject stationObj = stationArray.getJSONObject(i);
                String code = stationObj.getString("code");
                String name = stationObj.getString("fullname");
                String schDep = stationObj.getString("schdep");
                String state = stationObj.getString("state");
                String schArr = stationObj.getString("scharr");

                int halt = stationObj.getInt("halt");
                int r = stationObj.getInt("route");
                int day = stationObj.getInt("day");
                int no = stationObj.getInt("no");
                int distance = stationObj.getInt("distance");

                double lng = stationObj.getDouble("lng");
                double lat = stationObj.getDouble("lat");

                Station station = new Station(code, name, schDep, state, schArr, halt, r, day, no, distance, lng, lat);
                stations.add(station);
            }

            route = new Route(responseCode, stations, train);

        } catch (JSONException e) {
            return null;
        }
        return route;
    }

//    @Nullable
//    public static Station parseStationFromJSONString(String s) {
//        JSONObject stationObject = null;
//        try {
//            stationObject = new JSONObject(s);
//        } catch (JSONException e) {
//            return null;
//        }
//        Station station = null;
//        if (stationObject.has("code") && stationObject.has("name")) {
//            try {
//                station = new Station(stationObject.getString("code"), stationObject.getString("name"));
//            } catch (JSONException e) {
//                return null;
//            }
//        }
//        return station;
//    }

    public static ArrayList<Quota> getQuotaList() {
        ArrayList<Quota> quotas = new ArrayList<Quota>();

        for (int i = 0; i < (Constants.quotaCodes).length; i++) {
            Quota quota = new Quota (Constants.quotaCodes[i], Constants.quotaNames[i]);

            quotas.add(quota);
        }
        return quotas;
    }

}
