package com.convit.bookservice.query.controller;

import com.convit.bookservice.query.queries.GetAllBookQuery;
import com.convit.commonservice.model.BookResponseCommonModel;
import com.convit.commonservice.queries.GetBookDetailQuery;
import com.convit.commonservice.services.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping
    public List<BookResponseCommonModel> getAllBooks() {
        GetAllBookQuery getAllBookQuery = new GetAllBookQuery();
        return queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseCommonModel.class)).join(); // dung join se block luong tai day va cho ket qua tra ve

        // List<BookResponseModel> bookFuture = queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)); // dang la bat dong bo
        // CompletableFuture<List<BookResponseModel>> bookFuture = queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class));
        // bookFuture.thenAccept(books -> {
        //     System.out.println("book: ", books);
        // });
        // Trong then accept co mot consumer, consumer la gi -> no la mot functional interface de xu li bat dong bo
    }

    @GetMapping("{bookId}")
    public BookResponseCommonModel getBookDetails(@PathVariable String bookId) {
        GetBookDetailQuery getBookDetailQuery = GetBookDetailQuery.builder()
                .id(bookId)
                .build();
        return queryGateway.query(getBookDetailQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) {
        kafkaService.sendMessage("test", message);
    }
}
