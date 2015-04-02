Introduction to Spring Data JPA
===============================

Use packages `exercise1` and `exercise2` when implementing the exercises. 

The solutions can be found in the corresponding solution packages. For example 
the solution for `exercise1` can be found in the package `solution1`.

Exercise 1
----------

In this exercise you will implement a JPA repository for a simple blog 
application with the help of Spring Data JPA. 

The application contains a simple model class `blog.model.BlogPost`. The 
application also defines a business interface `blog.services.BlogService`.
At the moment the interface contains methods related only to the data access 
logic, but in the future it could be expanded to contain other logic as well.

Create a repository interface using Spring Data JPA and use that interface to
implement the `blog.services.BlogService` interface. Please remember that 
Spring Data JPA provides most of the methods in its interfaces such as 
`CrudRepository`. You will only need to add finder methods with custom 
parameters.

Use autowiring to have Spring inject the repository implementation to your 
service class.

Before you can test the application, complete the Spring Data JPA configuration 
in `exercise1/applicationContext.xml` which you can find under 
`src/main/resources`. The XML namespace has already been defined as `data-jpa`. 

Test the application by running the main application class 
`exercise1.BlogConsole`.

Exercise 2
----------

In this second exercise you should use JPA Criteria API with Spring Data JPA 
instead of defining query methods in the DAO interface directly.

Remove queries from the repository interface. Rewrite the `BlogService` 
implementation to use the `Specification` interface instead.

Test the application by running the main application class 
`exercise2.BlogConsole`.
