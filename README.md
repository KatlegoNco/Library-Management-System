﻿# Library-Management-System
Library Management System (Java)
The Library Management System is a Java application designed to manage books, members, and book checkouts within a library. It allows librarians to keep track of available books, member information, and borrowing history.

Features
Book Management:
Add new books with details such as title, author, and ISBN.
Update book information (e.g., availability status).
Remove books from the system.
Member Management:
Add new members with details like name and email.
Validate email format using regular expressions.
Maintain a list of borrowed books for each member.
Search Functionality:
Search for books by title or author.
Case-insensitive search using string manipulation techniques.
Book Checkout System:
Members can borrow available books.
Handle exceptions when books are not available for checkout.
Assertions and Input Validation:
Use assertions to ensure proper book checkouts and returns.
Validate user inputs to prevent invalid data entry.
Getting Started
Prerequisites:
Java Development Kit (JDK) installed.
IDE (e.g., IntelliJ IDEA, Eclipse) for development.
Clone the Repository:
git clone https://github.com/yourusername/library-management-system.git

Compile and Run:
Open the project in your preferred IDE.
Compile and run the LibrarySystem class.
Usage
Adding Books and Members:
Use the application to add new books and members.
Validate member email addresses during creation.
Searching for Books:
Use the search functionality to find books by title or author.
Book Checkouts:
Members can borrow available books.
Exceptions will be thrown if a book is not available.
