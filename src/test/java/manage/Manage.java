package manage;

import lombok.Getter;

@Getter
public class Manage {

private String US09insert_query="INSERT INTO users (full_name, email, password, role_name, role_id, created_at) \n" +
       // "VALUES ('John Doe', 'johnDoe@example.com', SHA2('password', 256), 'user', 1, unix_timestamp());";
        "VALUES (?, ?,  SHA2(?, 256), ?,?, unix_timestamp());";
private String US09update_query="UPDATE users  SET password = SHA2(?, 256) WHERE id = ?;";
private  String us09selected_id_usersTable="Select id From users Where full_name= ? ;";

private String us17payku_transaction="INSERT INTO payku_transactions (id, status, `order`, email, subject, url, amount, notified_at, created_at, updated_at, full_name) VALUES(?,?,?,?,?,?,?,now(),now(),now(),?)";
}