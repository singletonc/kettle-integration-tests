/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2018 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.kettle.test.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.pentaho.di.core.row.RowMetaInterface;

/**
 * Java Model that represents a single row result
 *
 * Note:  The reference to the rowMetaInterface seems redundant, but this is how Kettle seems to work today.  This is
 * needed to convert the array of objects into specific types.  For example convert a binary to a string.
 */
@Data
@RequiredArgsConstructor
public class RowResult {

  private final RowMetaInterface rowMetaInterface;
  private final Object[] objects;
}
