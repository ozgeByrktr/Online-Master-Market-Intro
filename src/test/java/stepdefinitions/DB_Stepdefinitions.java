package stepdefinitions;

import config_Requirements.ConfigReader;
import helperDB.CommonData;
import helperDB.Transactions;
import io.cucumber.java.en.Given;
import manage.Manage;

import java.sql.SQLException;
import java.util.*;


import static helperDB.CommonData.*;
import static helperDB.JDBC_Structure_Methods.*;

import static helperDB.Transactions.generateAccounts;
import static org.junit.Assert.*;

public class DB_Stepdefinitions extends Manage {

    CommonData data = new CommonData();

    @Given("Database connection established")
    public void database_connection_established() {
        createConnection();
    }

    @Given("Database connection is closed")
    public void database_connection_is_closed() {
        closeConnection();
    }


    /**
     * US09
     */

    @Given("prepare query  of the data entering the users table")
    public void prepare_query_of_the_data_entering_the_users_table() throws SQLException {
    query=getUS09insert_query();
    preparedStatement=getPraperedStatement(query);

    preparedStatement.setString(1, data.getFull_name());
    preparedStatement.setString(2, data.getEmail());
    preparedStatement.setString(3, data.getPassword());
    preparedStatement.setString(4, data.getRole_name());
    preparedStatement.setInt(5,data.getRole_id());

    }

    @Given("Verify the data information Result is obtained.")
    public void verify_the_data_information_result_is_obtained() {
        int rowCount=0;
        try {
            rowCount=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1,rowCount);
    }


    @Given("updated password")
    public void updated_password() throws SQLException {
        //update işlemini gerçekleştirmek için önce kaydedilen datanın UserIdsini bulmak gerekir
        userId=getLastInsertedUserId(getUs09selected_id_usersTable(),data.getFull_name());
        query=getUS09update_query();
        preparedStatement=getPraperedStatement(query);
        preparedStatement.setString(1,data.getNew_password());
        preparedStatement.setInt(2,userId);
    }

    /**
     * US10
     */
    @Given("A query is prepared for the list of users with financial approval and installment approval in the Users table.")
    public void a_query_is_prepared_for_the_list_of_users_with_financial_approval_and_installment_approval_in_the_users_table() throws SQLException {

    }


    /**
     * US12
     */

    @Given("prepare query to insert data entry into the users_zoom_api table")
    public void prepare_query_to_insert_data_entry_into_the_users_zoom_api_table() throws SQLException {

    }

    @Given("Updated account_id")
    public void updated_account_id() throws SQLException {

    }

    @Given("prepare query to insert data entry into the  verifications table")
    public void prepare_query_to_insert_data_entry_into_the_verifications_table() throws SQLException {


    }


    @Given("Verify the list results {string} {int} obtained")
    public void verify_the_list_results_obtained(String role, Integer record_count) throws SQLException {
    }

    @Given("prepare query to insert {int} datas entry into the payku_transactions table")
    public void prepare_query_to_insert_datas_entry_into_the_payku_transactions_table(Integer count) throws SQLException {
        query=getUs17payku_transaction();
        preparedStatement=getPraperedStatement(query);

        //addBatch yontemi ile her hazırlanan sorgu bir sorgu yıgınına eklenir
        List<Transactions> accounts=generateAccounts( count);

        int flag=0;
        for(Transactions transaction: accounts){
            preparedStatement.setInt(1,accounts.get(flag).getId());
            preparedStatement.setString(2,accounts.get(flag).getOrderStatus());
            preparedStatement.setString(3,accounts.get(flag).getOrder());
            preparedStatement.setString(4,accounts.get(flag).getEmail());
            preparedStatement.setString(5,accounts.get(flag).getSubject());
            preparedStatement.setString(6,accounts.get(flag).getUrl());
            preparedStatement.setInt(7,accounts.get(flag).getAmount());
            preparedStatement.setString(8,accounts.get(flag).getFull_name());

            preparedStatement.addBatch();
            flag++;
            if(flag== accounts.size()){
                result= preparedStatement.executeBatch();
            }
        }
    }


    @Given("{int} Enter the data in bulk. Check that it is added to the table.")
    public void enter_the_data_in_bulk_check_that_it_is_added_to_the_table(int rowCount) {
        /** executeBatch() yöntemi, her sorgunun etkilenen satır sayısını içeren bir int dizisi döndürür.*/
        System.out.println(Arrays.toString(result));
        assertEquals(rowCount,result.length);

    }


}









