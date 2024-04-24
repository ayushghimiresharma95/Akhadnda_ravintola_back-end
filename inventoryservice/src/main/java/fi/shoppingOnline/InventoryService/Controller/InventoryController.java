package fi.shoppingOnline.InventoryService.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.shoppingOnline.InventoryService.Dto.InventoryResponse;
import fi.shoppingOnline.InventoryService.Service.InventoryService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor

public class InventoryController {

    private final InventoryService inventoryService ;
    


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public  List<InventoryResponse>  isInStock(@RequestParam("barcodes") List<String> barcodes){
        return inventoryService.isInStock(barcodes) ;
    }
    
}
