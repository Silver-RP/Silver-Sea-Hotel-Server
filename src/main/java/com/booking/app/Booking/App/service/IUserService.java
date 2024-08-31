package com.booking.app.Booking.App.service;

import com.booking.app.Booking.App.model.User;

import java.util.List;

public interface IUserService {
     abstract User reggisterUser(User user);
     List<User> getUsers();
     void deleteUser(String email);
     User getUser(String email);
}
