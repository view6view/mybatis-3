/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Clinton Begin
 * @author Jeff Butler
 * @author Adam Gent
 * @author Kazuki Shimizu
 */
public abstract class AbstractSQL<T> {

  private static final String AND = ") \nAND (";
  private static final String OR = ") \nOR (";

  private final SQLStatement sql = new SQLStatement();

  public abstract T getSelf();

  public T UPDATE(String table) {
    sql().statementType = SQLStatement.StatementType.UPDATE;
    sql().tables.add(table);
    return getSelf();
  }

  public T SET(String sets) {
    sql().sets.add(sets);
    return getSelf();
  }

  /**
   * Sets the.
   *
   * @param sets
   *          the sets
   * @return the t
   * @since 3.4.2
   */
  public T SET(String... sets) {
    sql().sets.addAll(Arrays.asList(sets));
    return getSelf();
  }

  public T INSERT_INTO(String tableName) {
    sql().statementType = SQLStatement.StatementType.INSERT;
    sql().tables.add(tableName);
    return getSelf();
  }

  public T VALUES(String columns, String values) {
    INTO_COLUMNS(columns);
    INTO_VALUES(values);
    return getSelf();
  }

  /**
   * Into columns.
   *
   * @param columns
   *          the columns
   * @return the t
   * @since 3.4.2
   */
  public T INTO_COLUMNS(String... columns) {
    sql().columns.addAll(Arrays.asList(columns));
    return getSelf();
  }

  /**
   * Into values.
   *
   * @param values
   *          the values
   * @return the t
   * @since 3.4.2
   */
  public T INTO_VALUES(String... values) {
    List<String> list = sql().valuesList.get(sql().valuesList.size() - 1);
    Collections.addAll(list, values);
    return getSelf();
  }

  public T SELECT(String columns) {
    sql().statementType = SQLStatement.StatementType.SELECT;
    sql().select.add(columns);
    return getSelf();
  }

  /**
   * Select.
   *
   * @param columns
   *          the columns
   * @return the t
   * @since 3.4.2
   */
  public T SELECT(String... columns) {
    sql().statementType = SQLStatement.StatementType.SELECT;
    sql().select.addAll(Arrays.asList(columns));
    return getSelf();
  }

  public T SELECT_DISTINCT(String columns) {
    sql().distinct = true;
    SELECT(columns);
    return getSelf();
  }

  /**
   * Select distinct.
   *
   * @param columns
   *          the columns
   * @return the t
   * @since 3.4.2
   */
  public T SELECT_DISTINCT(String... columns) {
    sql().distinct = true;
    SELECT(columns);
    return getSelf();
  }

  public T DELETE_FROM(String table) {
    sql().statementType = SQLStatement.StatementType.DELETE;
    sql().tables.add(table);
    return getSelf();
  }

  public T FROM(String table) {
    sql().tables.add(table);
    return getSelf();
  }

  /**
   * From.
   *
   * @param tables
   *          the tables
   * @return the t
   * @since 3.4.2
   */
  public T FROM(String... tables) {
    sql().tables.addAll(Arrays.asList(tables));
    return getSelf();
  }

  public T JOIN(String join) {
    sql().join.add(join);
    return getSelf();
  }

  /**
   * Join.
   *
   * @param joins
   *          the joins
   * @return the t
   * @since 3.4.2
   */
  public T JOIN(String... joins) {
    sql().join.addAll(Arrays.asList(joins));
    return getSelf();
  }

  public T INNER_JOIN(String join) {
    sql().innerJoin.add(join);
    return getSelf();
  }

  /**
   * Inner join.
   *
   * @param joins
   *          the joins
   * @return the t
   * @since 3.4.2
   */
  public T INNER_JOIN(String... joins) {
    sql().innerJoin.addAll(Arrays.asList(joins));
    return getSelf();
  }

  public T LEFT_OUTER_JOIN(String join) {
    sql().leftOuterJoin.add(join);
    return getSelf();
  }

