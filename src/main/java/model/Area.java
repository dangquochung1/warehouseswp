package model;

public class Area {
    private String areaId;
    private String warehouseId;
    private String name;
    private String description;

    public Area() {}

    public Area(String areaId, String name, String warehouseId) {
        this.areaId = areaId;
        this.name = name;
        this.warehouseId = warehouseId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
