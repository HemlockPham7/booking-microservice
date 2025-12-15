package com.convit.bookservice.command.event;

import com.convit.bookservice.command.data.Book;
import com.convit.bookservice.command.data.BookRepository;
import com.convit.commonservice.event.BookUpdateStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent bookCreatedEvent){
        Book book = new Book();
        BeanUtils.copyProperties(bookCreatedEvent, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent bookUpdatedEvent){
        Optional<Book> oldBook = bookRepository.findById(bookUpdatedEvent.getId());

        if (oldBook.isPresent()) {
            Book book = oldBook.get();
            book.setName(bookUpdatedEvent.getName());
            book.setAuthor(bookUpdatedEvent.getAuthor());
            book.setIsReady(bookUpdatedEvent.getIsReady());

            bookRepository.save(book);
        }
    }

    @EventHandler
    public void on(BookDeletedEvent bookDeletedEvent){
        Optional<Book> oldBook = bookRepository.findById(bookDeletedEvent.getId());

        oldBook.ifPresent(book -> bookRepository.delete(book));
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }
}
