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
package org.apache.ibatis.mapping;

import java.sql.ResultSet;

/**
 * @author Clinton Begin
 */
public enum ResultSetType {
  /**
   * behavior with same as unset (driver dependent).
   * 取决于驱动程序相同的行为
   * @since 3.5.0
   */
  DEFAULT(-1),
  /*
  指示 <code>ResultSet</code> 对象类型的常量其光标只能向前移动。
   */
  FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY),
  /*
  指示 <code>ResultSet</code> 对象类型的常量可滚动但通常对数据更改不敏感<code>ResultSet</code> 的基础。
   */
  SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE),
  /*
  指示 <code>ResultSet</code> 对象类型的常量可滚动且通常对数据更改敏感<code>ResultSet</code> 的基础。
   */
  SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);

  private final int value;

  ResultSetType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
