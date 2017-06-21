package com.yuzhi.fine.model.LXLogin;

/**
 * Created by JiaoJianJun on 2017/6/21.
 * 用户登录信息
 */

public class LXLoginBean {
    private String AccountHolder;//开户人姓名
    private String Address;// 详细地址
    private String ApproveRemark;// 认证备注
    private String ApproveState;
    private String Balance;// 账户余额
    private String BalanceAmount;//账户余额
    private String BalanceNoCash;// 账户余额（悬赏金额）
    private String BankAccount;// 银行账号
    private String BirthDate;//// 出生日期
    private String BusinessLicenseID;// 营业执照号
    private String BusinessLicenseImgID;// 企业营业执照图片ID
    private String CellPhone;// 手机号
    private String CheckState;// 认证状态  1未认证 2等待认证  3认证没通过 4认证通过
    private String City;// 城市
    private String CityName;// 地市(名称)
    private String Cohr;// 公司昵称
    private String CompanyIndustry;// 所属行业
    private String CompanyIndustryID;//行业分类ID
    private String CompanyName;// 公司全称
    private String CompanyNature;// 公司性质
    private String CompanyStaff;// 公司规模
    private String CompanyTel;// 公司电话
    private String Country;// 县
    private String CountryName;// 县区(名称)
    private String CreateDate;// 注册时间
    private String CreatePlatform;// 注册平台 1 PC  2 Android  3 苹果
    private String CreditCertificateImgID;// 信用证图片ID
    private String Department;// 所属部门
    private String Email;// 邮箱
    private String Expenditure;// 消费总金额
    private String Experience;// 经验值
    private String GradeID;// 会员等级ID
    private String GradeName;// 用户等级名称
    private String ID;
    private String ImgFilePath;// 头像文件路径
    private String ImgID;// 头像ID
    private String IsAdmin;// 是否管理员
    private String IsFounder;// 是否超级管理员
    private String IsGeneration;
    private String IsHasPush;// 是否推送消息 （App设置用）
    private String IsHasSound;// 是否声音提示（App设置用）
    private String IsLocked;// 是否锁定
    private String IsUsed;// 是否启用
    private String IshasDisplay;// 是否显示详情（App设置用）
    private String LastLoginDate;// 最近登录时间
    private String LastPasswordChangedDate;// 最近改密码时间
    private String LastTradePasswordChangedDate;// 最近改支付密码时间
    private String Latitude;// 纬度值
    private String LegalIDCardBackID;// 法人身份证(反)ID
    private String LegalIDCardID;// 法人身份证ID
    private String LinkMan;// 联系人
    private String LoginTimes;// 登录次数
    private String LogoFilePath;// logo文件路径
    private String LogoID;// 会员LogoID
    private String Longitude;// 经度值
    private String Motto;// 座右铭（签名）
    private String MySummary;// 我的简介（兴趣爱好）
    private String NickName;// 昵称
    private String OpenBank;// 开户银行
    private String OrganizationCode;// 组织机构代码
    private String OrganizationCodeImgID;// 组织机构代码证图片ID
    private String PID;
    private String Password;// 登录密码
    private String Points;// 积分
    private String Position;// 职位
    private String Province;// 省份
    private String ProvinceName;// 所在省(名称)
    private String QQ;// QQ
    private String SafeValue;// 安全等级
    private String TaxRegistrationCertificateImgID;// 税务登记证图片ID
    private String TotalIncomeAmount;// 收益总金额(所有角色的收益总和)
    private String TradePassword;// 支付密码
    private String UserAge;// 用户的年龄
    private String UserID;// 用户ID
    private String UserName;// 用户名
    private String UserRealName;// 用户真实姓名
    private String UserSex;//性别  0：女 1：男
    private String UserType;// 用户类型(1会员，2供应商，3设计师)

