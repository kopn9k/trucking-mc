package com.po.trucking.user.repository;

import com.po.trucking.user.model.Role;
import com.po.trucking.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAllByRole(Role role);
    User findByEmail(String email);
    Page<User> findAllByCarrierId(Pageable pageable, Long carrierId);
    Optional<User> findByIdAndCarrierId(long id, Long carrierId);
    void deleteByIdAndCarrierId (long id, Long carrierId);
}
