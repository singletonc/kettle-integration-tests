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

import java.io.File;
import java.io.FileFilter;

/**
 * Java Model that represents a TestRails Test Case
 *
 * Currently assumes a single pair of KTR / Assertion file.  Can be modified to handle multiple pairs if desired; but
 * to keep things simple they can name folders with C000001-a C000001-b or some other naming convention.
 *
 * This was called a Scenario so it does not conflict with other classes in the JUnit library.
 */
@Data
@RequiredArgsConstructor
public class Scenario {

  private final File scenarioFolder;

  public String toString(){
    String msg = "%s - %s";
    return String.format(msg, id(), ktrFile());
  }

  public File ktrFile() {
    return scenarioFolder.listFiles( new FileFilter() {
      public boolean accept( File pathname ) {
        return pathname.getName().endsWith( ".ktr" );
      }
    } )[0];
  }

  public File assertionFile() {
    return scenarioFolder.listFiles( new FileFilter() {
      public boolean accept( File pathname ) {
        return pathname.getName().endsWith( ".xlsx" );
      }
    } )[0];
  }

  public String id() {
    return scenarioFolder.getName();
  }

}
