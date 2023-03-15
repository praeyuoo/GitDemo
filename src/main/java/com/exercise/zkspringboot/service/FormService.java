package com.exercise.zkspringboot.service;

import com.exercise.zkspringboot.model.Form;
import com.exercise.zkspringboot.repository.FormDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FormService {

    @Autowired
    FormDao formDao;

    public Form getById(Form t) {
        return formDao.getById(t);
    }

    public List<Form> search(Map<String, Object> mapFormSearch) {
        return formDao.search(mapFormSearch);
    }

    public boolean save(Form formAddView) {
        return formDao.save(formAddView);
    }

}











