package Model;

import com.koalap.geofirestore.GeoFire;
import com.koalap.geofirestore.GeoLocation;

import java.util.ArrayList;

public class User {

    private String login_number;
    private String name;
    private GeoLocation geoLocal;

    public User(String login_number, String name, GeoLocation geoLocal) {
        this.login_number = login_number;
        this.name = name;
        this.geoLocal = geoLocal;
    }

    public GeoLocation getGeoLocal() {
        return geoLocal;
    }

    public void setGeoLocal(GeoLocation geoLocal) {
        this.geoLocal = geoLocal;
    }

    public String getLogin_number() {
        return login_number;
    }

    public void setLogin_number(String login_number) {
        this.login_number = login_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
