package edu.pdx.cs410J.jsl;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class AppointmentBookServlet extends HttpServlet
{
    private final Map<String, AppointmentBook> appointmentBooks = new HashMap<>();

    public AppointmentBookServlet() {
        createPreCannedAppointmentBook();
    }

    private void createPreCannedAppointmentBook() {
        String owner = "PreCannedOwner";
        AppointmentBook book = new AppointmentBook(owner);
        this.appointmentBooks.put(owner, book);
    }

    /**
     * Handles an HTTP GET request from a client by writing the value of the key
     * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
     * parameter is not specified, all of the key/value pairs are written to the
     * HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter( "owner", request );

        AppointmentBook book = getAppointmentBookForOwner(owner);
        /*
        if (owner != null) {
            writeValue(owner, response);

        } else {
            writeAllMappings(response);
        }
        */
        prettyPrint(book, response.getWriter());

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void prettyPrint(AppointmentBook book, PrintWriter pw) throws IOException {
        PrettyPrinter pretty = new PrettyPrinter(pw);
        pretty.dump(book);
    }

    @VisibleForTesting
    AppointmentBook getAppointmentBookForOwner(String owner) {
        return this.appointmentBooks.get(owner);
    }

    /**
     * Handles an HTTP POST request by storing the key/value pair specified by the
     * "key" and "value" request parameters.  It writes the key/value pair to the
     * HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter("owner", request);

        AppointmentBook book = getAppointmentBookForOwner(owner);

        String description = getParameter("description", request);
        String beginTime = getParameter("beginTime", request);
        String endTime = getParameter("endTime", request);

        Appointment appointment = null;

        try {
            appointment = new Appointment(description, beginTime, endTime);
        } catch (ParseException e) {
            throw new IOException(e.getMessage());
        }

        book.addAppointment(appointment);

        response.setStatus(HttpServletResponse.SC_OK);

        /*
        response.setContentType( "text/plain" );

        String key = getParameter( "key", request );
        if (key == null) {
            missingRequiredParameter(response, "key");
            return;
        }

        String value = getParameter( "value", request );
        if ( value == null) {
            missingRequiredParameter( response, "value" );
            return;
        }

        this.appoitnmentBooks.put(key, value);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.mappedKeyValue(key, value));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
        */
    }

    /**
     * Handles an HTTP DELETE request by removing all key/value pairs.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.appointmentBooks.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allMappingsDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the value of the given key to the HTTP response.
     *
     * The text of the message is formatted with {@link Messages#getMappingCount(int)}
     * and {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeValue( String key, HttpServletResponse response ) throws IOException
    {
        /*
        String value = this.appoitnmentBooks.get(key);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( value != null ? 1 : 0 ));
        pw.println(Messages.formatKeyValuePair(key, value));

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
        */
    }

    /**
     * Writes all of the key/value pairs to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        /*
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(appoitnmentBooks.size()));

        for (Map.Entry<String, String> entry : this.appoitnmentBooks.entrySet()) {
            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue()));
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
        */
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }
    /*
    @VisibleForTesting
    void setValueForKey(String key, String value) {
        this.appoitnmentBooks.put(key, value);
    }
    */
    @VisibleForTesting
    String getValueForKey(String key) {
        //return this.appoitnmentBooks.get(key);
        throw new UnsupportedOperationException("test");
    }
}
