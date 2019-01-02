package view.util;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class CalDateStrConveter implements Converter 
{
    private String pattern = "EEE MMM dd HH:mm:ss z yyyy";
                //eg 02/02/2012 12:00  (note Rich:calendar has no support for seconds)

    public Object getAsObject(FacesContext context, UIComponent component, String value)
            throws ConverterException {

        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
        if(value!= null && value.length() > 0) {
            try {
                Date date = sdf.parse(value);
                result = sdf.format(date);
            } catch (Exception e) {
                Date date = new Date();
                //logger.error(e.getMessage());
                FacesMessage facesMessage = new FacesMessage("Invalid Date", value + " is an invalid date. Example " + sdf.format(date));
                FacesContext.getCurrentInstance().addMessage("DATE PARSE ERROR", facesMessage);
            }
        }

        return result;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException {

        String result = "";
        String valueStr = value.toString();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        if (valueStr!= null && valueStr.length() > 0) {
            try {
                Date date = sdf.parse(valueStr);
                result = sdf2.format(date);
            } catch (Exception e) {
                //logger.error(e.getMessage());
                Date date = new Date();
                FacesMessage facesMessage = new FacesMessage("Invalid Date", value + " is an invalid date. Example " + sdf.format(date));
                FacesContext.getCurrentInstance().addMessage("DATE PARSE ERROR", facesMessage);
            }
        }
        return result;
    }    
}
