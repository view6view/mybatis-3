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

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;

/**
 * The primary Java interface for working with MyBatis.
 * Through this interface you can execute commands, get mappers and manage transactions.<br>
 * 使用 MyBatis 的主要 Java 接口。
 * 通过此接口，您可以执行命令、获取映射器和管理事务。<br>
 * 实现了Closeable接口
 * {@code Closeable} 是可以关闭的数据源或目标。
 * 调用close方法释放对象所在的资源
 * 持有（如打开的文件）。
 * @author Clinton Begin
 */
public interface SqlSession extends Closeable {

  /**
   * Retrieve a single row mapped from the statement key.<br>
   * 检索从语句键映射的单行
   * @param <T> the returned object type
   * @param statement
   *          the statement
   * @return Mapped object
   */
  <T> T selectOne(String statement);

  /**
   * Retrieve a single row mapped from the statement key and parameter.<br>
   * 检索从语句键和参数映射的单行。
   * @param <T> the returned object type
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return Mapped object
   */
  <T> T selectOne(String statement, Object parameter);

  /**
   * Retrieve a list of mapped objects from the statement key.<br>
   * 从语句键中检索映射对象列表。
   * @param <E> the returned list element type
   * @param statement Unique identifier matching the statement to use.
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter.<br>
   * 从语句键和参数中检索映射对象列表。
   * @param <E> the returned list element type
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement, Object parameter);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter,
   * within the specified row bounds.<br>
   * 在指定的行范围内，从语句键和参数中检索映射对象列表。
   * @param <E> the returned list element type
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param rowBounds  Bounds to limit object retrieval
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

  /**
   * The selectMap is a special case in that it is designed to convert a list
   * of results into a Map based on one of the properties in the resulting
   * objects.
   * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")<br>
   * selectMap 是一种特殊情况，它旨在转换列表根据结果中的属性之一将结果转换为 Map对象。<br>
   * 例如。 为 selectMap("selectAuthors","id") 返回一个 Map[Integer,Author]
   * @param <K> the returned Map keys type
   * @param <V> the returned Map values type
   * @param statement Unique identifier matching the statement to use.
   * @param mapKey The property to use as key for each value in the list.
   * @return Map containing key pair data.
   */
  <K, V> Map<K, V> selectMap(String statement, String mapKey);

  /**
   * The selectMap is a special case in that it is designed to convert a list
   * of results into a Map based on one of the properties in the resulting
   * objects.<br>
   * selectMap 是一种特殊情况，它旨在转换列表根据结果中的属性之一将结果转换为 Map对象。
   * @param <K> the returned Map keys type
   * @param <V> the returned Map values type
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param mapKey The property to use as key for each value in the list.
   * @return Map containing key pair data.
   */
  <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

