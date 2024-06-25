package mybatis.handler;

import api.Message;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import util.FastJsonConvertUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author wangfin
 * @Date 2024/6/26
 * @Desc
 */
public class MessageJsonTypeHandler extends BaseTypeHandler<Message> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Message message, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, FastJsonConvertUtil.convertObjectToJSON(message));
    }

    @Override
    public Message getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        if (null != value && !StringUtils.isBlank(value)) {
            return FastJsonConvertUtil.convertJsonToObject(resultSet.getString(s), Message.class);
        }
        return null;
    }

    @Override
    public Message getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Message getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
