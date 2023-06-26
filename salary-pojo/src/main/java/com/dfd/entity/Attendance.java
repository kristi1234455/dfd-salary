package com.dfd.entity;

import io.swagger.annotations.ApiModelProperty;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Date;

//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Attendance {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    @Id
    private Long id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目信息表uid
     */
    @ApiModelProperty(value = "项目信息表uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    private String itemMemberUid;

    /**
     * 年
     */
    @ApiModelProperty(value = "年", name = "year")
    private Integer year;

    /**
     * 月
     */
    @ApiModelProperty(value = "月", name = "month")
    private Integer month;

    /**
     * 日
     */
    @ApiModelProperty(value = "日", name = "day")
    private Integer day;

    /**
     * 考勤状态 1全勤√；2事假О；3病假±；4探亲△；5婚假H；6丧假S；7工伤假T；8产假⊕；9公假I；10护理假F；11迟到/；12早退C；13旷工×；14公差D；15加班※；
     */
    @ApiModelProperty(value = "考勤状态 1全勤√；2事假О；3病假±；4探亲△；5婚假H；6丧假S；7工伤假T；8产假⊕；9公假I；10护理假F；11迟到/；12早退C；13旷工×；14公差D；15加班※；", name = "status")
    private Integer status;

    /**
     * 驻外天数
     */
    @ApiModelProperty(value = "驻外天数", name = "outgoingTotalDays")
    private Integer outgoingTotalDays;

    /**
     * 出勤天数
     */
    @ApiModelProperty(value = "出勤天数", name = "dutyTotalDays")
    private Integer dutyTotalDays;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createdBy")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createdTime")
//    @CreatedDate
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", name = "updatedBy")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updatedTime")
//    @LastModifiedDate
    private Date updatedTime;

    /**
     * 是否删除：0:未删除；其他时间，删除
     */
    @ApiModelProperty(value = "是否删除：0:未删除；其他时间，删除", name = "isDeleted")
    private String isDeleted;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * uid
     * @return uid uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * uid
     * @param uid uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 项目信息表uid
     * @return item_uid 项目信息表uid
     */
    public String getItemUid() {
        return itemUid;
    }

    /**
     * 项目信息表uid
     * @param itemUid 项目信息表uid
     */
    public void setItemUid(String itemUid) {
        this.itemUid = itemUid == null ? null : itemUid.trim();
    }

    /**
     * 项目人员表uid
     * @return item_member_uid 项目人员表uid
     */
    public String getItemMemberUid() {
        return itemMemberUid;
    }

    /**
     * 项目人员表uid
     * @param itemMemberUid 项目人员表uid
     */
    public void setItemMemberUid(String itemMemberUid) {
        this.itemMemberUid = itemMemberUid == null ? null : itemMemberUid.trim();
    }

    /**
     * 年
     * @return year 年
     */
    public Integer getYear() {
        return year;
    }

    /**
     * 年
     * @param year 年
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 月
     * @return month 月
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * 月
     * @param month 月
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * 日
     * @return day 日
     */
    public Integer getDay() {
        return day;
    }

    /**
     * 日
     * @param day 日
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * 考勤状态 1全勤√；2事假О；3病假±；4探亲△；5婚假H；6丧假S；7工伤假T；8产假⊕；9公假I；10护理假F；11迟到/；12早退C；13旷工×；14公差D；15加班※；
     * @return status 考勤状态 1全勤√；2事假О；3病假±；4探亲△；5婚假H；6丧假S；7工伤假T；8产假⊕；9公假I；10护理假F；11迟到/；12早退C；13旷工×；14公差D；15加班※；
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 考勤状态 1全勤√；2事假О；3病假±；4探亲△；5婚假H；6丧假S；7工伤假T；8产假⊕；9公假I；10护理假F；11迟到/；12早退C；13旷工×；14公差D；15加班※；
     * @param status 考勤状态 1全勤√；2事假О；3病假±；4探亲△；5婚假H；6丧假S；7工伤假T；8产假⊕；9公假I；10护理假F；11迟到/；12早退C；13旷工×；14公差D；15加班※；
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 驻外天数
     * @return outgoing_total_days 驻外天数
     */
    public Integer getOutgoingTotalDays() {
        return outgoingTotalDays;
    }

    /**
     * 驻外天数
     * @param outgoingTotalDays 驻外天数
     */
    public void setOutgoingTotalDays(Integer outgoingTotalDays) {
        this.outgoingTotalDays = outgoingTotalDays;
    }

    /**
     * 出勤天数
     * @return duty_total_days 出勤天数
     */
    public Integer getDutyTotalDays() {
        return dutyTotalDays;
    }

    /**
     * 出勤天数
     * @param dutyTotalDays 出勤天数
     */
    public void setDutyTotalDays(Integer dutyTotalDays) {
        this.dutyTotalDays = dutyTotalDays;
    }

    /**
     * 创建人
     * @return created_by 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 创建人
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * 创建时间
     * @return created_time 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 创建时间
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 更新人
     * @return updated_by 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 更新人
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * 更新时间
     * @return updated_time 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 更新时间
     * @param updatedTime 更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 是否删除：0:未删除；其他时间，删除
     * @return is_deleted 是否删除：0:未删除；其他时间，删除
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：0:未删除；其他时间，删除
     * @param isDeleted 是否删除：0:未删除；其他时间，删除
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}