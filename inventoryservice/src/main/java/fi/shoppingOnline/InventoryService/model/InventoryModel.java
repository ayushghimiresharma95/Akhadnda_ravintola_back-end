package fi.shoppingOnline.InventoryService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_inventory")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    private String name ; 
    private String barcode ;
    private Integer quantity ;
}