  /**
   * Left outer join.
   *
   * @param joins
   *          the joins
   * @return the t
   * @since 3.4.2
   */
  public T LEFT_OUTER_JOIN(String... joins) {
    sql().leftOuterJoin.addAll(Arrays.asList(joins));
    return getSelf();
  }

  public T RIGHT_OUTER_JOIN(String join) {
    sql().rightOuterJoin.add(join);
    return getSelf();
  }

  /**
   * Right outer join.
   *
   * @param joins
   *          the joins
   * @return the t
   * @since 3.4.2
   */
  public T RIGHT_OUTER_JOIN(String... joins) {
    sql().rightOuterJoin.addAll(Arrays.asList(joins));
    return getSelf();
  }

  public T OUTER_JOIN(String join) {
    sql().outerJoin.add(join);
    return getSelf();
  }

  /**
   * Outer join.
   *
   * @param joins
   *          the joins
   * @return the t
   * @since 3.4.2
   */
  public T OUTER_JOIN(String... joins) {
    sql().outerJoin.addAll(Arrays.asList(joins));
    return getSelf();
  }

  public T WHERE(String conditions) {
    sql().where.add(conditions);
    sql().lastList = sql().where;
    return getSelf();
  }

  /**
   * Where.
   *
   * @param conditions
   *          the conditions
   * @return the t
   * @since 3.4.2
   */
  public T WHERE(String... conditions) {
    sql().where.addAll(Arrays.asList(conditions));
    sql().lastList = sql().where;
    return getSelf();
  }

  public T OR() {
    sql().lastList.add(OR);
    return getSelf();
  }

  public T AND() {
    sql().lastList.add(AND);
    return getSelf();
  }

  public T GROUP_BY(String columns) {
    sql().groupBy.add(columns);
    return getSelf();
  }

  /**
   * Group by.
   *
   * @param columns
   *          the columns
   * @return the t
   * @since 3.4.2
   */
  public T GROUP_BY(String... columns) {
    sql().groupBy.addAll(Arrays.asList(columns));
    return getSelf();
  }

  public T HAVING(String conditions) {
    sql().having.add(conditions);
    sql().lastList = sql().having;
    return getSelf();
  }

  /**
   * Having.
   *
   * @param conditions
   *          the conditions
   * @return the t
   * @since 3.4.2
   */
  public T HAVING(String... conditions) {
    sql().having.addAll(Arrays.asList(conditions));
    sql().lastList = sql().having;
    return getSelf();
  }


  public T ORDER_BY(String columns) {
    sql().orderBy.add(columns);
    return getSelf();
  }

  /**
   * Order by.
   *
   * @param columns
   *          the columns
   * @return the t
   * @since 3.4.2
   */
  public T ORDER_BY(String... columns) {
    sql().orderBy.addAll(Arrays.asList(columns));
    return getSelf();
  }

  /**
   * Set the limit variable string(e.g. {@code "#{limit}"}).<br>
   * 设置查询行数限制值，采用OFFSET_LIMIT策略
   * @param variable a limit variable string
   * @return a self instance
   * @see #OFFSET(String)
   * @since 3.5.2
   */
  public T LIMIT(String variable) {
    sql().limit = variable;
    sql().limitingRowsStrategy = SQLStatement.LimitingRowsStrategy.OFFSET_LIMIT;
    return getSelf();
  }

  /**
   * Set the limit value.<br>
   * 设置查询行数限制值
   * @param value an offset value
   * @return a self instance
   * @see #OFFSET(long)
   * @since 3.5.2
   */
  public T LIMIT(int value) {
    return LIMIT(String.valueOf(value));
  }

  /**
   * Set the offset variable string(e.g. {@code "#{offset}"}).<br>
   * 设置查询行数偏移量，采用OFFSET_LIMIT策略
   * @param variable a offset variable string
   * @return a self instance
   * @see #LIMIT(String)
   * @since 3.5.2
   */
  public T OFFSET(String variable) {
    sql().offset = variable;
    sql().limitingRowsStrategy = SQLStatement.LimitingRowsStrategy.OFFSET_LIMIT;
    return getSelf();
  }