  /**
   * The selectMap is a special case in that it is designed to convert a list
   * of results into a Map based on one of the properties in the resulting
   * objects.<br>
   * selectMap 是一种特殊情况，它旨在转换列表根据结果中的属性之一将结果转换为 Map对象。
   * @param <K> the returned Map keys type
   * @param <V> the returned Map values type
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param mapKey The property to use as key for each value in the list.
   * @param rowBounds  Bounds to limit object retrieval
   * @return Map containing key pair data.
   */
  <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.<br>
   * Cursor 提供与 List 相同的结果，只是它使用 Iterator 懒惰地获取数据。
   * @param <T> the returned cursor element type.
   * @param statement Unique identifier matching the statement to use.
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.<br>
   * Cursor 提供与 List 相同的结果，只是它使用 Iterator 懒惰地获取数据。
   * @param <T> the returned cursor element type.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement, Object parameter);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.<br>
   * Cursor 提供与 List 相同的结果，只是它使用 Iterator 懒惰地获取数据。
   * @param <T> the returned cursor element type.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param rowBounds  Bounds to limit object retrieval
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds);

  /**
   * Retrieve a single row mapped from the statement key and parameter
   * using a {@code ResultHandler}.<br>
   * 检索从语句键和参数映射的单行使用 {@code ResultHandler}。
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row
   */
  void select(String statement, Object parameter, ResultHandler handler);

  /**
   * Retrieve a single row mapped from the statement
   * using a {@code ResultHandler}.<br>
   * 检索从语句键和参数映射的单行使用 {@code ResultHandler}。
   * @param statement Unique identifier matching the statement to use.
   * @param handler ResultHandler that will handle each retrieved row
   */
  void select(String statement, ResultHandler handler);

  /**
   * Retrieve a single row mapped from the statement key and parameter using a {@code ResultHandler} and
   * {@code RowBounds}.<br>
   * 使用 {@code ResultHandler} 检索从语句键和参数映射的单行和{@code RowBounds}。
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          the parameter
   * @param rowBounds
   *          RowBound instance to limit the query results
   * @param handler
   *          ResultHandler that will handle each retrieved row
   */
  void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

  /**
   * Execute an insert statement.<br>
   * 执行插入语句。
   * @param statement Unique identifier matching the statement to execute.
   * @return int The number of rows affected by the insert.
   */
  int insert(String statement);

  /**
   * Execute an insert statement with the given parameter object. Any generated
   * autoincrement values or selectKey entries will modify the given parameter
   * object properties. Only the number of rows affected will be returned.<br>
   * 使用给定的参数对象执行插入语句。 任何生成的自动增量值或 selectKey 条目将修改给定参数
   * 对象属性。 只会返回受影响的行数。
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return int The number of rows affected by the insert.
   */
  int insert(String statement, Object parameter);

  /**
   * Execute an update statement. The number of rows affected will be returned.<br>
   * 执行更新语句。 将返回受影响的行数。
   * @param statement Unique identifier matching the statement to execute.
   * @return int The number of rows affected by the update.
   */
  int update(String statement);

  /**
   * Execute an update statement. The number of rows affected will be returned.<br>
   * 执行更新语句。 将返回受影响的行数。
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return int The number of rows affected by the update.
   */
  int update(String statement, Object parameter);

  /**
   * Execute a delete statement. The number of rows affected will be returned.<br>
   * 执行删除语句。 将返回受影响的行数。
   * @param statement Unique identifier matching the statement to execute.
   * @return int The number of rows affected by the delete.
   */
  int delete(String statement);

  /**
   * Execute a delete statement. The number of rows affected will be returned.<br>
   * 执行删除语句。 将返回受影响的行数。
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return int The number of rows affected by the delete.
   */
  int delete(String statement, Object parameter);

  /**
   * Flushes batch statements and commits database connection.
   * Note that database connection will not be committed if no updates/deletes/inserts were called.
   * To force the commit call {@link SqlSession#commit(boolean)}<br>
   * 刷新批处理语句并提交数据库连接。
   * 请注意，如果没有调用更新/删除/插入，则不会提交数据库连接。
   * 强制提交调用 {@link SqlSession#commit(boolean)}
   */
  void commit();

  /**
   * Flushes batch statements and commits database connection.<br>
   * 刷新批处理语句并提交数据库连接。
   * @param force forces connection commit
   */
  void commit(boolean force);

  /**
   * Discards pending batch statements and rolls database connection back.
   * Note that database connection will not be rolled back if no updates/deletes/inserts were called.
   * To force the rollback call {@link SqlSession#rollback(boolean)}<br>
   * 丢弃挂起的批处理语句并回滚数据库连接。
   * 请注意，如果没有调用更新/删除/插入，则不会回滚数据库连接。
   * 强制回滚调用 {@link SqlSession#rollback(boolean)}
   */
  void rollback();

  /**
   * Discards pending batch statements and rolls database connection back.
   * Note that database connection will not be rolled back if no updates/deletes/inserts were called.<br>
   * 丢弃挂起的批处理语句并回滚数据库连接。
   * 请注意，如果没有调用更新/删除/插入，则不会回滚数据库连接。
   * @param force forces connection rollback
   */
  void rollback(boolean force);

  /**
   * Flushes batch statements.<br>
   * 刷新批处理语句。
   * @return BatchResult list of updated records
   * @since 3.0.6
   */
  List<BatchResult> flushStatements();

  /**
   * Closes the session.<br>
   * 关闭连接
   */
  @Override
  void close();

  /**
   * Clears local session cache.<br>
   * 清除缓存
   */
  void clearCache();

  /**
   * Retrieves current configuration.<br>
   * 检索当前配置。
   * @return Configuration
   */
  Configuration getConfiguration();

  /**
   * Retrieves a mapper.<br>
   * 检索映射器。
   * @param <T> the mapper type
   * @param type Mapper interface class
   * @return a mapper bound to this SqlSession
   */
  <T> T getMapper(Class<T> type);

  /**
   * Retrieves inner database connection.<br>
   * 检索内部数据库连接。
   * @return Connection
   */
  Connection getConnection();
}
