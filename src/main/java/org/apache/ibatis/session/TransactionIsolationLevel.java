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
package org.apache.ibatis.session;

import java.sql.Connection;

/**
 * @author Clinton Begin
 */
public enum TransactionIsolationLevel {
  NONE(Connection.TRANSACTION_NONE),

  /*
  数据库事务的四种隔离级别
   */
  /**
   * <b>读提交</b>，就是专门针对“不可重复读”这种情况而制定的隔离级别，自然，它就可以有效的避免“不可重复读”。
   * 现象：能避免“丢失数据”和“脏数据”，事务1能读到其他已提交事务的修改，出现“不可重复读”的问题。
   * 原理：写数据的时候加上X锁， 直到事务结束，  读的时候加上S锁， 读完数据立刻释放。(共享锁规则1)
   * level为2
   */
  READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
  /**
   * <b>读未提交</b>，顾名思义，就是可以读到未提交的内容。因此，在这种隔离级别下，查询是不会加锁的，也由于查询的不加锁，
   * 脏数据
   * 现象：不会丢失数据，事务1还未提交的修改能被事务2读取。可以读到没有提交或者回滚的内容 （脏数据）。
   * 原理：写数据时加上X锁，直到事务结束， 读的时候不加锁。
   * 所以这种隔离级别的一致性是最差的，可能会产生“脏读”、“不可重复读”、“幻读”。
   * level为1
   */
  READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
  /**
   * <b>可重复读</b>，就是专门针对“不可重复读”这种情况而制定的隔离级别，自然，它就可以有效的避免“不可重复读”。
   * 现象：能避免“丢失数据”和“脏数据”， “不可重复读”三个问题，事务1能读取到其他事务新插入读数据，出现“幻读”的问题。
   * 原理：写数据的时候加上X锁，  直到事务结束， 读数据的时候加S锁， 也是直到事务结束。(共享锁规则2)
   * 而它也是MySql的默认隔离级别。level为4
   */
  REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
  /**
   * <b>串行化</b>，这是数据库最高的隔离级别，这种级别下，事务“串行化顺序执行”，也就是一个一个排队执行。
   * level为8
   */
  SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE),
  /**
   * A non-standard isolation level for Microsoft SQL Server.
   * Defined in the SQL Server JDBC driver {@link com.microsoft.sqlserver.jdbc.ISQLServerConnection}<br>
   * Microsoft SQL Server 的非标准隔离级别。
   * 在 SQL Server JDBC 驱动程序 {@link com.microsoft.sqlserver.jdbc.ISQLServerConnection} 中定义
   * @since 3.5.6
   */
  SQL_SERVER_SNAPSHOT(0x1000);

  private final int level;

  TransactionIsolationLevel(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }
}
