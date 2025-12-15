package com.convit.bookservice.query.projection;

import com.convit.bookservice.command.data.Book;
import com.convit.bookservice.command.data.BookRepository;
import com.convit.bookservice.query.queries.GetAllBookQuery;
import com.convit.commonservice.model.BookResponseCommonModel;
import com.convit.commonservice.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookProjection {

    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseCommonModel> handle(GetAllBookQuery query) {
        List<Book> list = bookRepository.findAll();

//        List<BookResponseModel> listBookReponse = new ArrayList<>();
//        list.forEach(book -> {
//            BookResponseModel model = new BookResponseModel();
//            BeanUtils.copyProperties(book, model);
//            listBookReponse.add(model);
//        });
//        return listBookReponse;

        return list.stream().map(book -> {
            BookResponseCommonModel model = new BookResponseCommonModel();
            BeanUtils.copyProperties(book, model);
            return model;
        }).toList();
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetBookDetailQuery query) throws Exception {
        BookResponseCommonModel bookResponseModel = new BookResponseCommonModel();
        Book book = bookRepository.findById(query.getId()).orElseThrow(() -> new Exception("Book not found with BookId: "+ query.getId()));
        BeanUtils.copyProperties(book,bookResponseModel);
        return bookResponseModel;
    }
}
