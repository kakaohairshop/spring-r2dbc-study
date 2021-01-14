CREATE TABLE shop (
    id VARCHAR(255) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE styler (
    id VARCHAR(255) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    shop_id VARCHAR(255),
    FOREIGN KEY (shop_id) REFERENCES shop(id)
);


INSERT INTO shop(name) VALUES ('준오헤어');

INSERT INTO styler(name, shop_id) VALUES ('홍길동', '1');
INSERT INTO styler(name, shop_id) VALUES ('이순신', '1');