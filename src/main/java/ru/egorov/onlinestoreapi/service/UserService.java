package ru.egorov.onlinestoreapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.onlinestoreapi.model.User;
import ru.egorov.onlinestoreapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User find(Integer id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public User find(String name) {
        return userRepository.findByName(name)
                .orElseThrow();
    }

    public User update(User user) {

        return userRepository.save(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
