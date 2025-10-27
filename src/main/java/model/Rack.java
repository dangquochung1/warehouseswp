package model;

public class Rack {
    private String rackId;
    private String aisleId;
    private String name;
    private int sum;
    private String description;
    private int maxcapacity;

    public Rack() {}

    public Rack(String rackId, String name, String aisleId) {
        this.rackId = rackId;
        this.name = name;
        this.aisleId = aisleId;
    }

    public Rack(String rackId, String aisleId, String name, int sum, String description, int maxcapacity) {
        this.rackId = rackId;
        this.aisleId = aisleId;
        this.name = name;
        this.sum = sum;
        this.description = description;
        this.maxcapacity = maxcapacity;
    }
    public Rack(String rackId, String aisleId, String name, int sum,String description ) {
        this.rackId = rackId;
        this.aisleId = aisleId;
        this.name = name;
        this.sum = sum;
        this.description = description;
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

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxcapacity() {
        return maxcapacity;
    }

    public void setMaxcapacity(int maxcapacity) {
        this.maxcapacity = maxcapacity;
    }

    @Override
    public String toString() {
        return "Rack{" +
                "rackId='" + rackId + '\'' +
                ", aisleId='" + aisleId + '\'' +
                ", name='" + name + '\'' +
                ", sum=" + sum +
                ", description='" + description + '\'' +
                ", maxcapacity=" + maxcapacity +
                '}';
    }
}
