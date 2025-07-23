package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(nativeQuery = true, value = "SELECT tb_user.email AS USERNAME, tb_user.password, tb_roles.id AS ROLE_ID, tb_roles.authority " +
            "FROM tb_user " +
            "INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id " +
            "INNER JOIN tb_roles ON tb_user_role.role_id = tb_roles.id " +
            "WHERE tb_user.email = :email")
    List<UserDetailsProjection> getUserByEmailWithRole(String email);

    Optional<User> findUserByEmail(String email);
}
