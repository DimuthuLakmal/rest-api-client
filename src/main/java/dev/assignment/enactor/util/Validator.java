package dev.assignment.enactor.util;

import dev.assignment.enactor.config.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private final static Logger logger =  Logger.getLogger(Validator.class.getName());

    public Validator() {
    }

    public boolean isDataValid(int restApi, String date, int passengerCount, String startStop, String endStop,
                               int threadCount) {
        boolean isValid = true;
        if(restApi > 2 || restApi < 1)
        {
            System.out.println(Constants.REST_API_ERROR);
            logger.log(Level.WARNING, Constants.REST_API_ERROR);
            isValid = false;
        }

        if(startStop.equals(endStop)) {
            System.out.println(Constants.BUS_STOPS_ERROR);
            logger.log(Level.WARNING, Constants.BUS_STOPS_ERROR);
            isValid = false;
        }

        if(!(startStop.equals("A") || startStop.equals("B") || startStop.equals("C") || startStop.equals("D"))) {
            System.out.println(Constants.START_STOPS_ERROR);
            logger.log(Level.WARNING, Constants.START_STOPS_ERROR);
            isValid = false;
        }

        if(!(endStop.equals("A") || endStop.equals("B") || endStop.equals("C") || endStop.equals("D"))) {
            System.out.println(Constants.END_STOPS_ERROR);
            logger.log(Level.WARNING, Constants.END_STOPS_ERROR);
            isValid = false;
        }

        if(passengerCount <= 0) {
            System.out.println(Constants.PASSENGER_ERROR);
            logger.log(Level.WARNING, Constants.PASSENGER_ERROR);
            isValid = false;
        }

        if(threadCount <= 0) {
            System.out.println(Constants.THREAD_ERROR);
            logger.log(Level.WARNING, Constants.THREAD_ERROR);
            isValid = false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date bookedDate = sdf.parse(date);
            Date today = new Date();
            if(bookedDate.before(today)) {
                System.out.println(Constants.DATE_ERROR);
                logger.log(Level.WARNING, Constants.DATE_ERROR);
                isValid = false;
            }
        } catch (ParseException e) {
            System.out.println(Constants.DATE_ERROR);
            logger.log(Level.WARNING, Constants.DATE_ERROR);
            isValid = false;
        }

        return isValid;
    }

    public boolean areNamesEmailsValid(ArrayList<String> names, ArrayList<String> emails) {
        boolean isValid = true;

        for(int i=0; i<names.size(); i++) {
            String name = names.get(i);
            String email = emails.get(i);

            if(name.equals("")) {
                System.out.println(String.format(Constants.NAME_ERROR, (i+1)));
                logger.log(Level.WARNING, String.format(Constants.NAME_ERROR, (i+1)));
                isValid = false;
                break;
            }

            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if(!matcher.matches()) {
                System.out.println(String.format(Constants.EMAIL_ERROR, (i+1)));
                logger.log(Level.WARNING, String.format(Constants.EMAIL_ERROR, (i+1)));
                isValid = false;
                break;
            }
        }

        return isValid;
    }
}
