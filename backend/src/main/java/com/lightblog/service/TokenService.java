package com.lightblog.service;

import com.lightblog.entity.User;
import com.lightblog.mapper.OnlineLogMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final Map<String, User> sessions = new ConcurrentHashMap<String, User>();
    private final OnlineLogMapper onlineLogMapper;

    public TokenService(OnlineLogMapper onlineLogMapper) {
        this.onlineLogMapper = onlineLogMapper;
    }

    public String createSession(User user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        user.password = null;
        sessions.put(token, user);
        onlineLogMapper.insertLogin(user.id);
        return token;
    }

    public User getUser(String token) {
        return sessions.get(token);
    }

    public void touch(String token) {
        User user = sessions.get(token);
        if (user != null) {
            onlineLogMapper.touch(user.id);
        }
    }

    public void logout(String token) {
        User user = sessions.remove(token);
        if (user != null) {
            onlineLogMapper.logout(user.id);
        }
    }
}
