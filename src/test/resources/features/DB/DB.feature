@DB
Feature: DB

  Background: Database connection
    * Database connection established


  @US09  # Insert a data into the users table by creating the values (full_name, email, password, role_name, role_id, created_at).
        # Verify that the password of the entered data can be updated.
  Scenario:insert the data in the users table and update password test
    * prepare query  of the data entering the users table
    * Verify the data information Result is obtained.
    * updated password
    * Verify the data information Result is obtained.
    * Database connection is closed


    @US17 #Add 5 data to the payku_transactions table at the same time. Verify that it is added.
    Scenario: Add 5 data to the payku_transactions table at the same time
      * prepare query to insert 5 datas entry into the payku_transactions table
      * 5 Enter the data in bulk. Check that it is added to the table.
      * Database connection is closed
