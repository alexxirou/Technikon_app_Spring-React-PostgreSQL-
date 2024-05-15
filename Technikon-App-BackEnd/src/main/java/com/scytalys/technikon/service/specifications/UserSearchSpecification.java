package com.scytalys.technikon.service.specifications;



import com.scytalys.technikon.domain.PropertyOwner;

import com.scytalys.technikon.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSearchSpecification {
    public static Specification<User> tinContains(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("tin"), "%" + keyword + "%");
    }

    public static Specification<User> usernameContains(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + keyword + "%");
    }

    public static Specification<User> emailContains(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
    }
}
