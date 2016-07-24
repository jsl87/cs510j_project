package edu.pdx.cs410J.jsl;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * <code>PrettyPrinter</code> is a class that implements {@link AppointmentBookDumper} interface.
 * The purpose of this class is to print information regarding an appointment book and appointments
 * in a form that a human can read easily compared to {@link TextDumper} class.
 *
 * @author Jong Seong Lee
 * @version   %I%, %G%
 * @since     1.0
 */
public class PrettyPrinter implements AppointmentBookDumper {

    private PrintWriter printWriter = null;

    public PrettyPrinter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    /**
     * Overrides <code>dump</code> method of {@link AppointmentBookDumper}.
     * This method will take an instance of {@link AppointmentBook} class
     * and will print this information out to a specified output source.
     *
     * @param abstractAppointmentBook   an instance of {@link AppointmentBook} class
     * @throws IOException              an exception will be thrown if IO problem happens
     */
    @Override
    public void dump(AbstractAppointmentBook abstractAppointmentBook) throws IOException {

        if (abstractAppointmentBook instanceof AppointmentBook) {
            dumpToFile((AppointmentBook)abstractAppointmentBook);
        }
    }

    /**
     * Dumps information of an appointment book to a file or a standard output stream.
     * The printing format will be easier to read compared to the <code>dump</code> function of
     * {@link TextDumper} class.
     *
     * @param appointmentBook   an instance of {@link AppointmentBook} class
     * @throws IOException      an exception will be thrown if IO problem happens
     */
    private void dumpToFile(AppointmentBook appointmentBook) throws IOException {

        String ownerName = appointmentBook.getOwnerName();
        List<Appointment> listOfAppointments = appointmentBook.getAppointments();
        Collections.sort(listOfAppointments);

        int i = 1;

        printWriter.println("1. Appointment Book Information");
        printWriter.println(" 1) Owner Name: " + ownerName + "\n");
        printWriter.println("2. Appointments");

        for (Appointment app: listOfAppointments) {
            printWriter.println(" " + i++ + ") Appointment: " + app.getDescription());
            printWriter.println("    Begin Time:  " + DateUtility.parseStringToDatePrettyPrint(app.getBeginTime()));
            printWriter.println("    End Time:    " + DateUtility.parseStringToDatePrettyPrint(app.getEndTime()));
            printWriter.println("    Duration:    " + app.getDurationInMinutes() + " Minutes");
        }
        printWriter.close();
    }
}
