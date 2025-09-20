package com.charllson.inventory_service.service;

import com.charllson.inventory_service.dto.InventoryResponse;
import com.charllson.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows //do not use this in production, it is only for testing purpose
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        //delaying this call for testing purpose
        log.info("Wait stated in inventory service");
        Thread.sleep(10000);
        log.info("Wait ended in inventory service");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
