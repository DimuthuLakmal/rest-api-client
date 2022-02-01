package dev.assignment.enactor.model;

import dev.assignment.enactor.config.Configuration;
import dev.assignment.enactor.config.Constants;

public class AvailabilityRequest extends Request {

    public AvailabilityRequest(String date, char startStop, char endStop, int passengerCount) {
        super(date, startStop, endStop, passengerCount,
                Configuration.HOST + ':' + Configuration.PORT + '/' + Constants.AVAILABILITY_ROUTE);
    }

    @Override
    public void buildRequestBody() {
        String jsonInput = "{\"date\": \"%s\", \"startStop\": \"%s\", \"endStop\": \"%s\", \"passengerCount\": %d}";
        String formattedInput = String.format(jsonInput, this.date, this.startStop, this.endStop, this.passengerCount);
        this.requestBody = formattedInput;
    }
}