  /**
   * Set the offset value.<br>
   * 设置偏移行数量值
   * @param value an offset value
   * @return a self instance
   * @see #LIMIT(int)
   * @since 3.5.2
   */
  public T OFFSET(long value) {
    return OFFSET(String.valueOf(value));
  }

  /**
   * Set the fetch first rows variable string(e.g. {@code "#{fetchFirstRows}"}).<br>
   * 设置查询行数限制值，采用ISO策略
   * @param variable a fetch first rows variable string
   * @return a self instance
   * @see #OFFSET_ROWS(String)
   * @since 3.5.2
   */
  public T FETCH_FIRST_ROWS_ONLY(String variable) {
    sql().limit = variable;
    sql().limitingRowsStrategy = SQLStatement.LimitingRowsStrategy.ISO;
    return getSelf();
  }

  /**
   * Set the fetch first rows value.<br>
   * 设置查询行数限制值
   * @param value a fetch first rows value
   * @return a self instance
   * @see #OFFSET_ROWS(long)
   * @since 3.5.2
   */
  public T FETCH_FIRST_ROWS_ONLY(int value) {
    return FETCH_FIRST_ROWS_ONLY(String.valueOf(value));
  }

  /**
   * Set the offset rows variable string(e.g. {@code "#{offset}"}).
   * 设置查询行数偏移量，采用ISO策略
   * @param variable a offset rows variable string
   * @return a self instance
   * @see #FETCH_FIRST_ROWS_ONLY(String)
   * @since 3.5.2
   */
  public T OFFSET_ROWS(String variable) {
    sql().offset = variable;
    sql().limitingRowsStrategy = SQLStatement.LimitingRowsStrategy.ISO;
    return getSelf();
  }

  /**
   * Set the offset rows value.<br>
   * 设置偏移行数量值
   * @param value an offset rows value
   * @return a self instance
   * @see #FETCH_FIRST_ROWS_ONLY(int)
   * @since 3.5.2
   */
  public T OFFSET_ROWS(long value) {
    return OFFSET_ROWS(String.valueOf(value));
  }

  /**
   * used to add a new inserted row while do multi-row insert.<br>
   * 用于在进行多行插入时添加新的插入行。
   *
   * @return the t
   * @since 3.5.2
   */
  public T ADD_ROW() {
    sql().valuesList.add(new ArrayList<>());
    return getSelf();
  }

  /**
   * 获取sql语句SQLStatement对象
   * @return
   */
  private SQLStatement sql() {
    return sql;
  }

  /**
   * 通过追加的方式实现sql拼接
   * @param a
   * @param <A>
   * @return
   */
  public <A extends Appendable> A usingAppender(A a) {
    sql().sql(a);
    return a;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sql().sql(sb);
    return sb.toString();
  }

  /**
   * 安全的sql 建造者模式中的建造者,
   */
  private static class SafeAppendable {
    /**
     * 作用：附加字符序列和值的对象，
     * 规则：字符为Unicode。
     * 规则：线程不一定安全，是否安全是由实现类控制。
     * 规则：错误（异常）可能传递到调用程序。因为方法本身既有抛出异常：IOException  和    IndexOutOfBoundsException
     */
    private final Appendable appendable;
    /**
     * 初始化设置empty为true
     */
    private boolean empty = true;

    public SafeAppendable(Appendable a) {
      // 调用父类object的构造方法
      super();
      this.appendable = a;
    }

    /**
     * 1. CharSequence类是java.lang包下的一个接口，此接口对多种不同的对char访问的统一接口，像String、StringBuffer、StringBuilder类都是CharSequence的子接口；<br>
     * 2. CharSequence类和String类都可以定义字符串，但是String定义的字符串只能读，CharSequence定义的字符串是可读可写的；<br>
     * 3. 对于抽象类或者接口来说不可以直接使用new的方式创建对象，但是可以直接给它赋值；
     * @param s
     * @return
     */
    public SafeAppendable append(CharSequence s) {
      try {
        if (empty && s.length() > 0) {
          empty = false;
        }
        // 追加 sql 语句
        appendable.append(s);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return this;
    }

    public boolean isEmpty() {
      return empty;
    }

  }

