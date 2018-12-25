package project.cn.edu.tongji.sse.nowfitness.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.BookDTO;

/**
 * Created by a on 2018/12/21.
 */

public class DoubanBookModel implements Serializable {

    /**
     * rating : {"max":10,"numRaters":1709,"average":"8.7","min":0}
     * subtitle : 用自身体重锻练
     * author : ["马克·劳伦","乔舒亚·克拉克"]
     * pubdate : 2012-9-1
     * tags : [{"count":2255,"name":"健身","title":"健身"},{"count":801,"name":"健康","title":"健康"},{"count":704,"name":"运动","title":"运动"},{"count":674,"name":"锻炼","title":"锻炼"},{"count":357,"name":"徒手","title":"徒手"},{"count":197,"name":"健美","title":"健美"},{"count":180,"name":"美国","title":"美国"},{"count":157,"name":"科普","title":"科普"}]
     * origin_title : You Are Your Own Gym
     * image : https://img3.doubanio.com/view/subject/m/public/s24403715.jpg
     * binding : 平装
     * translator : []
     * catalog : 前进！
     1 任务目标：结实、强壮、自信
     2 我的历程
     3 为什么进行自身体重训练？
     4 为什么进行力量训练？
     5 究竟什么是体能？
     6 营养：回归基础
     7 力量训练的常见认识误区
     8 借口和动力
     9 锻炼强度
     10 锻炼方法
     11 练习动作
     12 推力练习
     13 拉力练习
     14 腿部和臀部练习
     15 核心区练习
     16 全身练习
     17 健身计划
     18 更短、更强的20分钟锻炼！
     附录一　自制健身装备
     附录二　成功健身必须遵循的6个原则
     附录三　健身计划背后的科学
     附录四　练习索引
     附录五　健身日志
     * pages : 223
     * images : {"small":"https://img3.doubanio.com/view/subject/s/public/s24403715.jpg","large":"https://img3.doubanio.com/view/subject/l/public/s24403715.jpg","medium":"https://img3.doubanio.com/view/subject/m/public/s24403715.jpg"}
     * alt : https://book.douban.com/subject/11608712/
     * id : 11608712
     * publisher : 北京科学技术出版社
     * isbn10 : 7530459929
     * isbn13 : 9787530459928
     * title : 无器械健身
     * url : https://api.douban.com/v2/book/11608712
     * alt_title : You Are Your Own Gym
     * author_intro : 马克?劳伦是美国军方的体能训练专家、特种作战管理者、三项全能运动员，还是一个顶级的泰拳手。他曾经有效地训练过近1000名士兵，使他们达到了最精锐的特种部队的极端体能要求。作为一个有经验的指挥官，他善于计划和执行诸如机场占领、战斗搜寻和营救、低空支持、侦察和监控等任务。他训练的部队能够通过空投、越野载具、步行、潜水及其他的两栖作战方法立即部署前线作战行动。乔舒亚?克拉克是《心如止水：卡特里娜飓风中的生命》的作者，他的著作出现在许多报纸、杂志和文选中。他还是一个获得了资格认证的私人教练，自从卡特里娜飓风毁了他的健身中心后，他再也没有踏进健身房一步。
     * summary : 无器械健身：用自身体重锻炼，ISBN：9787530459928，作者：（美）马克·劳伦，乔舒亚·克拉克 著 蔡杰 译
     * price : 49.00元
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private String price;
    private List<String> author;
    private List<TagsBean> tags;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class RatingBean  implements Serializable{
        /**
         * max : 10
         * numRaters : 1709
         * average : 8.7
         * min : 0
         */

        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
        public  RatingBean(BookDTO.BooksBean.RatingBean ratingBean){
            this.average = ratingBean.getAverage();
            this.numRaters = ratingBean.getNumRaters();
        }
        public  RatingBean(){

        }

    }

    public static class ImagesBean  implements Serializable {
        /**
         * small : https://img3.doubanio.com/view/subject/s/public/s24403715.jpg
         * large : https://img3.doubanio.com/view/subject/l/public/s24403715.jpg
         * medium : https://img3.doubanio.com/view/subject/m/public/s24403715.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class TagsBean  implements Serializable{
        /**
         * count : 2255
         * name : 健身
         * title : 健身
         */

        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public TagsBean(BookDTO.BooksBean.TagsBean tagsBean){
            this.count = tagsBean.getCount();
            this.name = tagsBean.getName();
            this.title = tagsBean.getTitle();
        }
        public TagsBean(){}

    }
    public DoubanBookModel(){

    }
    public DoubanBookModel(BookDTO.BooksBean booksBean){
        this.setImage(booksBean.getImage());
        this.setTitle(booksBean.getTitle());
        this.setRating(new RatingBean(booksBean.getRating()));
        this.setAuthor(booksBean.getAuthor());
        this.setPubdate(booksBean.getPubdate());
        this.setSummary(booksBean.getSummary());
        List<TagsBean> tagsBeanList = new ArrayList<>();
        for (BookDTO.BooksBean.TagsBean tag :booksBean.getTags()){
            tagsBeanList.add(new TagsBean(tag));
        }
        this.setTags(tagsBeanList);
    }
}
