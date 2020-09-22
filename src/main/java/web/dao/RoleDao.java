package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
}
