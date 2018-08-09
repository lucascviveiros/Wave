package app.waveway.Model;

import java.util.Calendar;
import java.util.Date;

public class PostUser {

    private String texto;
    private Date date;

    public PostUser(String texto){
        this.texto = texto;
        this.date = Calendar.getInstance().getTime();
    }

    public PostUser(){

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
