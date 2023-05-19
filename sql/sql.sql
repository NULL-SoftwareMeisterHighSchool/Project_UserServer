CREATE TABLE user (
                      user_idx			    INT				NOT NULL 	AUTO_INCREMENT	COMMENT 'idx',
                      email				    VARCHAR(100)	NOT NULL 	UNIQUE			COMMENT '이메일',
                      nickname			    VARCHAR(100)	NOT NULL					COMMENT '보여지는 이름',
                      user_type			    CHAR(1)										COMMENT 'S : student, G : graduated',
                      id                    VARCHAR(100)    NOT NULL    UNIQUE          COMMENT '로그인용 id',
                      password			    VARCHAR(256)	NOT NULL					COMMENT '비밀번호',
                      grade				    TINYINT			DEFAULT '1' 				COMMENT '1 : student, 7 : graduated, 9 : administrator',
                      school_name		    TINYINT										COMMENT '학교',
                      school_year           INT                                         COMMENT '입학 년도',
                      register_time	    	DATETIME		NOT NULL					COMMENT '등록 시간',
                      approved_yn	    	CHAR(1)			NOT NULL	DEFAULT 'Y'		COMMENT '승인 여부',
                      last_login_time   	DATETIME									COMMENT '마지막 로그인 시간',
                      update_time	    	DATETIME									COMMENT '수정 시간',
                      withdrawed_yn		    CHAR(1)			NOT NULL	DEFAULT 'N'		COMMENT '탈퇴 여부',
                      withdrawed_time		DATETIME									COMMENT '탈퇴 시간',
                      password_update_time  DATETIME                                    COMMENT '비번 최종변경일',
                      mail_auth             CHAR(1)                                     COMMENT '인증 여부',
                      mail_key              VARCHAR(100)                                COMMENT '메일인증키 저장',
                      github_link           VARCHAR(255)                                COMMENT '깃허브 링크',
                      commit_count          INT                                         COMMENT '커밋수',
                      PRIMARY KEY (user_idx)
);

CREATE TABLE schoolauthcode (
                    school_authcode         VARCHAR(100)    NOT NULL    AUTO_INCREMENT COMMENT 'idx',
                    PRIMARY KEY (school_authcode)
);

CREATE TABLE githubranking (
                    ranking_idx             INT             NOT NULL    AUTO_INCREMENT COMMENT 'idx',
                    PRIMARY KEY (ranking_idx)
);