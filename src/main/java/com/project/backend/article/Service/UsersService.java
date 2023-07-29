package com.project.backend.article.Service;

import com.project.backend.article.protocode.Users;

public interface UsersService {
    void publishUserCreated(Users.CreateUserEvent request);

    void publishUserDeleted(Users.DeleteUserEvent request);

    Users.GetGithubStatsResponse getGithubStats(Users.GetGithubStatsRequest request);
}