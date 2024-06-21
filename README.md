# tasks-v2

Tech: Java, SpringBoot, PostgreSql

Steps to run this app:

1. Set up SDK & clean install
2. You should have postgresql installed and create a db called task, and add in app properties you should add you credentials
3. Run app with spring.jpa.hibernate.ddl-auto=create in order to create the tables; change it back to update if you make any changes
4. Run sql script in order to have some data in your tables(you can find them in the resources package)

Testing:
1. There is an account with username: 1 email: 1@gmail.com and password 1 and you can login with it.
2. Test each endpoint by adding the JWT token - > Authorization - > Bearer Token ( you should paste here only the JWT token from the login response without the string Bearer)


Functionalities:
1. Add a task
2. Associate task to a user
3. Add sub task to a task
4. Update a task
5. Add comment to a task
6. Get task by id
7. Search task by params

Todos:
1. Use DTOs instead of objects
2. Increase tests coverage
3. Create custom exceptions
