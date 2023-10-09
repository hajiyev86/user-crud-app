package com.example.crudkotlinapi.specification

import com.example.crudkotlinapi.dto.UserSearchPageableRequestDTO
import com.example.crudkotlinapi.entity.User
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class UserSpecification(private val filter: UserSearchPageableRequestDTO) : Specification<User> {

    override fun toPredicate(
            root: Root<User>,
            query: CriteriaQuery<*>,
            cb: CriteriaBuilder
    ): Predicate? {
        val predicates = mutableListOf<Predicate>()

        if (filter.userId != null) {
            predicates.add(cb.equal(root.get<Long>("id"), filter.userId))
        }

        if (!filter.name.isNullOrBlank()) {
            predicates.add(cb.like(cb.lower(root.get<String>("name")), "%" + filter.name?.lowercase() + "%"))
        }

        if (!filter.email.isNullOrBlank()){
            predicates.add(cb.like(cb.lower(root.get<String>("email")), "%" + filter.email?.lowercase() + "%"))
        }

        if (!filter.phoneNumber.isNullOrBlank()) {
            predicates.add(cb.like(cb.lower(root.get<String>("phoneNumber")), "%" + filter.phoneNumber?.lowercase()  + "%"))
        }

        return cb.and(*predicates.toTypedArray())
    }
}
