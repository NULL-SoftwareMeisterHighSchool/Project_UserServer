package main.java.com.project.backend.user.service.impl;

import com.project.backend.user.domain.User;
import com.project.backend.user.service.GithubService;
import com.project.backend.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class GithubServiceImpl implements GithubService {

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
