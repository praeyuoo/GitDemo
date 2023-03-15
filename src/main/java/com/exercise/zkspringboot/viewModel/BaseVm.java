package com.exercise.zkspringboot.viewModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseVm {

    public Map<String, Object> mapParams(Object data) {
        Map<String, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> params = objectMapper.convertValue(data, Map.class);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof String || entry.getValue() instanceof Integer) {
                map.put(entry.getKey(), entry.getValue());
            }
        }

        return map;
    }

}
