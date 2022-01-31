package dev.assignment.enactor.request;

import dev.assignment.enactor.config.Configuration;
import dev.assignment.enactor.config.Constants;

public class BookingRequest extends Request {

    private String name;
    private String email;

    public BookingRequest(String date, char startStop, char endStop, int passengerCount, String name, String email) {
        super(date, startStop, endStop, passengerCount,
                Configuration.HOST + ':' + Configuration.PORT + '/' + Constants.BOOKING_ROUTE);
        this.name =  name;
        this.email =  email;
    }

    @Override
    public void buildRequestBody() {
        String jsonInput = "{\"date\": \"%s\", \"startStop\": \"%s\", \"endStop\": \"%s\", \"passengerCount\": %d, " +
                "\"email\": \"%s\", \"name\": \"%s\"}";
        String formattedInput = String.format(jsonInput, this.date, this.startStop, this.endStop, this.passengerCount,
                this.email, this.name);
        this.requestBody = formattedInput;
    }
}
