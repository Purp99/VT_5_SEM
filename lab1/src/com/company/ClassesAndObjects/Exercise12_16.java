package com.company.ClassesAndObjects;

import java.awt.print.Book;
import java.util.Comparator;

public class Exercise12_16 {

    public static class Book implements Comparable<Book>, Cloneable  {
        private String title;
        private String author;
        private int price;
        private static int edition;

        private ISBN isbn;




        public  Book(String title, String author, int price, ISBN isbn){
            this.title = title;
            this.author = author;
            this.price = price;
            this.isbn = isbn;
        }

        public String getTitle(){
            return this.title;
        }

        @Override
        public int hashCode() {
            return (3 * title.hashCode() + 3 * author.hashCode() + 3 * price + 3 * isbn.hashCode());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Book book = (Book) obj;
            return price == book.price && title.equals(book.title) &&
                    author.equals(book.author) && isbn.equals(book.isbn);
        }

        @Override
        public String toString() {
            return "Book: " +
                    "title= " + title +
                    ",author= " + author +
                    ",price= " + price +
                    ",ISBN= " + isbn +
                    '}';
        }


        @Override
        protected Object clone() throws CloneNotSupportedException {
            Book clonedBook = (Book) super.clone();
            clonedBook.isbn = isbn.clone();
            return clonedBook;
        }

        @Override
        public int compareTo(Book book) {
            return isbn.compareTo(book.isbn);
        }
    }

    public class ProgrammerBook extends Book {
        private String language;
        private int level;

        public ProgrammerBook(String title, String author, int price, ISBN isbn, String language, int level) {
            super(title, author, price, isbn);
            this.language = language;
            this.level = level;
        }

        @Override
        public int hashCode() {
            return (3 * language.hashCode() + 3 * level + super.hashCode());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ProgrammerBook programmerBook = (ProgrammerBook) obj;
            return level == programmerBook.level && language.equals(programmerBook.language) && super.equals(obj);
        }

        @Override
        public String toString() {
            return "ProgrammerBook{" +
                    "language= " + language +
                    ",level= " + level +
                    '}';
        }
    }

    public class ISBN implements Comparable<ISBN>, Cloneable {
        private final int bookNumber;

        public ISBN(int bookNumber) {
            this.bookNumber = bookNumber;
        }

        @Override
        public int compareTo(ISBN isbn) {
            return bookNumber - isbn.bookNumber;
        }

        @Override
        public ISBN clone() throws CloneNotSupportedException {
            return (ISBN) super.clone();
        }
    }

    public class TitleComparator implements Comparator<Book> {

        @Override
        public int compare(Book book1, Book book2) {
            return book1.getTitle().compareTo(book2.getTitle());
        }
    }
}
