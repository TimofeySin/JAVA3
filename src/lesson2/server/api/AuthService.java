package ru.geekbrains.Jawa2.Lesson8.server.api;

import ru.geekbrains.Jawa2.Lesson8.server.model.Client;

public interface AuthService {
    Client authenticate(String login, String pass);
}
