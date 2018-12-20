package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;

public class MomentsModelList {
    int total;
    List<MomentsModel> list;
    int pageNum;
    int pageSize;
    int size;
    int startRow;
    int endRow;
    int pages;

    public MomentsModelList(MomentsDTO momentsDTO){
        this.total = momentsDTO.getTotal();
        this.pageNum = momentsDTO.getPageNum();
        this.pageSize = momentsDTO.getPageSize();
        this.size = momentsDTO.getSize();
        this.pages = momentsDTO.getPages();
        this.startRow = momentsDTO.getStartRow();
        this.endRow = momentsDTO.getEndRow();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MomentsModel> getList() {
        return list;
    }

    public void setList(List<MomentsModel> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
