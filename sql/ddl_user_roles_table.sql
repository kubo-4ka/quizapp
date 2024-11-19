CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,             -- ユーザーID
    role_id BIGINT NOT NULL,             -- 役割ID
    PRIMARY KEY (user_id, role_id),      -- 複合主キー
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
