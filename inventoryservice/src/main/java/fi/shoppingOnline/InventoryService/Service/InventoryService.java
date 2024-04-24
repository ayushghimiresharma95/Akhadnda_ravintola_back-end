package fi.shoppingOnline.InventoryService.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.shoppingOnline.InventoryService.Dto.InventoryResponse;
import fi.shoppingOnline.InventoryService.Repository.InventoryRepository;
import fi.shoppingOnline.InventoryService.model.InventoryModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository ;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> barcodes){
        log.info("Checking Inventory Status");
        return inventoryRepository.findByBarcodeIn(barcodes).stream().map(item -> MapToInventory(item)).toList() ;
    }

    private InventoryResponse MapToInventory(InventoryModel item){

        return InventoryResponse.builder().barcode(item.getBarcode()).isInstock(item.getQuantity()>0).build() ;
    }

}

