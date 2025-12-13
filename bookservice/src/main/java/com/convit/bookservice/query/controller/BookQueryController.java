package com.convit.bookservice.query.controller;

import com.convit.bookservice.query.model.BookResponseModel;
import com.convit.bookservice.query.queries.GetAllBookQuery;
import com.convit.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBookQuery getAllBookQuery = new GetAllBookQuery();
        return queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join(); // dung join se block luong tai day va cho ket qua tra ve

        // List<BookResponseModel> bookFuture = queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)); // dang la bat dong bo
        // CompletableFuture<List<BookResponseModel>> bookFuture = queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class));
        // bookFuture.thenAccept(books -> {
        //     System.out.println("book: ", books);
        // });
        // Trong then accept co mot consumer, consumer la gi -> no la mot functional interface de xu li bat dong bo
    }

    @GetMapping("{bookId}")
    public BookResponseModel getBookDetails(@PathVariable String bookId) {
        GetBookDetailQuery getBookDetailQuery = GetBookDetailQuery.builder()
                .id(bookId)
                .build();
        return queryGateway.query(getBookDetailQuery, ResponseTypes.instanceOf(BookResponseModel.class)).join();
    }
}
