package com.scytalys.technikon.repository;

import com.scytalys.technikon.domain.PropertyRepair;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PropertyRepairSpecifications {
    public static Specification<PropertyRepair> dateContains(LocalDate keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("date"), "%" + keyword + "%");
    }

    public static Specification<PropertyRepair> firstDateContains(LocalDate keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstDate"), "%" + keyword + "%");
    }

    public static Specification<PropertyRepair> lastDateContains(LocalDate minPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("lastDate"),  minPrice );
    }
}
