package helperDB;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static utilities.API_Utilities.API_Methods.faker;
@Getter
public class Transactions {

    //( id,status, `order`, email, subject, url, amount, notified_at, created_at, updated_at, full_name)
    private int id;
    private static List<String>status=new ArrayList<>(Arrays.asList("success","failed","pending","invalid"));
    private String orderStatus;
    private String order;
    private String email;
    private String subject;
    private String url;
    private Integer amount;
    private String full_name;
    public static int randomIndex;

    public Transactions(int id,String email, String orderStatus, String order, String subject, String url, Integer amount, String full_name) {
        this.email = email;
        this.orderStatus = orderStatus;
        this.order = order;
        this.subject = subject;
        this.url = url;
        this.amount = amount;
        this.full_name = full_name;
        this.id = id;
    }
    public  static List<Transactions> getPayku(){
        return List.of(
                new Transactions( faker.number().numberBetween(20000,1000000), status.get(randomIndex),"order"+faker.number().randomNumber(), faker.internet().emailAddress(),faker.lorem().word(),  faker.company().url(), (int) faker.number().randomNumber(), faker.name().fullName()),
                new Transactions( faker.number().numberBetween(20000,1000000), status.get(randomIndex),"order"+faker.number().randomNumber(), faker.internet().emailAddress(),faker.lorem().word(),   faker.company().url(),(int) faker.number().randomNumber(), faker.name().fullName()),
                new Transactions( faker.number().numberBetween(20000,1000000), status.get(randomIndex),"order"+faker.number().randomNumber(), faker.internet().emailAddress(),faker.lorem().word(),   faker.company().url(),(int) faker.number().randomNumber(), faker.name().fullName()),
                new Transactions( faker.number().numberBetween(20000,1000000), status.get(randomIndex),"order"+faker.number().randomNumber(), faker.internet().emailAddress(),faker.lorem().word(),   faker.company().url(),(int) faker.number().randomNumber(), faker.name().fullName()),
                new Transactions( faker.number().numberBetween(20000,1000000), status.get(randomIndex),"order"+faker.number().randomNumber(), faker.internet().emailAddress(),faker.lorem().word(),   faker.company().url(),(int) faker.number().randomNumber(), faker.name().fullName())


        );
    }
    public static List<Transactions> generateAccounts(int count){
        List<Transactions> accounts = new ArrayList<>();
        Random random=new Random();
        randomIndex=random.nextInt(status.size());
        for (int i = 0; i <count ; i++) {
            Transactions account=new Transactions(
                    faker.number().numberBetween(20000,1000000),
                    status.get(randomIndex),
                    "order"+faker.number().randomNumber(),
                    faker.internet().emailAddress(),
                    faker.lorem().word(),
                    faker.company().url(),
                    (int) faker.number().randomNumber(),
                    faker.name().fullName()

            );
                accounts.add(account);
        }
        return accounts;
    }
}
