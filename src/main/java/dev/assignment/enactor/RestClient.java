package dev.assignment.enactor;

import dev.assignment.enactor.config.Configuration;
import dev.assignment.enactor.config.Constants;
import dev.assignment.enactor.request.AvailabilityRequest;
import dev.assignment.enactor.request.BookingRequest;
import dev.assignment.enactor.request.Request;
import dev.assignment.enactor.util.FakeDataGenerator;
import dev.assignment.enactor.util.InputOutputHandler;
import dev.assignment.enactor.util.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClient {

    private static Logger logger = Logger.getLogger(RestClient.class.getName());
    private static ArrayList<Thread> threadPool;

    public static void main(String[] args) {
        final InputOutputHandler inputOutputHandler = new InputOutputHandler();
        Validator validator = new Validator();
        while (true) {
            try {
                threadPool = new ArrayList<Thread>();
                inputOutputHandler.setRestApi();
                final int restApi = inputOutputHandler.getRestApi();

                inputOutputHandler.setDate();
                final String date = inputOutputHandler.getDate();

                inputOutputHandler.setStartStop();
                final String startStop = inputOutputHandler.getStartStop();

                inputOutputHandler.setEndStop();
                final String endStop = inputOutputHandler.getEndStop();

                inputOutputHandler.setPassengerCount();
                final int passengerCount = inputOutputHandler.getPassengerCount();

                inputOutputHandler.setThreadCount();
                int threadCount = inputOutputHandler.getThreadCount();
                final CyclicBarrier gate = new CyclicBarrier(threadCount + 1);

                boolean isValid = validator.isDataValid(restApi, date, passengerCount, startStop, endStop, threadCount);
                if (!isValid) {
                    inputOutputHandler.printMessage(Constants.ABORTING_MSG);
                }

                if (isValid && restApi == 1) {
                    inputOutputHandler.printData();
                    boolean confirmed = inputOutputHandler.askConfirmation(Constants.FOLLOWING_DETAILS_MSG);
                    if (confirmed) {
                        for(int i=0; i < threadCount; i++) {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Request request = new AvailabilityRequest(date, startStop.charAt(0),
                                            endStop.charAt(0), passengerCount);
                                    execute(gate, inputOutputHandler, request);
                                }
                            });
                            threadPool.add(t);
                        }

                    } else {
                        inputOutputHandler.printMessage(Constants.ABORTING_MSG);
                    }
                } else if (isValid && restApi == 2) {
                    ArrayList<String> names;
                    ArrayList<String> emails;
                    if(Configuration.GENERATE_FAKE_DATA) {
                        FakeDataGenerator generator = new FakeDataGenerator(threadCount);
                        names = generator.getNames();
                        emails = generator.getEmails();
                    } else {
                        for(int i=0; i < threadCount; i++) {
                            inputOutputHandler.setName(i+1);
                            inputOutputHandler.setEmail(i+1);
                        }
                        names = inputOutputHandler.getNames();
                        emails = inputOutputHandler.getEmails();
                    }

                    isValid = validator.areNamesEmailsValid(names, emails);

                    if (!isValid) {
                        inputOutputHandler.printMessage(Constants.ABORTING_MSG);
                    } else {
                        inputOutputHandler.printData();
                        boolean confirmed = inputOutputHandler.askConfirmation(Constants.FOLLOWING_DETAILS_MSG);
                        if (confirmed) {
                            for(int i=0; i < threadCount; i++) {
                                final String name = names.get(i);
                                final String email = emails.get(i);
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Request request = new BookingRequest(date, startStop.charAt(0),
                                                endStop.charAt(0), passengerCount, name, email);
                                        execute(gate, inputOutputHandler, request);
                                    }
                                });
                                threadPool.add(t);
                            }
                        } else {
                            inputOutputHandler.printMessage(Constants.ABORTING_MSG);
                        }
                    }
                }

                for(Thread t: threadPool) {
                    t.start();
                }
                gate.await();

                for(Thread t: threadPool) {
                    t.join();
                }

                boolean confirmed = inputOutputHandler.askConfirmation(Constants.REQUEST_AGAIN_MSG);
                if (!confirmed) {
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.log(Level.SEVERE, ex.getMessage());
                boolean confirmed = inputOutputHandler.askConfirmation(Constants.WENT_WRONG_MSG + "\n" +
                        Constants.REQUEST_AGAIN_MSG);
                if (!confirmed) {
                    break;
                }
            }
        }
    }

    private static void execute(CyclicBarrier gate, InputOutputHandler inputOutputHandler, Request request) {
        request.buildRequestBody();
        try {
            gate.await();
            inputOutputHandler.printMessage(Constants.MAKING_REQUEST_MSG);
            String response = request.sendRequest();
            inputOutputHandler.printMessage(response);
        } catch (IOException | InterruptedException | BrokenBarrierException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
    }

}
