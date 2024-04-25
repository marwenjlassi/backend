package backendPFE.Request;

public class MatriculeRequest {
    private String series;
    private String location;
    private String registrationNumber;

    // Constructors, getters, and setters
    public MatriculeRequest() {
    }

    public MatriculeRequest(String series, String location, String registrationNumber) {
        this.series = series;
        this.location = location;
        this.registrationNumber = registrationNumber;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
