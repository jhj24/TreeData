package com.jhj.treedata.bean;

import com.jhj.datalibrary.model.BaseTree;

import java.util.List;

/**
 * Created by jhj on 18-5-15.
 */

public class StaffBean extends BaseTree<StaffBean>{


    /**
     * departId : 431
     * departName : 人事
     * parentId : 0
     * deac : HR
     * CompanyId : 46
     * UserInfo : [{"userGuid":"4bdf57feaf1749748dbfd3b2ebd6b0aa","companyId":46,"company":"郑州易企点信息科技有限公司","department":"人事","departmentId":431,"post":"人事经理","postId":157,"EQDCode":"EQD13849110116","uname":"13849110116","photo":"https://www.eqid.top:8009/image/20180404/13849110116/20180404084248headimage.png","upname":"小狐狸","location":"河南省安阳市滑县222省道靠近圣皇沙发·床垫·软床","username":"王倩","isFriend":false,"Signature":"","jobNumber":"HR180410001"}]
     * childs : []
     */

    private String departId;
    private String departName;
    private String parentId;
    private String deac;
    private String CompanyId;
    private List<UserInfoBean> UserInfo; //子节点，
    private List<StaffBean> childs; //子节点，可能其下还有子节点

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeac() {
        return deac;
    }

    public void setDeac(String deac) {
        this.deac = deac;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String CompanyId) {
        this.CompanyId = CompanyId;
    }

    public List<UserInfoBean> getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(List<UserInfoBean> UserInfo) {
        this.UserInfo = UserInfo;
    }

    public List<?> getChilds() {
        return childs;
    }

    public void setChilds(List<StaffBean> childs) {
        this.childs = childs;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<StaffBean> getChildren() {
        return null;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    public static class UserInfoBean {
        /**
         * userGuid : 4bdf57feaf1749748dbfd3b2ebd6b0aa
         * companyId : 46
         * company : 郑州易企点信息科技有限公司
         * department : 人事
         * departmentId : 431
         * post : 人事经理
         * postId : 157
         * EQDCode : EQD13849110116
         * uname : 13849110116
         * photo : https://www.eqid.top:8009/image/20180404/13849110116/20180404084248headimage.png
         * upname : 小狐狸
         * location : 河南省安阳市滑县222省道靠近圣皇沙发·床垫·软床
         * username : 王倩
         * isFriend : false
         * Signature :
         * jobNumber : HR180410001
         */

        private String userGuid;
        private int companyId;
        private String company;
        private String department;
        private int departmentId;
        private String post;
        private int postId;
        private String EQDCode;
        private String uname;
        private String photo;
        private String upname;
        private String location;
        private String username;
        private boolean isFriend;
        private String Signature;
        private String jobNumber;

        public String getUserGuid() {
            return userGuid;
        }

        public void setUserGuid(String userGuid) {
            this.userGuid = userGuid;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getEQDCode() {
            return EQDCode;
        }

        public void setEQDCode(String EQDCode) {
            this.EQDCode = EQDCode;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUpname() {
            return upname;
        }

        public void setUpname(String upname) {
            this.upname = upname;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isIsFriend() {
            return isFriend;
        }

        public void setIsFriend(boolean isFriend) {
            this.isFriend = isFriend;
        }

        public String getSignature() {
            return Signature;
        }

        public void setSignature(String Signature) {
            this.Signature = Signature;
        }

        public String getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(String jobNumber) {
            this.jobNumber = jobNumber;
        }
    }
}
