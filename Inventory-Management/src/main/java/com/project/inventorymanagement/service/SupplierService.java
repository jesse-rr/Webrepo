package com.project.inventorymanagement.service;

import com.project.inventorymanagement.model.Supplier;
import com.project.inventorymanagement.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new EntityNotFoundException("SUPPLIER NOT FOUND WITH ID :: "+supplierId));
    }
}
