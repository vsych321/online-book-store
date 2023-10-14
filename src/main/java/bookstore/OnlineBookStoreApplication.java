package bookstore;

import bookstore.entity.Book;
import bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class OnlineBookStoreApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setTitle("Influence");
                book.setAuthor("Robert Cialdini");
                book.setIsbn("9780061241895");
                book.setPrice(BigDecimal.valueOf(900));
                book.setDescription(
                        "This book will help you to understand what is psychology of persuasion");
                bookService.save(book);
                System.out.println(bookService.findAll());
            }
        };
    }
}
