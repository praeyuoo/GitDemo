package com.exercise.zkspringboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form {

    @JsonProperty("FORM_ID")
    private Integer formId;

    @JsonProperty("FORM_ABBR")
    private String abbr;

    @JsonProperty("FORM_STATUS")
    private String status;

    @JsonProperty("FORM_PROGRESS")
    private String progress;

    @JsonProperty("FORM_CREATE_DATE")
    private Date createDate;

    private String createDateValue;

    @JsonProperty("FORM_EXPIRE_DATE")
    private Date expireDate;

    private String expireDateValue;

    @JsonProperty("FORM_RENEW_OPTION")
    private String renewOption;

    private String renewOptionValue;

    @JsonProperty("FORM_CONTROLLER")
    private String controller;

    private String controllerValue;

    @JsonProperty("FORM_APPR_GROUP")
    private Integer apprGroup;

    @JsonProperty("FORM_DISPLAY_LEVEL")
    private String displayLevel;

    private String displayLevelValue;

    @JsonProperty("FORM_DISPLAY_SEQUENCE")
    private Integer displaySequence;

    @JsonProperty("FORM_MAINTAIN_BY_KB_FLG")
    private String maintainByKbFlg;

    private String maintainByKbFlgValue;

    @JsonProperty("FORM_UPD_USER")
    private String updUser;

    @JsonProperty("FORM_UPD_DT")
    private Timestamp updDt;

    private String updDtValue;

}
