CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,         -- ユーザーID
    username VARCHAR(50) NOT NULL UNIQUE, -- ユーザー名（ユニーク）
    password VARCHAR(255) NOT NULL,       -- パスワード（ハッシュ化することが推奨されます）
    enabled BOOLEAN NOT NULL DEFAULT TRUE -- ユーザーが有効かどうかのフラグ
);
