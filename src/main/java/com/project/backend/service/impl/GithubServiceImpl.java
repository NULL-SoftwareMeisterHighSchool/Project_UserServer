package com.project.backend.service.impl;

import com.project.backend.domain.User;
import com.project.backend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class GithubServiceImpl implements com.project.backend.service.GithubService {

    UserService userService;
    User user;

    @Override
    public int[] getmyranking() {
        int user_number = userService.getCount();
        int getcount = 0;

        for ( int i = 0 ; i < user_number ; i++) {
            if ( user.getRankinYn() == "Y") {
                getcount++;
            }
        }

        int[] ranking = new int[getcount];

        int j = 0;
        for ( int i = 0 ; i < user_number ; i++) {
            if ( user.getGithub_link() != null ) {
                ranking[j] = user.getUserIdx();
                j++;
            }
        }

        return ranking;
    }


}
