


CREATE TABLE BusinessProject.Task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    task TEXT NOT NULL,
    character_count INT NOT NULL,
    send_date_time DATETIME NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES User(id),
    FOREIGN KEY (receiver_id) REFERENCES User(id)
);
