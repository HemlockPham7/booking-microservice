package com.convit.bookservice.query.projection;

import com.convit.bookservice.command.data.Book;
import com.convit.bookservice.command.data.BookRepository;
import com.convit.bookservice.query.model.BookResponseModel;
import com.convit.bookservice.query.queries.GetAllBookQuery;
import com.convit.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookProjection {

    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query) {
        List<Book> list = bookRepository.findAll();

//        List<BookResponseModel> listBookReponse = new ArrayList<>();
//        list.forEach(book -> {
//            BookResponseModel model = new BookResponseModel();
//            BeanUtils.copyProperties(book, model);
//            listBookReponse.add(model);
//        });
//        return listBookReponse;

        return list.stream().map(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book, model);
            return model;
        }).toList();
    }

    @QueryHandler
    public BookResponseModel handle(GetBookDetailQuery query) {
        BookResponseModel bookResponseModel = new BookResponseModel();
        bookRepository.findById(query.getId()).ifPresent(book -> {
            BeanUtils.copyProperties(book, bookResponseModel);
        });
        return bookResponseModel;
    }
}
