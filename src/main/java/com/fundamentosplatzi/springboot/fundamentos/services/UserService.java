package com.fundamentosplatzi.springboot.fundamentos.services;

import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.transaction.Transactional;
import java.util.List;

public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveTransactional(List<User> users){
        users.stream()
                .peek(user -> LOG.info("Usuario Insertado: " + user))
                .forEach(userRepository::save);
                //.forEach(user -> userRepository.save(user));
    }
    public  List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public void delete(Long id) {
        userRepository.delete(new User(id));
    }
    public User update(User newUser,Long id) {
        return userRepository.findById(id)

                .map(
                        user -> {
                            user.setEmail(newUser.getEmail());
                            user.setBirthDate();
                            user.setName(newUser.getName());
                            return userRepository.save(user);
                        }
                ).get();
    }
}
