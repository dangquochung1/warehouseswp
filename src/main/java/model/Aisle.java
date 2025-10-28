package model;

public class Aisle {
    private String aisleId;
    private String areaId;
    private String name;
    private String description;
    private String categoryId;

    public Aisle() {}

    public Aisle(String aisleId, String name, String areaId) {
        this.aisleId = aisleId;
        this.name = name;
        this.areaId = areaId;
    }

    public Aisle(String aisleId, String areaId, String name, String description, String categoryId) {
        this.aisleId = aisleId;
        this.areaId = areaId;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Aisle{" +
                "aisleId='" + aisleId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
