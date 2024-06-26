package cn.xuyinyin.magic;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.dialect.PostgresqlSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;
import org.apache.calcite.tools.RelConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 16:03
 */
public class Demo1 {
    public static Logger log = LoggerFactory.getLogger(Demo1.class);

    public static String sqlStr = "SELECT\n" +
            "\tsu.dept_id `deptId`,\n" +
            "\tsu.user_id,\n" +
            "\tsr.role_id,\n" +
            "\tsu.user_name,\n" +
            "\tsd.dept_name,\n" +
            "\tsr.role_name\n" +
            "FROM\n" +
            "\tsys_user AS su\n" +
            "JOIN sys_dept sd ON su.dept_id = sd.dept_id\n" +
            "JOIN sys_user_role sur ON sur.user_id = su.user_id\n" +
            "JOIN sys_role sr ON sur.role_id = sr.role_id\n" +
            "WHERE\n" +
            "\tsd.dept_name = '研发部门'\n" +
            "\tand su.user_name = 'admin'\n" +
            "\tand su.dept_id = 103\n" +
            "\tor sr.role_name = '超级管理员'\n" +
            "ORDER BY\n" +
            "\tsd.create_time DESC";

    public static void main(String[] args) throws SqlParseException, RelConversionException {
        // 假设SQL与MySQL相似
        SqlParser.Config parserConfig = SqlParser.config().withLex(Lex.MYSQL);
        // 创建Calcite的SqlParser实例
        SqlParser parser = SqlParser.create(sqlStr, parserConfig);
        // 解析SQL
        SqlNode sqlNode = parser.parseQuery();

        sqlNode.accept(new SqlBasicVisitor<String>() {
            public String visit(SqlCall call) {
                if (call.getKind().equals(SqlKind.SELECT)) {
                    SqlSelect select = (SqlSelect) call;

                    log.info("--------------查询列名----------------------------------------");
                    select.getSelectList().forEach(colum -> {
                        if (SqlKind.AS.equals(colum.getKind())) {
                            SqlBasicCall basicCall = (SqlBasicCall) colum;
                            log.info("{} as {}", basicCall.getOperandList().get(0).toString(), basicCall.getOperandList().get(1).toString());
                        } else if (SqlKind.IDENTIFIER.equals(colum.getKind())) {
                            log.info(colum.toString());
                        }
                    });

                    log.info("--------------From Table Info----------------------------------------");
                    select.getFrom().accept(new SqlBasicVisitor<String>() {
                        public String visit(SqlCall call) {
                            if (call.getKind().equals(SqlKind.JOIN)) {
                                SqlJoin join = (SqlJoin) call;
                                log.info("join.getRight: {},join.getCondition {}", join.getRight().toString(), join.getCondition().toString());
                                if (!join.getLeft().getKind().equals(SqlKind.JOIN)) {
                                    log.info("join.getLeft: {}", join.getLeft().toString());
                                }
                            }
                            return call.getOperator().acceptCall(this, call);
                        }
                    });

                    log.info("--------------Where  Info-----------------------------------------");
                    select.getWhere().accept(new SqlBasicVisitor<String>() {
                        public String visit(SqlCall call) {
                            if (call.getKind().equals(SqlKind.AND) || call.getKind().equals(SqlKind.OR)) {
                                SqlBasicCall sql = (SqlBasicCall) call;
                                SqlBasicCall left = (SqlBasicCall) sql.getOperandList().get(0);
                                SqlBasicCall right = (SqlBasicCall) sql.getOperandList().get(1);
                                log.info("kind: {},right: {}", sql.getKind(), right);
                                if (!left.getKind().equals(SqlKind.AND) && !left.getKind().equals(SqlKind.OR)) {
                                    log.info("left: {}", left);
                                }
                            }
                            return call.getOperator().acceptCall(this, call);
                        }
                    });
                    log.info("--------------增加查询条件----------------------------------------");
                    try {
                        SqlNode condition = SqlParser.create("1=1").parseExpression();
                        SqlNode where = SqlUtil.andExpressions(select.getWhere(), condition);
                        select.setWhere(where);
                    } catch (SqlParseException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("语句: {}", select);
                }
                return call.getOperator().acceptCall(this, call);
            }
        });

        // 创建PostgreSQL方言实例
        SqlDialect dialect = new PostgresqlSqlDialect(SqlDialect.EMPTY_CONTEXT);

        // 转换SQL到PostgreSQL格式
        String postgresSql = sqlNode.toSqlString(dialect).getSql();

        // 输出转换后的SQL
        log.info("pg sql {}", postgresSql);
    }
}
