package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository {
    @Query(nativeQuery = true, value = "SELECT tb_user.email AS USERNAME, tb_role.id AS ROLE_ID, tb.authority " +
            "FROM tb_user " +
            "INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id " +
            "INNER JOIN tb_role ON tb_user_role.role_id = tb_role.id " +
            "WHERE tb_user.email = :email")
    List<UserDetailsProjection> getUserByEmailWithRole(String email);

}
