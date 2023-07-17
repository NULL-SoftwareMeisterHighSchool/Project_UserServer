create table user (
                      user_idx             int auto_increment comment 'idx' primary key,
                      email                varchar(100)        not null comment '이메일',
                      nickname             varchar(100)        not null comment '보여지는 이름',
                      name                 varchar(255)        not null comment '실제 이름',
                      user_type            char                null comment 'S : student, G : graduated',
                      id                   varchar(100)        not null comment '로그인용 id',
                      password             varchar(256)        not null comment '비밀번호',
                      grade                tinyint default 1   null comment '1 : student, 7 : graduated, 9 : administrator',
                      school_name          tinyint             null comment '학교',
                      school_year          int                 null comment '입학 년도',
                      register_time        datetime            not null comment '등록 시간',
                      approved_yn          char    default 'Y' not null comment '승인 여부',
                      last_login_time      datetime            null comment '마지막 로그인 시간',
                      update_time          datetime            null comment '수정 시간',
                      withdrawed_yn        char    default 'N' not null comment '탈퇴 여부',
                      withdrawed_time      datetime            null comment '탈퇴 시간',
                      userbanned_yn        char    default 'N' not null comment '',
                      password_update_time datetime            null comment '비번 최종변경일',
                      mail_auth            varchar(100)        null comment '사용자가 입력한 인증키',
                      mail_key             varchar(100)        null comment '메일인증키 저장',
                      github_link          varchar(255)        null comment '깃허브 링크',
                      commit_count         int                 null comment '유저 총 커밋수',
                      ranking_yn           char    default 'N' not null comment '유저 랭킹 표시',
                      constraint id
                          unique (id)
);



CREATE TABLE schoolauthcode (
                    school_authcode         VARCHAR(100)    NOT NULL    AUTO_INCREMENT COMMENT 'idx',
                    PRIMARY KEY (school_authcode)
);

CREATE TABLE githubranking (
                    ranking_idx             INT             NOT NULL    AUTO_INCREMENT COMMENT 'idx',
                    PRIMARY KEY (ranking_idx)
);

/*
    ARTICLE
 	for _, article := range articles {
		elems = append(elems, &pb.ListArticleElement{
			ArticleID: uint32(article.ID),
			AuthorID:  uint32(article.AuthorID),
			Title:     article.Title,
			Summary:   article.Summary,
			Type:      pb.ArticleType(article.Type),
			IsPrivate: article.IsPrivate,
			CreatedAt: timestamppb.New(article.CreatedAt),
			Views:     article.Views,
			Likes:     uint32(article.Likes),
			Thumbnail: article.Thumbnail,
			IsAuthor:  article.IsAuthor,
			Comments:  uint32(article.Comments),
		})
	}
 */