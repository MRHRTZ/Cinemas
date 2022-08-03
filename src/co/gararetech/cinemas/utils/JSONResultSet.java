/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.gararetech.cinemas.utils;

import java.math.BigDecimal;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class JSONResultSet {

    private ResultSet resultSet;

    public JSONResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public JSONArray mapResultSet() throws SQLException, JSONException {
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = null;
        ResultSet rs = this.resultSet;
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        do {
            if (rs.isBeforeFirst()) {
                rs.next();
            }
            jsonObject = new JSONObject();
            for (int index = 1; index <= columnCount; index++) {
                String column = rsmd.getColumnName(index);
                Object value = rs.getObject(column);
                if (value == null) {
                    jsonObject.put(column, "");
                } else if (value instanceof Integer) {
                    jsonObject.put(column, (Integer) value);
                } else if (value instanceof String) {
                    jsonObject.put(column, (String) value);
                } else if (value instanceof Boolean) {
                    jsonObject.put(column, (Boolean) value);
                } else if (value instanceof Date) {
                    jsonObject.put(column, ((Date) value).getTime());
                } else if (value instanceof Long) {
                    jsonObject.put(column, (Long) value);
                } else if (value instanceof Double) {
                    jsonObject.put(column, (Double) value);
                } else if (value instanceof Float) {
                    jsonObject.put(column, (Float) value);
                } else if (value instanceof BigDecimal) {
                    jsonObject.put(column, (BigDecimal) value);
                } else if (value instanceof Byte) {
                    jsonObject.put(column, (Byte) value);
                } else if (value instanceof byte[]) {
                    jsonObject.put(column, (byte[]) value);
                } else {
                    throw new IllegalArgumentException("Unmappable object type: " + value.getClass());
                }
            }
            jArray.put(jsonObject);
        } while (rs.next());
        return jArray;
    }
}
