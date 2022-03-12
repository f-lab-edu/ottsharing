-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- User Table Create SQL
CREATE TABLE User
(
    `user_id`  VARCHAR(45)    NOT NULL,
    PRIMARY KEY (user_id)
);


-- Party Table Create SQL
CREATE TABLE Party
(
    `party_id`      INT            NOT NULL    AUTO_INCREMENT,
    `leader_id`     VARCHAR(45)    NOT NULL,
    `ott_id`        VARCHAR(45)    NOT NULL,
    `ott_password`  VARCHAR(45)    NOT NULL,
    `is_full`       TINYINT(1)     NOT NULL,
    PRIMARY KEY (party_id)
);

ALTER TABLE Party COMMENT '계정아이디,계정비밀번호,파티장';

ALTER TABLE Party
    ADD CONSTRAINT FK_Party_leader_id_User_user_id FOREIGN KEY (leader_id)
        REFERENCES User (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- PartyMember Table Create SQL
CREATE TABLE PartyMember
(
    `user_id`   VARCHAR(45)    NOT NULL,
    `party_id`  INT            NOT NULL,
    PRIMARY KEY (user_id, party_id)
);

ALTER TABLE PartyMember
    ADD CONSTRAINT FK_PartyMember_user_id_User_user_id FOREIGN KEY (user_id)
        REFERENCES User (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PartyMember
    ADD CONSTRAINT FK_PartyMember_party_id_Party_party_id FOREIGN KEY (party_id)
        REFERENCES Party (party_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- PartyWaiting Table Create SQL
CREATE TABLE PartyWaiting
(
    `waiting_id`  INT            NOT NULL    AUTO_INCREMENT,
    `user_id`     VARCHAR(45)    NOT NULL,
    `timstamp`    DATETIME       NOT NULL,
    PRIMARY KEY (waiting_id)
);

ALTER TABLE PartyWaiting
    ADD CONSTRAINT FK_PartyWaiting_user_id_User_user_id FOREIGN KEY (user_id)
        REFERENCES User (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


