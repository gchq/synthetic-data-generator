#!/usr/bin/env bash
# Copyright 2018-2021 Crown Copyright
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Check if necessary compiled JAR is present
FILE=target/synthetic-data-generator-*-jar-with-dependencies.jar

if [ -f $FILE ]; then
  # Run the generator
  java -cp $FILE uk.gov.gchq.syntheticdatagenerator.CreateData $@
else
  echo "Cannot find synthetic-data-generator-<version>>-jar-with-dependencies.jar - have you run 'mvn install'?"
fi
