package com.example.democrat.data;

import com.example.democrat.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves LoggedInUser information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String LoggedInUsername, String password) {

        try {
            // TODO: handle loggedInLoggedInUser authentication
            LoggedInUser fakeLoggedInUser =
                    new LoggedInUser(
                            0,
                            "Jane",
                            "Doe",
                            "photo",
                            true);
            return new Result.Success<>(fakeLoggedInUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}