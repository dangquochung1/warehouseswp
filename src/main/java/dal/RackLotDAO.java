package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.RackLot;
public class RackLotDAO extends DBContext {
    private static final int MAX_PER_RACK = 50;

    public boolean assignToRacks(String lotdetailId, int totalQuantity, String[] rackIds){
        int remaining = totalQuantity;
        return true;
    }

}
