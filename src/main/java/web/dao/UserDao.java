package web.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
}
