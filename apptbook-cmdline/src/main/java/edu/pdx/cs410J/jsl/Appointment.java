package edu.pdx.cs410J.jsl;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * The <code>Appointment</code> is the class extended from the {@link AbstractAppointment} class
 * that describes an appointment with description, begin time and end time.
 *
 * @author Jong Seong Lee
 * @version %I%, %G%
 * @since 1.0
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment> {
  private String description = null;
  private String begin_input = null;
  private String end_input = null;

  private Date begin_date = null;
  private Date end_date = null;
  private DateFormat date_format = null;

  /**
   * This constructor takes a list of arguments as a parameter, and the list should have
   * description, begin time, and end time in order as <code>String</code> data type.
   *
   * @param arguments a List collection that contains description, begin time and end time in order
     */
  public Appointment(List<String> arguments) throws ParseException {
    this(arguments.get(0), arguments.get(1), arguments.get(2));
  }

  /**
   * This constructor takes description, begin time and end time in order.
   *
   * @param desc a description of an appointment in string format
   * @param begin a begin time of an appointment in string format
   * @param end an end time of an appointment in string format
     */
  public Appointment(String desc, String begin, String end) throws ParseException {
    description = desc;

    date_format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.ENGLISH);
    begin_date = date_format.parse(begin);
    end_date = date_format.parse(end);

    // keep the initial date time inputs for dumping purpose
    begin_input = begin;
    end_input = end;
  }

  /**
   * Returns a begin time of an instance.
   *
   * @return a begin time
     */
  @Override
  public String getBeginTimeString() {
    return begin_date.toString();
  }

  /**
   * Returns an end time of an instance.
   *
   * @return an end time
     */
  @Override
  public String getEndTimeString() {
    return end_date.toString();
  }

  /**
   * Returns a description of an instance.
   *
   * @return a description
     */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Returns a begin time.
   *
   * @return a begin time
     */
  @Override
  public Date getBeginTime() { return begin_date; }

  /**
   * Returns an end time.
   *
   * @return an end time
     */
  @Override
  public Date getEndTime() { return end_date; }

  /**
   * Returns the initial begin time input.
   *
   * @return a begin time
     */
  public String getBeginTimeInput() { return begin_input; }

  /**
   * Returns the initial end time input.
   *
   * @return an end time
   */
  public String getEndTimeInput() { return end_input; }

  /**
   * Returns a duration of an appointment.
   *
   * @return a duration
     */
  public int getDurationInMinutes() {
    return (int)TimeUnit.MILLISECONDS.toMinutes(end_date.getTime() - begin_date.getTime());
  }

  /**
   * Compares two <code>Appointment</code> object and determine their orders.
   * The order of comparision is begin time, end time, and description.
   *
   * @param appointment another <code>Appointment</code> object
   * @return returns 0 if two objects are equal, -1 if the other object is smaller, otherwise 1
     */
  @Override
  public int compareTo(Appointment appointment) {
    int result = this.getBeginTime().compareTo(appointment.getBeginTime());

    if (result != 0) {
      return result;
    }

    result = this.getEndTime().compareTo(appointment.getEndTime());;

    if (result != 0) {
      return result;
    }

    return this.getDescription().compareTo(appointment.getDescription());
  }
}