    public String getAccountHolder() {
        return AccountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        AccountHolder = accountHolder;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getApproveRemark() {
        return ApproveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        ApproveRemark = approveRemark;
    }

    public String getApproveState() {
        return ApproveState;
    }

    public void setApproveState(String approveState) {
        ApproveState = approveState;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        BalanceAmount = balanceAmount;
    }

    public String getBalanceNoCash() {
        return BalanceNoCash;
    }

    public void setBalanceNoCash(String balanceNoCash) {
        BalanceNoCash = balanceNoCash;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getBusinessLicenseID() {
        return BusinessLicenseID;
    }

    public void setBusinessLicenseID(String businessLicenseID) {
        BusinessLicenseID = businessLicenseID;
    }

    public String getBusinessLicenseImgID() {
        return BusinessLicenseImgID;
    }

    public void setBusinessLicenseImgID(String businessLicenseImgID) {
        BusinessLicenseImgID = businessLicenseImgID;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public String getCheckState() {
        return CheckState;
    }

    public void setCheckState(String checkState) {
        CheckState = checkState;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCohr() {
        return Cohr;
    }

    public void setCohr(String cohr) {
        Cohr = cohr;
    }

    public String getCompanyIndustry() {
        return CompanyIndustry;
    }

    public void setCompanyIndustry(String companyIndustry) {
        CompanyIndustry = companyIndustry;
    }

    public String getCompanyIndustryID() {
        return CompanyIndustryID;
    }

    public void setCompanyIndustryID(String companyIndustryID) {
        CompanyIndustryID = companyIndustryID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyNature() {
        return CompanyNature;
    }

    public void setCompanyNature(String companyNature) {
        CompanyNature = companyNature;
    }

    public String getCompanyStaff() {
        return CompanyStaff;
    }

    public void setCompanyStaff(String companyStaff) {
        CompanyStaff = companyStaff;
    }

    public String getCompanyTel() {
        return CompanyTel;
    }

    public void setCompanyTel(String companyTel) {
        CompanyTel = companyTel;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreatePlatform() {
        return CreatePlatform;
    }

    public void setCreatePlatform(String createPlatform) {
        CreatePlatform = createPlatform;
    }

    public String getCreditCertificateImgID() {
        return CreditCertificateImgID;
    }

    public void setCreditCertificateImgID(String creditCertificateImgID) {
        CreditCertificateImgID = creditCertificateImgID;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getExpenditure() {
        return Expenditure;
    }

    public void setExpenditure(String expenditure) {
        Expenditure = expenditure;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getGradeID() {
        return GradeID;
    }

    public void setGradeID(String gradeID) {
        GradeID = gradeID;
    }

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String gradeName) {
        GradeName = gradeName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImgFilePath() {
        return ImgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        ImgFilePath = imgFilePath;
    }

    public String getImgID() {
        return ImgID;
    }

    public void setImgID(String imgID) {
        ImgID = imgID;
    }

    public String getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        IsAdmin = isAdmin;
    }

    public String getIsFounder() {
        return IsFounder;
    }

    public void setIsFounder(String isFounder) {
        IsFounder = isFounder;
    }

    public String getIsGeneration() {
        return IsGeneration;
    }

    public void setIsGeneration(String isGeneration) {
        IsGeneration = isGeneration;
    }

    public String getIsHasPush() {
        return IsHasPush;
    }

    public void setIsHasPush(String isHasPush) {
        IsHasPush = isHasPush;
    }

    public String getIsHasSound() {
        return IsHasSound;
    }

    public void setIsHasSound(String isHasSound) {
        IsHasSound = isHasSound;
    }

    public String getIsLocked() {
        return IsLocked;
    }

    public void setIsLocked(String isLocked) {
        IsLocked = isLocked;
    }

    public String getIsUsed() {
        return IsUsed;
    }

    public void setIsUsed(String isUsed) {
        IsUsed = isUsed;
    }

    public String getIshasDisplay() {
        return IshasDisplay;
    }

    public void setIshasDisplay(String ishasDisplay) {
        IshasDisplay = ishasDisplay;
    }

    public String getLastLoginDate() {
        return LastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        LastLoginDate = lastLoginDate;
    }

    public String getLastPasswordChangedDate() {
        return LastPasswordChangedDate;
    }

    public void setLastPasswordChangedDate(String lastPasswordChangedDate) {
        LastPasswordChangedDate = lastPasswordChangedDate;
    }

    public String getLastTradePasswordChangedDate() {
        return LastTradePasswordChangedDate;
    }

    public void setLastTradePasswordChangedDate(String lastTradePasswordChangedDate) {
        LastTradePasswordChangedDate = lastTradePasswordChangedDate;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLegalIDCardBackID() {
        return LegalIDCardBackID;
    }

    public void setLegalIDCardBackID(String legalIDCardBackID) {
        LegalIDCardBackID = legalIDCardBackID;
    }

    public String getLegalIDCardID() {
        return LegalIDCardID;
    }

    public void setLegalIDCardID(String legalIDCardID) {
        LegalIDCardID = legalIDCardID;
    }

    public String getLinkMan() {
        return LinkMan;
    }

    public void setLinkMan(String linkMan) {
        LinkMan = linkMan;
    }

    public String getLoginTimes() {
        return LoginTimes;
    }

    public void setLoginTimes(String loginTimes) {
        LoginTimes = loginTimes;
    }

    public String getLogoFilePath() {
        return LogoFilePath;
    }

    public void setLogoFilePath(String logoFilePath) {
        LogoFilePath = logoFilePath;
    }

    public String getLogoID() {
        return LogoID;
    }

    public void setLogoID(String logoID) {
        LogoID = logoID;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getMotto() {
        return Motto;
    }

    public void setMotto(String motto) {
        Motto = motto;
    }

    public String getMySummary() {
        return MySummary;
    }

    public void setMySummary(String mySummary) {
        MySummary = mySummary;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getOpenBank() {
        return OpenBank;
    }

    public void setOpenBank(String openBank) {
        OpenBank = openBank;
    }

    public String getOrganizationCode() {
        return OrganizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        OrganizationCode = organizationCode;
    }

    public String getOrganizationCodeImgID() {
        return OrganizationCodeImgID;
    }

    public void setOrganizationCodeImgID(String organizationCodeImgID) {
        OrganizationCodeImgID = organizationCodeImgID;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getSafeValue() {
        return SafeValue;
    }

    public void setSafeValue(String safeValue) {
        SafeValue = safeValue;
    }

    public String getTaxRegistrationCertificateImgID() {
        return TaxRegistrationCertificateImgID;
    }

    public void setTaxRegistrationCertificateImgID(String taxRegistrationCertificateImgID) {
        TaxRegistrationCertificateImgID = taxRegistrationCertificateImgID;
    }

    public String getTotalIncomeAmount() {
        return TotalIncomeAmount;
    }

    public void setTotalIncomeAmount(String totalIncomeAmount) {
        TotalIncomeAmount = totalIncomeAmount;
    }

    public String getTradePassword() {
        return TradePassword;
    }

    public void setTradePassword(String tradePassword) {
        TradePassword = tradePassword;
    }

    public String getUserAge() {
        return UserAge;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserRealName() {
        return UserRealName;
    }

    public void setUserRealName(String userRealName) {
        UserRealName = userRealName;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
