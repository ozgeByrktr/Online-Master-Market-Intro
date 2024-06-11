package helperDB;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.sql.SQLException;
import java.util.*;

import static helperDB.JDBC_Structure_Methods.*;
import static utilities.API_Utilities.API_Methods.faker;
@Getter
public class CommonData {
// (full_name, email, password, role_name, role_id,
    private String full_name;
    private String email;
    private String password;
    private String new_password;
    private String role_name;
    private int role_id;
    public static int userId;
    static Random random = new Random();
    public static  int[] result;
    static List<String> userTypes= Arrays.asList("user","teacher","organization","admin");

    public static int getRoleId(String role_name){
        Map<String, Integer> userTypeToRoleId=new HashMap<>();
        userTypeToRoleId.put("user",1);
        userTypeToRoleId.put("admin",2);
        userTypeToRoleId.put("organization",3);
        userTypeToRoleId.put("teacher",4);


        return userTypeToRoleId.get(role_name);
    }
    public String generateNewPassword(Faker faker, String currentPassword){
        String newPassword;
        do{
            newPassword=faker.internet().password();
        }while(newPassword.equals(currentPassword));

        return newPassword;
    }

    public static int getLastInsertedUserId(String query,String full_name) throws SQLException {
        preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,full_name);
        resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt("id");
        }else{
            throw  new SQLException("Kullanıcı ID Bulunamadı");
        }
    }





    public CommonData(){
        this.full_name=faker.name().fullName();
        this.email=faker.internet().emailAddress();
        this.password=faker.internet().password();
        this.role_name=userTypes.get(random.nextInt(userTypes.size()));
        this.role_id=getRoleId(role_name);
        this.new_password=generateNewPassword(faker,this.password);

    }
}
