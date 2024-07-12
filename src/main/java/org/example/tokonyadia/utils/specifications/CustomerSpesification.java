package org.example.tokonyadia.utils.specifications;

import jakarta.persistence.criteria.Predicate;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpesification {
    public static Specification<Customer> getSpesification(CustomerRequest customerRequest) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (customerRequest.getName() !=null) {
                Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+customerRequest.getName().toLowerCase()+"%");
                predicates.add(name);
            }

            if (customerRequest.getPhone() != null) {
                Predicate phone = criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%a%");
                predicates.add(phone);
            }

            if (customerRequest.getAddress() != null) {
                Predicate address = criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%a%");
                predicates.add(address);
            }

            if (customerRequest.getBirthDate() != null) {
                Predicate birthDate = criteriaBuilder.like(root.get("birthDate"), "%"+customerRequest.getBirthDate()+"%");
                predicates.add(birthDate);
            }

            Predicate[] arrPredicates = predicates.toArray(new Predicate[0]);

            return criteriaBuilder.and(arrPredicates);
        });
    }
}
