package dev.assignment.enactor.util;

import dev.assignment.enactor.config.Constants;

import java.util.ArrayList;
import java.util.Scanner;

public class InputOutputHandler {
    private Scanner in;
    private int restApi;
    private String date;
    private String startStop;
    private String endStop;
    private int passengerCount;
    private ArrayList<String> names;
    private ArrayList<String> emails;
    private int threadCount;

    public InputOutputHandler() {
        this.names = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.in = new Scanner(System.in);
    }

    public void setRestApi() throws NumberFormatException {
        System.out.println(Constants.SELECT_API_MSG);
        System.out.println(Constants.AVAILABILITY_SELECTION_MSG);
        System.out.println(Constants.BOOKING_SELECTION_MSG);
        System.out.println(Constants.SELECTION_INSTRUCTION_MSG);
        this.restApi = Integer.parseInt(in.nextLine());
    }

    public int getRestApi() {
        return restApi;
    }

    public void setDate(){
        System.out.println(Constants.ENTER_DATE_MSG);
        this.date = in.nextLine().trim();
    }

    public String getDate() {
        return date;
    }

    public void setStartStop() {
        System.out.println(Constants.ENTER_START_STOP_MSG);
        this.startStop = in.nextLine().toUpperCase().trim();
    }

    public String getStartStop() {
        return startStop;
    }

    public void setEndStop() {
        System.out.println(Constants.ENTER_END_STOP_MSG);
        this.endStop = in.nextLine().toUpperCase().trim();
    }

    public String getEndStop() {
        return endStop;
    }

    public void setPassengerCount() throws NumberFormatException {
        System.out.println(Constants.ENTER_PASSENGER_MSG);
        this.passengerCount = Integer.parseInt(in.nextLine());
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setName(int index) {
        System.out.println(String.format(Constants.ENTER_NAME_MSG, index));
        this.names.add(in.nextLine().trim());
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setEmail(int index) {
        System.out.println(String.format(Constants.ENTER_EMAIL_MSG, index));
        this.emails.add(in.nextLine().trim());
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setThreadCount() throws NumberFormatException{
        System.out.println(Constants.ENTER_THREAD_COUNT);
        this.threadCount = Integer.parseInt(in.nextLine());
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void printData() {
        System.out.println("Date: " + date);
        System.out.println("Start Bus Stop: " + startStop);
        System.out.println("Destination Bus Stop: " + endStop);
        System.out.println("Number of Passengers: " + passengerCount);

        if(restApi == 2) {
            for(int i=0; i<names.size(); i++) {
                System.out.println(String.format("Name: %s, Email: %s", names.get(i), emails.get(i)));
            }
        }
    }

    public boolean askConfirmation(String msg) {
        System.out.println(msg);
        System.out.println(Constants.CONFIRMATION_MSG);
        String confirmation = in.nextLine().toLowerCase();
        if(confirmation.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    public void printMessage(String msg) {
        System.out.println(msg);
    }
}
