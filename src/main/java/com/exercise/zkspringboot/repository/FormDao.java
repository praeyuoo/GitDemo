package com.exercise.zkspringboot.repository;

import com.exercise.zkspringboot.model.Form;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.text.MessageFormat.format;

@Slf4j
@Repository
public class FormDao extends BaseDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_QUERY_BY_ID = "SELECT * FROM formconsent WHERE FORM_ID = ?";
    private static final String SQL_QUERY = "SELECT * FROM formconsent WHERE 1=1";
    private static final String SQL_INSERT = "INSERT INTO formconsent (FORM_ABBR, FORM_STATUS, FORM_PROGRESS, " +
            "FORM_CREATE_DATE, FORM_EXPIRE_DATE, FORM_RENEW_OPTION, FORM_CONTROLLER, FORM_DISPLAY_LEVEL, " +
            "FORM_DISPLAY_SEQUENCE, FORM_MAINTAIN_BY_KB_FLG, FORM_UPD_USER, FORM_UPD_DT) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

    public Form getById(Form t) {
        log.debug("****START SEARCH****");
        log.debug(format(SQL_QUERY_BY_ID));
        log.debug(ToStringBuilder.reflectionToString(t));
        List<Form> items = jdbcTemplate.query(SQL_QUERY_BY_ID, new Object[]{t.getFormId()}, new FormMapping());
        log.debug("****END SEARCH****");
        return !items.isEmpty() ? items.get(0) : null;
    }

    public List<Form> search(Map<String, Object> mapFormSearch) {
        log.debug("****START SEARCH****");
        String SQL_QUERY_WITH_CONDITION = sqlQueryWithCondition(mapFormSearch, SQL_QUERY);
        log.debug(format(SQL_QUERY_WITH_CONDITION));
        List<Form> items = jdbcTemplate.query(SQL_QUERY_WITH_CONDITION, new FormMapping());
        log.debug("****END SEARCH****");
        return !items.isEmpty() ? items : null;
    }

    public Boolean save(Form formAddView) {
        log.debug("****START SAVE****");
        log.debug(format(SQL_INSERT));
        log.debug(ToStringBuilder.reflectionToString(formAddView));
        jdbcTemplate.update(SQL_INSERT,
                formAddView.getAbbr(),
                formAddView.getStatus(),
                "P",
                formAddView.getCreateDate(),
                formAddView.getExpireDate(),
                formAddView.getRenewOption(),
                formAddView.getController(),
                formAddView.getDisplayLevel(),
                formAddView.getDisplaySequence(),
                formAddView.getMaintainByKbFlg(),
                "adminx");
        log.debug("****END SAVE****");
        return true;
    }

    private static class FormMapping implements RowMapper<Form> {

        public Form mapRow(ResultSet rs, int line) throws SQLException {
            ResultSetExtractor<Form> extractor = rs1 -> {
                Form model = new Form();
                model.setFormId(rs1.getInt("FORM_ID"));
                model.setAbbr(rs.getString("FORM_ABBR").trim());
                model.setStatus(rs.getString("FORM_STATUS").equals("A") ? "~./img/active.png" :
                        rs.getString("FORM_STATUS").equals("I") ? "~./img/inactive.png" : null);
                model.setProgress(rs.getString("FORM_PROGRESS").equals("P") ? "Publish" : null);

                model.setCreateDate(rs.getDate("FORM_CREATE_DATE"));
                model.setCreateDateValue(dateToString(rs.getDate("FORM_CREATE_DATE")));

                model.setExpireDate(rs.getDate("FORM_EXPIRE_DATE"));
                model.setExpireDateValue(dateToString(rs.getDate("FORM_EXPIRE_DATE")));

                model.setRenewOption(
                        rs.getString("FORM_RENEW_OPTION") != null ?
                                rs.getString("FORM_RENEW_OPTION").trim() : "");
                model.setController(rs.getInt("FORM_CONTROLLER") == 1 ? "KBank" : "");
                model.setApprGroup(rs.getInt("FORM_APPR_GROUP") != 0 ?
                        rs.getInt("FORM_APPR_GROUP") : null);
                model.setDisplayLevel(rs.getString("FORM_DISPLAY_LEVEL").equals("R") ?
                        "Receiver" : "");
                model.setDisplaySequence(rs.getInt("FORM_DISPLAY_SEQUENCE"));
                model.setMaintainByKbFlg(
                        rs.getString("FORM_MAINTAIN_BY_KB_FLG").equals("Y") ? "Yes" :
                                rs.getString("FORM_MAINTAIN_BY_KB_FLG").equals("N") ? "No" : null);
                model.setUpdUser(rs.getString("FORM_UPD_USER").trim());

                model.setUpdDt(rs.getTimestamp("FORM_UPD_DT"));
                model.setUpdDtValue(timezoneToString(rs.getTimestamp("FORM_UPD_DT")));
                return model;
            };
            return extractor.extractData(rs);
        }

        public String dateToString(Date date) {
            String pattern = "dd/MM/yyyy";
            DateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
            return df.format(date);
        }

        public String timezoneToString(Date date) {
            String pattern = "dd/MM/yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
            return df.format(date);
        }
    }


}
