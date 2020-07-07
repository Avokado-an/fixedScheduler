package com.anton.organizer.dao.implementation;

import com.anton.organizer.dao.UserDao;
import com.anton.organizer.entity.User;
import com.anton.organizer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImplementation implements UserDao {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public User getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    @Override
    public User loadUserByUsername(String s) {
        return repository.getByName(s);
    }


}
