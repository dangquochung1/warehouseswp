package model;

import java.util.ArrayList;
import java.util.List;

public class ProductWithWarehouses extends Product {
    private List<WarehouseStock> warehouses = new ArrayList<>();
    public List<WarehouseStock> getWarehouses() { return warehouses; }
    public void setWarehouses(List<WarehouseStock> warehouses) { this.warehouses = warehouses; }
}
