package fi.shoppingOnline.InventoryService.Service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.shoppingOnline.InventoryService.Dto.InventoryResponse;
import fi.shoppingOnline.InventoryService.Repository.InventoryRepository;
import fi.shoppingOnline.InventoryService.model.InventoryModel;
import fi.shoppingOnline.InventoryService.model.InventoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> barcodes) {
        log.info("Checking Inventory Status");
        return inventoryRepository.findByBarcodeIn(barcodes).stream()
                .map(this::mapToInventoryResponse)
                .toList();
    }

    private InventoryResponse mapToInventoryResponse(InventoryModel item) {
        return InventoryResponse.builder()
                .barcode(item.getBarcode())
                .isInstock(item.getQuantity() > 0)
                .build();
    }

    @Transactional
    public String inventoryNewItem(InventoryRequest inventoryRequest) {
        List<InventoryModel> present = inventoryRepository.findByBarcode(inventoryRequest.getBarcode());
        
        if (present.isEmpty()) {
            InventoryModel newInventoryModel = mapToInventoryRequest(inventoryRequest);
            inventoryRepository.save(newInventoryModel);
            return "New Product is Saved";
        } else {
            InventoryModel existingItem = present.get(0);
            existingItem.setQuantity(existingItem.getQuantity() + inventoryRequest.getQuantity());
            inventoryRepository.save(existingItem);
            return "Quantity of the Product is increased";
        }
    }

    private InventoryModel mapToInventoryRequest(InventoryRequest inventoryRequest) {
        InventoryModel inventoryModel = new InventoryModel();
        inventoryModel.setBarcode(inventoryRequest.getBarcode());
        inventoryModel.setName(inventoryRequest.getName());
        inventoryModel.setQuantity(inventoryRequest.getQuantity());
        return inventoryModel;
    }
}
