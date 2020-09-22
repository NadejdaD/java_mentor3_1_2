package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService{

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(long id);

    User getUserById(long id);

    Role getRoleById(long id);

    void editUser(User user);

}