  /**
   * sql语句类
   */
  private static class SQLStatement {

    /**
     * 枚举类sql的四种操作
     */
    public enum StatementType {
      DELETE, INSERT, SELECT, UPDATE
    }

    /**
     * 枚举实现——查询数据行数限制的"策略"
     */
    private enum LimitingRowsStrategy {
      // 没有行数限制策略
      NOP {
        @Override
        protected void appendClause(SafeAppendable builder, String offset, String limit) {
          // NOP
        }
      },
      // OFFSET 和 FETCH 策略
      ISO {
        @Override
        protected void appendClause(SafeAppendable builder, String offset, String limit) {
          // offset 10 rows ： 作用就是（假如20条数据，从第11条开始计算，前面10条就不要了）
          // offset 0 rows ： 实际查询中不起任何作用
          if (offset != null) {
            builder.append(" OFFSET ").append(offset).append(" ROWS");
          }
          // 从头部开始限制查找的条数
          // FIRST和NEXT是同义词，因此，可以互换使用它们
          if (limit != null) {
            builder.append(" FETCH FIRST ").append(limit).append(" ROWS ONLY");
          }
        }
      },
      // 使用limit 和 offset 限制行数
      OFFSET_LIMIT {
        @Override
        protected void appendClause(SafeAppendable builder, String offset, String limit) {
          if (limit != null) {
            builder.append(" LIMIT ").append(limit);
          }
          if (offset != null) {
            builder.append(" OFFSET ").append(offset);
          }
        }
      };

      /**
       * 抽象方法：查询数据行数限制的"策略"
       * @param builder
       * @param offset
       * @param limit
       */
      protected abstract void appendClause(SafeAppendable builder, String offset, String limit);

    }

    // 语句类型，对应的枚举类
    StatementType statementType;

    List<String> sets = new ArrayList<>();

    // 需要select的字段集合
    List<String> select = new ArrayList<>();

    // 需要被查下的table集合
    List<String> tables = new ArrayList<>();

    List<String> join = new ArrayList<>();
    List<String> innerJoin = new ArrayList<>();
    List<String> outerJoin = new ArrayList<>();
    List<String> leftOuterJoin = new ArrayList<>();
    List<String> rightOuterJoin = new ArrayList<>();
    List<String> where = new ArrayList<>();
    List<String> having = new ArrayList<>();
    List<String> groupBy = new ArrayList<>();
    List<String> orderBy = new ArrayList<>();
    List<String> lastList = new ArrayList<>();
    List<String> columns = new ArrayList<>();
    List<List<String>> valuesList = new ArrayList<>();
    boolean distinct;
    String offset;
    String limit;
    LimitingRowsStrategy limitingRowsStrategy = LimitingRowsStrategy.NOP;

    public SQLStatement() {
      // Prevent Synthetic Access
      valuesList.add(new ArrayList<>());
    }

    /**
     * 连接sql的7个部分的方法
     * @param builder sql语句建造者对象
     * @param keyword sql关键字：select from distinct等等
     * @param parts 跟在sql关键字之后的字段集合，可能是字段名、表名
     * @param open 左边界：可能是空字符串或者 左括号
     * @param close 右边界：可能是空字符串或者 右括号
     * @param conjunction 此部分parts的尾部字符：可能是逗号（字段）、and（where条件），join（连表条件），空字符
     */
    private void sqlClause(SafeAppendable builder, String keyword, List<String> parts, String open, String close,
                           String conjunction) {
      if (!parts.isEmpty()) {
        if (!builder.isEmpty()) {
          builder.append("\n");
        }
        builder.append(keyword);
        builder.append(" ");
        builder.append(open);
        String last = "________";
        for (int i = 0, n = parts.size(); i < n; i++) {
          String part = parts.get(i);
          if (i > 0 && !part.equals(AND) && !part.equals(OR) && !last.equals(AND) && !last.equals(OR)) {
            builder.append(conjunction);
          }
          builder.append(part);
          last = part;
        }
        builder.append(close);
      }
    }

