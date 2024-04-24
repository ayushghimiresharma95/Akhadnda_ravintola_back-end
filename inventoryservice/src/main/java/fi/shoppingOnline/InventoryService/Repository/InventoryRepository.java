package fi.shoppingOnline.InventoryService.Repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import fi.shoppingOnline.InventoryService.model.InventoryModel;





public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {
  List<InventoryModel> findByBarcodeIn(List<String> barcodes);
}
