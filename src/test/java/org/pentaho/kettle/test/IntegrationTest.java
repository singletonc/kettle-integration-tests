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

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.RowListener;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMetaDataCombi;
import org.pentaho.kettle.test.model.RowResult;
import org.pentaho.kettle.test.model.Scenario;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parameterized JUnit test.
 * <p>
 * Reads in a list of test Scenarios.  It will run the KTR through the Kettle engine collecting row results as it runs
 * and then asserting the rows for each step against an assertion file and optionally expected output files.
 */
@Slf4j
@RunWith( value = Parameterized.class )
public class IntegrationTest {

  // Injected Scenario from the dataProvider method.
  @Parameterized.Parameter( value = 0 )
  public Scenario scenario;

  @Parameterized.Parameters( name = "{index}: {0}" )
  public static Collection<Scenario> dataProvider() {
    List<Scenario> data = new ArrayList<Scenario>();

    // Scan Test Folder for Scenarios
    String sTestsFolder = IntegrationTest.class.getClassLoader().getResource( "tests" ).getFile();
    File testsFolder = new File( sTestsFolder );
    for ( File folder : testsFolder.listFiles() ) {
      Scenario scenario = new Scenario( folder );
      data.add( scenario );
    }
    return data;
  }

  /**
   * Instantiate the KettleEngine once at the begining of all the tests
   *
   * @throws Exception
   */
  @BeforeClass
  public static void beforeClass() throws Exception {
    log.info( "Kettle Enviornment Initializing . . ." );
    KettleEnvironment.init();
    log.info( "Kettle Environment Initialized" );
  }

  /**
   * Actual Test Execution Logic
   *
   * @throws Exception
   */
  @Test
  public void run() throws Exception {
    log.info( "Scenario {} : {} - {}", scenario.id(), scenario.ktrFile(), scenario.assertionFile() );

    TransMeta transMeta = new TransMeta( scenario.ktrFile().getAbsolutePath() );
    // Set additional test properties
    transMeta.setParameterValue( "targetDirectory", targetFolder() );

    Trans trans = new Trans( transMeta );
    trans.prepareExecution( new String[] {} );

    // Hook up row test row listener to each step
    Map<StepInterface, List<RowResult>> actualResults = new HashMap<StepInterface, List<RowResult>>();
    List<StepMetaDataCombi> steps = trans.getSteps();
    for ( StepMetaDataCombi dataCombi : steps ) {
      List<RowResult> results = new ArrayList<RowResult>();
      RowListener rowListener = new TestRowListener( results );
      dataCombi.step.addRowListener( rowListener );
      actualResults.put( dataCombi.step, results );
    }

    // Execute Transformation
    trans.startThreads();
    trans.waitUntilFinished();

    // TODO Remove this logging in favor of the 2 assertions below
    for ( StepInterface step : actualResults.keySet() ) {
      log.info( "Results for {}: ", step.getStepname() );
      for ( RowResult result : actualResults.get( step ) ) {
        log.info( "  {}", result.getRowMetaInterface().getString( result.getObjects() ) );
      }
    }

    // TODO Use assertionFile to assert result data of each step (each set of step data is defined in a worksheet)
    // PRO TIP:  Create a new method/class to do this; it may get nasty.

    // TODO Find out which files were output and assert with the golden copy in the {scenario}/output folders
    // PRO TIP:  Create a new method/class to do this; it may get nasty.

  }

  /**
   * KTRs that output should be written using the `targetDirectory` parameter so that the output of each tests is output
   * relative to the workspace.  This is good practice for running builds / tests in parallel with Jenkins.
   *
   * @return string that represents target folder
   */
  private String targetFolder() {
    String sClassFolder = IntegrationTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    File classFolder = new File( sClassFolder );
    return classFolder.getParentFile().getAbsolutePath();
  }


}