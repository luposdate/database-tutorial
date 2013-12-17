# Database Tutorial for SQL, Relational Algebra, Tuple and Domain Calculus 

This is a [repository of Database Tutorial](https://github.com/luposdate/database-tutorial), a database tutorial developed by [IFIS](http://www.ifis.uni-luebeck.de/) at the [University of Lübeck](http://www.uni-luebeck.de/).

The database tutorial is designed to make the database study easier for computer science students. This application can read SQL queries, execute them and show the results to the users. It can also parse the relational algebra queries, tuple relational calculus queries and domain relational calculus queries, then convert them into the corresponding SQL queries and show the results of their execution. In this way, the users can get a clear idea of whether their queries are correct or they can get the results the users want. If the results are not ideal, the users can use this application to help them modify their queries until they get the satisfactory outcomes.

Users can create their own tables and insert their own data. They can also try out other database query languages as much as they want. Through practice, the users will form a better understanding of computer databases.

## Starting the Applet

The main class is Tutorial.java of the tutorial package. The tutorial can be tested [here](http://www.ifis.uni-luebeck.de/index.php?id=database-tutorial).

## User Guide

This is a database course tutorial application. You can easily execute different database languages. Supported languages are:
- SQL
- Relational Algebra Expressions (Supported features are: selection, projection, Cartesian product, union, complement, rename, assignment, join, intersection)
- Tuple Calculus
- Domain Calculus

The database already has 4 tables with some testing data, they are:
- Flug (flugnummer int not null, ursprung varchar(30) not null, ziel varchar(30) not null, distanz int not null, abflugzeit int not null, ankunftzeit int not null, primary key(flugnummer))
- Mitarbeiter (personalnummer char(10) not null, name varchar(30) not null, gehalt int not null, primary key(personalnummer))
- Flugzeug (flugzeugnummer int not null, name varchar(30) not null, reichweite int not null, primary key (flugzeugnummer))
- Zertifiziert (personalnummer char(10) not null, flugzeugnummer int not null, primary key (personalnummer, flugzeugnummer), foreign key (personalnummer) references mitarbeiter, foreign key (flugzeugnummer) references flugzeug)

There are some basic rules concerning writing the expressions:
- All the expressions should be written in one line;
- One expression at a time;
- All the expressions should end with a semicolon (“;”);
- The join conditions for relational algebra need to be put in a pair of curve brackets (“{}”).

On the left side of the applet, user can input all the queries and the generated SQL queries will be shown on the right side, and the result will be shown in the bottom.

Example queries for each kind of query language is embedded in the application. Users can choose from an example drop-list.

The operators that cannot be typed by the keyboard can be input by using the buttons or short-cuts. The short-cuts are in the form of “Ctrl + Alt + Underlined letter” altogether, e.g. “Ctrl + Alt + S” is the short-cut for select operator.

## Implementation Details

The application is a Java Applet. It is based on HSQLDB database system, which provides an in-memory-database. Therefore, the database will be created in the users’ memory. We do not need another server to provide database service for all the users. Besides, users can do whatever they want to the database and will not affect other users.

JavaCC is used to parse the relational algebra, tuple relational calculus and domain relational calculus queries and generate parse trees using the .jjt grammar files and are converted into parser trees. The parsers are written in Java and will read the parser trees from the root and convert the trees into SQL queries.
