[![Build Status](https://travis-ci.com/ccaspanello/kettle-integration-tests.svg?branch=master)](https://travis-ci.com/ccaspanello/kettle-integration-tests)

# Kettle Integration Tests
Framework for running KTRs on the Kettle Engine with row level and output file assertions. 

The goal of this project is to give QA an automated way to test transformations.  This project should do the following:
* Run KTRs and assert the data value and type at each step
* If a transformation writes to an output file; be able to compare that file with an expected result file
* Parameterized JUnits so each test can be reported on
* Know not only when a test fails but at which step it failed and the data element it failed on

## Running Tests
This is simple.  It's a Maven project so just run `mvn clean install` at the root directory.

## Contributing
Please feel free to contribute to this project.  Fork the project, create an issue, make a pull request agaist that
issue and we'll review it.

## License
```
/*******************************************************************************
 Pentaho Data Integration
 Copyright (C) 2018 by Hitachi Vantara : http://www.pentaho.com
******************************************************************************

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with
 the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ````