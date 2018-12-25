package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.List;

/**
 * Created by a on 2018/12/25.
 */

public class BookResponseModel {
    private int count;
    private int start;
    private int total;
    private List<DoubanBookModel> books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DoubanBookModel> getBooks() {
        return books;
    }

    public void setBooks(List<DoubanBookModel> books) {
        this.books = books;
    }
}
