package project.cn.edu.tongji.sse.nowfitness.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.reactivex.Single;

import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.BookDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DouBanNetWorkUtils;
import project.cn.edu.tongji.sse.nowfitness.model.BookResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.DoubanBookModel;

/**
 * Created by a on 2018/12/25.
 */

public class DouBanAPIRepositoryImpl implements  DouBanAPIRepository {
    private ApiInterface api = DouBanNetWorkUtils.makeRetrofit().create(ApiInterface.class);
    @Override
    public Single<BookResponseModel> getBookInfo(String tag, int start, int count) {
       BookResponseModel bookResponseModel = new BookResponseModel();
        return api.getBookInfo(tag, start, count).map(new io.reactivex.functions.Function<BookDTO, BookResponseModel>() {
            @Override
            public BookResponseModel apply(BookDTO bookDTO) throws Exception {
                bookResponseModel.setTotal(bookDTO.getTotal());
                bookResponseModel.setCount(bookDTO.getCount());
                bookResponseModel.setStart(bookDTO.getStart());
                List<DoubanBookModel> doubanBookModelList = new ArrayList<>();
                if(bookDTO.getBooks()!=null) {
                    for (BookDTO.BooksBean e : bookDTO.getBooks())
                       doubanBookModelList.add( new DoubanBookModel(e));
                    bookResponseModel.setBooks(doubanBookModelList);
                }
                return bookResponseModel;
            }
        });
    }
}
