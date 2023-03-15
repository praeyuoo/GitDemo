package com.exercise.zkspringboot.viewModel;

import com.exercise.zkspringboot.model.Form;
import com.exercise.zkspringboot.service.FormService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Slf4j
@VariableResolver(DelegatingVariableResolver.class)
public class FormViewModel extends BaseVm {

    @WireVariable
    private FormService formService;

    private Form formSearch;
    private Form formAddView;

    private String searchVisible;
    private String addViewVisible;

    private String[] strArray;
    private List<Form> formList;

    private boolean addFormDisable;
    private boolean saveBtnDisable;

    @Init
    @NotifyChange({"searchVisible", "addViewVisible", "formList"})
    public void init() {
        Map<String, Object> mapFormSearch;
        formSearch = new Form();
        formAddView = new Form();

        gotoSearch();
        formSearch.setStatus("all");
        formAddView.setStatus("A");

        try {
            mapFormSearch = mapParams(formSearch);
            formList = formService.search(checkValueAll(mapFormSearch));
        } catch (Exception e) {
            log.error("Initial error" + e);
            Messagebox.show("Initial error \n" + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange("formList")
    public void search() {
        Map<String, Object> mapFormSearch;

        try {
            mapFormSearch = mapParams(formSearch);
            formList = formService.search(checkValueAll(mapFormSearch));
        } catch (Exception e) {
            log.error("Search error " + e);
            Messagebox.show("Search error \n" + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange("formSearch")
    public void clearSearch() {
        formSearch = new Form();
        formSearch.setStatus("all");
    }

    @Command
    public void save() {
        Messagebox.show("Are you sure to save form?", "System", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION, evt1 -> {
                    if (Messagebox.ON_YES.equals(evt1.getName())) {
                        try {
                            boolean saveFlag = formService.save(formAddView);
                            if (saveFlag) {
                                Messagebox.show(
                                        "Save Success", "Info",
                                        Messagebox.OK, Messagebox.INFORMATION, evt2 -> Executions.sendRedirect("/home"));
                            } else {
                                Messagebox.show("Save Fail", "Error", Messagebox.OK, Messagebox.ERROR);
                            }
                        } catch (Exception e) {
                            log.error("Save error" + e);
                            Messagebox.show("Save Error " + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
                        }
                    }
                });
    }

    @Command
    @NotifyChange({"searchVisible", "addViewVisible", "addFormDisable", "saveBtnDisable", "formAddView"})
    public void clearAddView() {
        gotoSearch();
        addFormDisable = false;
        saveBtnDisable = false;

        formAddView = new Form();
        formAddView.setStatus("A");
    }

    @Command
    @NotifyChange({"searchVisible", "addViewVisible", "addFormDisable", "saveBtnDisable", "formAddView"})
    public void viewContent(@BindingParam("item") Form item) {
        gotoAddView();
        addFormDisable = true;
        saveBtnDisable = true;

        formAddView.setFormId(item.getFormId());
        formAddView.setAbbr(item.getAbbr());
        formAddView.setControllerValue(item.getController());
        formAddView.setCreateDate(item.getCreateDate());
        formAddView.setExpireDate(item.getExpireDate());
        formAddView.setRenewOptionValue(item.getRenewOption());

        if (item.getApprGroup() == null) {
            formAddView.setApprGroup(null);
        } else {
            formAddView.setApprGroup(item.getApprGroup());
        }

        formAddView.setDisplayLevelValue(item.getDisplayLevel());
        formAddView.setDisplaySequence(item.getDisplaySequence());
        formAddView.setMaintainByKbFlgValue(item.getMaintainByKbFlg());
        formAddView.setProgress(item.getProgress());

        if (item.getStatus().equals("~./img/inactive.png")) {
            formAddView.setStatus("I");
        } else if (item.getStatus().equals("~./img/active.png")) {
            formAddView.setStatus("A");
        }

        formAddView.setUpdUser(item.getUpdUser());
        formAddView.setUpdDt(item.getUpdDt());
    }

    @Command
    @NotifyChange({"searchVisible", "addViewVisible"})
    public void gotoAddView() {
        searchVisible = "false";
        addViewVisible = "true";
        Clients.scrollTo(0, 0);
    }

    @Command
    @NotifyChange({"searchVisible", "addViewVisible"})
    public void gotoSearch() {
        searchVisible = "true";
        addViewVisible = "false";
        Clients.scrollTo(0, 0);
    }

    public Map<String, Object> checkValueAll(Map<String, Object> map) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue().toString().equals("all")) {
                String key = entry.getKey().toString();
                if (key.equals("FORM_STATUS")) {
                    map.put(key, new Object[]{"A", "I"});
                }
            }
        }
        return map;
    }

}
