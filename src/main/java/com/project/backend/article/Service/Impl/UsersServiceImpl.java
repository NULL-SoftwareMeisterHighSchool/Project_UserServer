package com.project.backend.article.Service.Impl;

import com.project.backend.article.Service.UsersService;
import com.project.backend.article.entity.UserRanking;
import com.project.backend.article.protocode.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {

    private Map<Integer, Users.GithubStats> githubStatsMap = new HashMap<>();
    private List<UserRanking> userRankings = new ArrayList<>();

    @Override
    public void publishUserCreated(Users.CreateUserEvent request) {
        // 새로운 사용자를 생성하는 경우, 빈 GithubStats 정보를 추가합니다.
        githubStatsMap.put(request.getUserID(), Users.GithubStats.newBuilder().build());
        return;
    }

    @Override
    public void publishUserDeleted(Users.DeleteUserEvent request) {
        githubStatsMap.remove(request.getUserID());
        return;
    }

    @Override
    public Users.GetGithubStatsResponse getGithubStats(Users.GetGithubStatsRequest request) {
        List<Users.GithubStats> githubStatsList = new ArrayList<>();
        for (Users.UserInfo user : request.getUsersList()) {
            int userID = user.getUserID();
            if (githubStatsMap.containsKey(userID)) {
                githubStatsList.add(githubStatsMap.get(userID));
            } else {
                githubStatsList.add(Users.GithubStats.newBuilder().build());
            }
        }
        return Users.GetGithubStatsResponse.newBuilder().addAllStatElems(githubStatsList).build();
    }

    public void calculateUserRankings() {
        userRankings.clear();
        for (Map.Entry<Integer, Users.GithubStats> entry : githubStatsMap.entrySet()) {
            int userID = entry.getKey();
            Users.GithubStats githubStats = entry.getValue();
            int totalScore = calculateTotalScore(githubStats);
            userRankings.add(new UserRanking(userID, totalScore));
        }
        userRankings.sort((u1, u2) -> Integer.compare(u2.getTotalScore(), u1.getTotalScore()));
    }

    private int calculateTotalScore(Users.GithubStats githubStats) {
        int contributionScore = githubStats.getContributionCount() * 2;
        int starScore = githubStats.getStarCount() * 100;
        int issueScore = githubStats.getIssueCount() * 20;
        int prScore = githubStats.getPullRequestCount() * 10;
        int contributedRepoScore = githubStats.getContributedRepositoryCount() * 15;
        return contributionScore + starScore + issueScore + prScore + contributedRepoScore;
    }

}
