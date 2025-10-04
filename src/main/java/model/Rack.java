package model;

public class Rack {
    private String rackId;
    private String aisleId;
    private String name;
    private String description;

    public Rack() {}

    public Rack(String rackId, String name, String aisleId) {
        this.rackId = rackId;
        this.name = name;
        this.aisleId = aisleId;
    }

    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getAisleId() {
        return aisleId;
    }

    public void setAisleId(String aisleId) {
        this.aisleId = aisleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
