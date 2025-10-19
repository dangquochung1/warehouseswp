package service;

import dal.*;
import model.*;
import java.util.*;

public class RackLotService {
    private RackDAO rackDAO;
    private RackLotDAO rackLotDAO;
    private LotDetailDAO lotDetailDAO;

    private final int MAX_CAPACITY = 50;

    public RackLotService() {
        this.rackDAO = new RackDAO();
        this.rackLotDAO = new RackLotDAO();
        this.lotDetailDAO = new LotDetailDAO();
    }

    // 🟢 Lớp tạm giữ trạng thái rack trong quá trình phân bổ
    static class TempRack {
        String rackId;
        int currentQuantity;
        List<LotDetail> lots = new ArrayList<>();

        TempRack(String rackId, int currentQuantity) {
            this.rackId = rackId;
            this.currentQuantity = currentQuantity;
        }

        boolean canAdd(int qty) {
            return currentQuantity + qty <= 50;
        }

        boolean isFull() {
            return currentQuantity >= 50;
        }

        LotDetail addLot(LotDetail lot) {
            int qty = lot.getUnarrangedRemaining(); // ⚙️ dùng cột mới

            if (canAdd(qty)) {
                lots.add(new LotDetail(
                        lot.getLotDetailId(),
                        lot.getLotId(),
                        lot.getProductId(),
                        lot.getPurchasePrice(),
                        0,
                        lot.getRemaining(),           // trạng thái
                        qty
                ));
                currentQuantity += qty;
                return null;
            } else {
                int canTake = 50 - currentQuantity;
                lots.add(new LotDetail(
                        lot.getLotDetailId(),
                        lot.getLotId(),
                        lot.getProductId(),
                        lot.getPurchasePrice(),
                        0,
                        lot.getRemaining(),
                        canTake
                ));
                currentQuantity = 50;

                int remain = qty - canTake;
                return new LotDetail(
                        lot.getLotDetailId(),
                        lot.getLotId(),
                        lot.getProductId(),
                        lot.getPurchasePrice(),
                        0,
                        lot.getRemaining(),
                        remain
                );
            }
        }
    }

    // 🧠 Phân bổ LotDetail vào rack
    public void autoDistribute(String warehouseId, String lotDetailId) throws Exception {
        LotDetail lotDetail = lotDetailDAO.getLotDetailById(lotDetailId);
        if (lotDetail == null) {
            throw new Exception("Không tìm thấy LotDetail: " + lotDetailId);
        }

        List<Rack> racks = rackDAO.getRacksByWarehouseId(warehouseId);
        List<TempRack> tempRacks = new ArrayList<>();
        for (Rack r : racks) {
            tempRacks.add(new TempRack(r.getRackId(), r.getSum()));
        }

        LotDetail current = lotDetail;
        while (current != null && current.getUnarrangedRemaining() > 0) {
            boolean placed = false;

            for (TempRack rack : tempRacks) {
                if (rack.isFull()) continue;
                LotDetail remaining = rack.addLot(current);
                if (remaining == null) {
                    placed = true;
                    current = null;
                    break;
                } else {
                    current = remaining;
                }
            }

            // Không rack nào chứa được → tạo mới
            if (!placed && current != null && current.getUnarrangedRemaining() > 0) {
                String newRackId = createNewRack(warehouseId);
                TempRack newRack = new TempRack(newRackId, 0);
                tempRacks.add(newRack);
                current = newRack.addLot(current);
            }
        }

        // Insert racklot
        for (TempRack rack : tempRacks) {
            for (LotDetail ld : rack.lots) {
                RackLot rl = new RackLot();
                rl.setRacklotId(rackLotDAO.getNextRacklotId());
                rl.setRackId(rack.rackId);
                rl.setLotdetailId(ld.getLotDetailId());
                rl.setQuantity(ld.getUnarrangedRemaining()); // ⚙️ cột mới
                rackLotDAO.insertRackLot(rl);
            }
        }

        // Cập nhật lại lotdetail sau khi phân xong
        lotDetail.setUnarrangedRemaining(0); // đã sắp hết
        lotDetailDAO.updateLotDetail(lotDetail);
    }

    private String createNewRack(String warehouseId) {
        return "TEMP-" + System.currentTimeMillis();
    }
}
