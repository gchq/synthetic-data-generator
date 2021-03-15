/*
 * Copyright 2018-2021 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.syntheticdatagenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.gchq.syntheticdatagenerator.serialise.AvroSerialiser;
import uk.gov.gchq.syntheticdatagenerator.types.Employee;
import uk.gov.gchq.syntheticdatagenerator.types.Manager;
import uk.gov.gchq.syntheticdatagenerator.types.UserId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public final class CreateDataFile implements Callable<Boolean> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateDataFile.class);
    // When a large number of employees are requested, print the progress as feedback that the process has not frozen
    private static final long PRINT_EVERY = 100_000L;

    private final long numberOfEmployees;
    private final Random random;
    private final File outputFile;

    public CreateDataFile(final long numberOfEmployees, final long seed, final File outputFile) {
        this.numberOfEmployees = numberOfEmployees;
        this.random = new Random(seed);
        this.outputFile = outputFile;
    }

    public Boolean call() {
        if (!outputFile.getParentFile().exists()) {
            boolean mkdirSuccess = outputFile.getParentFile().mkdirs();
            if (!mkdirSuccess) {
                LOGGER.warn("Failed to create parent directory {}", outputFile.getParent());
            }
        }
        try (OutputStream out = new FileOutputStream(outputFile)) {
            AvroSerialiser<Employee> employeeAvroSerialiser = new AvroSerialiser<>(Employee.class);

            // Need at least one Employee
            Employee firstEmployee = Employee.generate(random);
            Manager[] managers = firstEmployee.getManager();
            managers[0].setUid("Bob");
            firstEmployee.setManager(managers);

            // Create more employees if needed
            Stream<Employee> employeeStream = Stream.of(firstEmployee);
            if (numberOfEmployees > 1) {
                employeeStream = Stream.concat(employeeStream, generateStreamOfEmployees());
            }

            // Serialise stream to output
            employeeAvroSerialiser.serialise(employeeStream, out);
            return true;
        } catch (IOException ex) {
            LOGGER.error("IOException when serialising Employee to Avro", ex);
            return false;
        }
    }

    private Stream<Employee> generateStreamOfEmployees() {
        LOGGER.info("Generating {} employees", numberOfEmployees);
        final AtomicLong counter = new AtomicLong(0);
        Stream<Employee> employeeStream = Stream.generate(() -> {
            if (counter.incrementAndGet() % PRINT_EVERY == 0) {
                LOGGER.info("Processing {} of {}", counter.get(), numberOfEmployees);
            }
            return Employee.generate(random);
        });
        // Excluding the one employee we had to generate above
        return employeeStream.limit(numberOfEmployees - 1);
    }
}
