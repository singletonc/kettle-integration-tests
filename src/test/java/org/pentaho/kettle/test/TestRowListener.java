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
package org.pentaho.kettle.test;

import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.RowListener;
import org.pentaho.kettle.test.model.RowResult;

import java.util.List;

/**
 * Test implementation of the RowListener
 *
 * Collects RowResults as each row is written
 */
public class TestRowListener implements RowListener {

  private final List<RowResult> written;

  public TestRowListener( List<RowResult> written ) {
    this.written = written;
  }

  public void rowReadEvent( RowMetaInterface rowMetaInterface, Object[] objects ) throws KettleStepException {
    // Ignore Read Events - Focusing on results for now
  }

  public void rowWrittenEvent( RowMetaInterface rowMetaInterface, Object[] objects ) throws KettleStepException {
      this.written.add( new RowResult( rowMetaInterface, objects ));
  }

  public void errorRowWrittenEvent( RowMetaInterface rowMetaInterface, Object[] objects ) throws KettleStepException {
    // Ignore Error Events - Focusing on results for now
  }

}
