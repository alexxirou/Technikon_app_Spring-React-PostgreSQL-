package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;

public interface PropertyRepairRepo {
    int updatePropertyRepairExistingFields(PropertyRepair newPropertyRepair, long propertyRepairId);
}
