package com.lightblog.context;

import com.lightblog.entity.User;

public final class AuthContext {
    private static final ThreadLocal<User> CURRENT = new ThreadLocal<User>();

    private AuthContext() {
    }

    public static void set(User user) {
        CURRENT.set(user);
    }

    public static User get() {
        return CURRENT.get();
    }

    public static Long userId() {
        User user = get();
        return user == null ? null : user.id;
    }

    public static boolean isAdmin() {
        User user = get();
        return user != null && "admin".equals(user.role);
    }

    public static void clear() {
        CURRENT.remove();
    }
}
