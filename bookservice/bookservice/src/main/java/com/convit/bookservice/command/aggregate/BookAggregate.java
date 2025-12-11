package com.convit.bookservice.command.aggregate;

import com.convit.bookservice.command.command.CreateBookCommand;
import com.convit.bookservice.command.event.BookCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Getter
@Setter
public class BookAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand){
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);

        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent bookCreatedEvent){
        this.id = bookCreatedEvent.getId();
        this.name = bookCreatedEvent.getName();
        this.author = bookCreatedEvent.getAuthor();
        this.isReady = bookCreatedEvent.getIsReady();
    }
}
