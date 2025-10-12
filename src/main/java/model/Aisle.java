package model;

public class Aisle {
    private String aisleId;
    private String areaId;
    private String name;
    private String description;

    public Aisle() {}

    public Aisle(String aisleId, String name, String areaId) {
        this.aisleId = aisleId;
        this.name = name;
        this.areaId = areaId;
    }

    public String getAisleId() {
        return aisleId;
    }

    public void setAisleId(String aisleId) {
        this.aisleId = aisleId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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
    @Override
    public String toString() {
        return "Aisle{" +
                "aisleId='" + aisleId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
