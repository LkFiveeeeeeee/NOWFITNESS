package project.cn.edu.tongji.sse.nowfitness.data.network.DTO;

import java.util.List;

/**
 * Created by a on 2018/11/29.
 */

public class CommentsDTO {

    /**
     * commentsNum : 2
     * commentsList : [{"id":6,"momentsId":10,"content":"adsfaf","commentUserId":4,"commentTime":"2018-11-28T16:25:02.000+0000","commentUserPhoto":"e3bb8aef5f5f4edd8be8dd0de60c6874.JPG","commentUserName":"liuke","repliesList":[{"id":2,"commentId":6,"fromUserId":2,"toUserId":3,"replyType":"reply","content":"haofangaaaa","replyTime":"2018-11-25T12:47:47.000+0000","replyId":1,"fromUserName":"huitaaa","toUserName":"omf"},{"id":1,"commentId":6,"fromUserId":3,"toUserId":2,"replyType":"reply","content":"haofangaaaa","replyTime":"2018-11-25T12:45:05.000+0000","replyId":1,"fromUserName":"omf","toUserName":"huitaaa"}]},{"id":4,"momentsId":10,"content":"asdgqrqtr","commentUserId":4,"commentTime":"2018-11-25T13:20:41.000+0000","commentUserPhoto":"e3bb8aef5f5f4edd8be8dd0de60c6874.JPG","commentUserName":"liuke","repliesList":[{"id":6,"commentId":4,"fromUserId":3,"toUserId":4,"replyType":"reply","content":"hwytufsgha","replyTime":"2018-11-25T12:49:42.000+0000","replyId":5,"fromUserName":"omf","toUserName":"liuke"},{"id":5,"commentId":4,"fromUserId":2,"toUserId":4,"replyType":"reply","content":"hafgdfsgha","replyTime":"2018-11-25T12:49:16.000+0000","replyId":5,"fromUserName":"huitaaa","toUserName":"liuke"},{"id":4,"commentId":4,"fromUserId":4,"toUserId":2,"replyType":"reply","content":"haaaaa","replyTime":"2018-11-25T12:48:43.000+0000","replyId":1,"fromUserName":"liuke","toUserName":"huitaaa"}]}]
     */

    private int commentsNum;
    private List<CommentsListBean> commentsList;

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public List<CommentsListBean> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CommentsListBean> commentsList) {
        this.commentsList = commentsList;
    }

    public static class CommentsListBean {
        /**
         * id : 6
         * momentsId : 10
         * content : adsfaf
         * commentUserId : 4
         * commentTime : 2018-11-28T16:25:02.000+0000
         * commentUserPhoto : e3bb8aef5f5f4edd8be8dd0de60c6874.JPG
         * commentUserName : liuke
         * repliesList : [{"id":2,"commentId":6,"fromUserId":2,"toUserId":3,"replyType":"reply","content":"haofangaaaa","replyTime":"2018-11-25T12:47:47.000+0000","replyId":1,"fromUserName":"huitaaa","toUserName":"omf"},{"id":1,"commentId":6,"fromUserId":3,"toUserId":2,"replyType":"reply","content":"haofangaaaa","replyTime":"2018-11-25T12:45:05.000+0000","replyId":1,"fromUserName":"omf","toUserName":"huitaaa"}]
         */

        private int id;
        private int momentsId;
        private String content;
        private int commentUserId;
        private String commentTime;
        private String commentUserPhoto;
        private String commentUserNickName;
        private String commentUserName;
        private List<RepliesListBean> repliesList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMomentsId() {
            return momentsId;
        }

        public void setMomentsId(int momentsId) {
            this.momentsId = momentsId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getCommentUserPhoto() {
            return commentUserPhoto;
        }

        public void setCommentUserPhoto(String commentUserPhoto) {
            this.commentUserPhoto = commentUserPhoto;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public String getCommentUserNickName() {
            return commentUserNickName;
        }

        public void setCommentUserNickName(String commentUserNickName) {
            this.commentUserNickName = commentUserNickName;
        }

        public List<RepliesListBean> getRepliesList() {
            return repliesList;
        }

        public void setRepliesList(List<RepliesListBean> repliesList) {
            this.repliesList = repliesList;
        }

        public static class RepliesListBean {
            /**
             * id : 2
             * commentId : 6
             * fromUserId : 2
             * toUserId : 3
             * replyType : reply
             * content : haofangaaaa
             * replyTime : 2018-11-25T12:47:47.000+0000
             * replyId : 1
             * fromUserName : huitaaa
             * toUserName : omf
             */

            private int id;
            private int commentId;
            private int fromUserId;
            private int toUserId;
            private String replyType;
            private String content;
            private String replyTime;
            private int replyId;
            private String fromUserName;
            private String toUserName;
            private String fromUserNickName;

            private String toUserNickName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public int getFromUserId() {
                return fromUserId;
            }

            public void setFromUserId(int fromUserId) {
                this.fromUserId = fromUserId;
            }

            public int getToUserId() {
                return toUserId;
            }

            public void setToUserId(int toUserId) {
                this.toUserId = toUserId;
            }

            public String getReplyType() {
                return replyType;
            }

            public void setReplyType(String replyType) {
                this.replyType = replyType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getReplyTime() {
                return replyTime;
            }

            public void setReplyTime(String replyTime) {
                this.replyTime = replyTime;
            }

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
            }

            public String getFromUserName() {
                return fromUserName;
            }

            public void setFromUserName(String fromUserName) {
                this.fromUserName = fromUserName;
            }

            public String getToUserName() {
                return toUserName;
            }

            public void setToUserName(String toUserName) {
                this.toUserName = toUserName;
            }


            public String getFromUserNickName() {
                return fromUserNickName;
            }

            public void setFromUserNickName(String fromUserNickName) {
                this.fromUserNickName = fromUserNickName;
            }

            public String getToUserNickName() {
                return toUserNickName;
            }

            public void setToUserNickName(String toUserNickName) {
                this.toUserNickName = toUserNickName;
            }

        }
    }
}