    /**
     * 7部分的查询语句：<br>
     * SELECT<br>
     * FROM<br>
     * JOIN<br>
     * WHERE<br>
     * GROUP BY<br>
     * HAVING<br>
     * ORDER BY<br>
     * @param builder
     * @return
     */
    private String selectSQL(SafeAppendable builder) {
      if (distinct) {
        sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
      } else {
        sqlClause(builder, "SELECT", select, "", "", ", ");
      }

      sqlClause(builder, "FROM", tables, "", "", ", ");
      joins(builder);
      sqlClause(builder, "WHERE", where, "(", ")", " AND ");
      sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
      sqlClause(builder, "HAVING", having, "(", ")", " AND ");
      sqlClause(builder, "ORDER BY", orderBy, "", "", ", ");
      limitingRowsStrategy.appendClause(builder, offset, limit);
      return builder.toString();
    }

    /**
     * sql连表的语句
     * @param builder
     */
    private void joins(SafeAppendable builder) {
      sqlClause(builder, "JOIN", join, "", "", "\nJOIN ");
      sqlClause(builder, "INNER JOIN", innerJoin, "", "", "\nINNER JOIN ");
      sqlClause(builder, "OUTER JOIN", outerJoin, "", "", "\nOUTER JOIN ");
      sqlClause(builder, "LEFT OUTER JOIN", leftOuterJoin, "", "", "\nLEFT OUTER JOIN ");
      sqlClause(builder, "RIGHT OUTER JOIN", rightOuterJoin, "", "", "\nRIGHT OUTER JOIN ");
    }

    /**
     * 插入数据的sql语句
     * @param builder
     * @return
     */
    private String insertSQL(SafeAppendable builder) {
      sqlClause(builder, "INSERT INTO", tables, "", "", "");
      sqlClause(builder, "", columns, "(", ")", ", ");
      for (int i = 0; i < valuesList.size(); i++) {
        sqlClause(builder, i > 0 ? "," : "VALUES", valuesList.get(i), "(", ")", ", ");
      }
      return builder.toString();
    }

    /**
     * 删除数据的sql语句
     * @param builder
     * @return
     */
    private String deleteSQL(SafeAppendable builder) {
      sqlClause(builder, "DELETE FROM", tables, "", "", "");
      sqlClause(builder, "WHERE", where, "(", ")", " AND ");
      limitingRowsStrategy.appendClause(builder, null, limit);
      return builder.toString();
    }

    /**
     * 更新数据的sql语句
     * @param builder
     * @return
     */
    private String updateSQL(SafeAppendable builder) {
      sqlClause(builder, "UPDATE", tables, "", "", "");
      joins(builder);
      sqlClause(builder, "SET", sets, "", "", ", ");
      sqlClause(builder, "WHERE", where, "(", ")", " AND ");
      limitingRowsStrategy.appendClause(builder, null, limit);
      return builder.toString();
    }

    /**
     * 同传入一个实现Appendable的语句实现sql语句的拼接处理
     * @param a
     * @return
     */
    public String sql(Appendable a) {
      SafeAppendable builder = new SafeAppendable(a);
      if (statementType == null) {
        return null;
      }

      String answer;

      switch (statementType) {
        case DELETE:
          answer = deleteSQL(builder);
          break;

        case INSERT:
          answer = insertSQL(builder);
          break;

        case SELECT:
          answer = selectSQL(builder);
          break;

        case UPDATE:
          answer = updateSQL(builder);
          break;

        default:
          answer = null;
      }

      return answer;
    }
  }
}
