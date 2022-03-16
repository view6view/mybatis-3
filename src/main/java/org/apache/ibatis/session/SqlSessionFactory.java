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
 * Creates an {@link SqlSession} out of a connection or a DataSource<br>
 * 从连接或数据源中创建一个 {@link SqlSession}
 *
 * @author Clinton Begin
 */
public interface SqlSessionFactory {

  SqlSession openSession();

  /**
   * 指定是否开启自动提交，sql commit的三种方式：
   * @param autoCommit
   * @return
   */
  SqlSession openSession(boolean autoCommit);

  /**
   * 通过连接创建一个 {@link SqlSession}
   * @param connection 数据库连接（会话）对象
   * @return
   */
  SqlSession openSession(Connection connection);

  /**
   * 通过事务隔离级别创建一个 {@link SqlSession}
   * @param level 事务隔离级别枚举对象
   * @return
   */
  SqlSession openSession(TransactionIsolationLevel level);

  /**
   * 通过指定执行器级创建一个 {@link SqlSession}
   * @param execType
   * @return
   */
  SqlSession openSession(ExecutorType execType);

  SqlSession openSession(ExecutorType execType, boolean autoCommit);

  SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

  SqlSession openSession(ExecutorType execType, Connection connection);

  Configuration getConfiguration();

}
