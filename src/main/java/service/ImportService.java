package service;

import dao.ImportReceiptItemDAO;
import dao.InventoryItemDAO;
import java.security.SecureRandom;
import java.util.List;
import model.ImportReceiptItem;
import model.InventoryItem;

/**
 * Nghiệp vụ liên quan đến phiếu nhập & sinh tồn kho.
 */
public class ImportService {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Sinh bản ghi inventory_items từ tất cả dòng của một phiếu nhập.
     * Mỗi quantity sẽ tạo ra quantity record IN_STOCK gắn với receipt_item_id tương ứng.
     * IMEI ở đây được auto-generate đơn giản, nếu cần IMEI thật thì thay thế ở tầng controller/UI.
     */
    public int generateInventoryFromReceipt(int receiptId) {
        ImportReceiptItemDAO itemDao = new ImportReceiptItemDAO();
        InventoryItemDAO inventoryDao = new InventoryItemDAO();

        List<ImportReceiptItem> items = itemDao.getItemsByReceiptId(receiptId);
        int created = 0;

        for (ImportReceiptItem it : items) {
            int qtyToCreate = it.getQuantity() - inventoryDao.countByReceiptItemId(it.getReceipt_item_id());
            for (int i = 0; i < qtyToCreate; i++) {
                String imei = generateUniqueImei(inventoryDao);
                InventoryItem inv = new InventoryItem(
                        0,
                        it.getVariant_id(),
                        it.getReceipt_item_id(),
                        imei,
                        it.getImport_price(),
                        "IN_STOCK"
                );
                if (inventoryDao.insertInventory(inv)) {
                    created++;
                }
            }
        }
        return created;
    }

    private String generateUniqueImei(InventoryItemDAO inventoryDao) {
        String imei;
        do {
            StringBuilder sb = new StringBuilder("86");
            for (int i = 0; i < 13; i++) {
                sb.append(RANDOM.nextInt(10));
            }
            imei = sb.toString();
        } while (inventoryDao.existsByImei(imei));
        return imei;
    }
}

