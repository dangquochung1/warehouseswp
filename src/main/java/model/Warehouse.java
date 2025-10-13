package model;

public class Warehouse {
    private String warehouseId;
    private String name;
    private String location;
    private String description;

    public Warehouse() {}

    public Warehouse(String warehouseId, String name) {
        this.warehouseId = warehouseId;
        this.name = name;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseId='" + warehouseId + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
