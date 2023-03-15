package com.exercise.zkspringboot.repository;

import java.util.Map;

public class BaseDao {

    public String sqlQueryWithCondition(Map<String, Object> mapFormSearch, String SQL_QUERY) {
        StringBuilder SQL_QUERY_WITH_CONDITION = new StringBuilder();
        SQL_QUERY_WITH_CONDITION.append(SQL_QUERY);

        for (Map.Entry entry : mapFormSearch.entrySet()) {
            if (entry.getValue() instanceof Object[]) {
                SQL_QUERY_WITH_CONDITION
                        .append(" AND ")
                        .append(entry.getKey())
                        .append(" IN (");
                for (Object element : (Object[]) entry.getValue())
                    SQL_QUERY_WITH_CONDITION
                            .append("'")
                            .append(element)
                            .append("',");

                if (SQL_QUERY_WITH_CONDITION.length() > 0)
                    SQL_QUERY_WITH_CONDITION.deleteCharAt(SQL_QUERY_WITH_CONDITION.length() - 1);
                SQL_QUERY_WITH_CONDITION.append(")");
            } else
                SQL_QUERY_WITH_CONDITION
                        .append(" AND ")
                        .append(entry.getKey())
                        .append(" = '")
                        .append(entry.getValue())
                        .append("'");
        }
        return SQL_QUERY_WITH_CONDITION.toString();
    }

}
