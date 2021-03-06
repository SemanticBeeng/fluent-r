/*
 * Copyright 2015 David Russell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.onetapbeyond.fluent.r.deployr

import static io.onetapbeyond.fluent.r.deployr.TestConstants.*
import static io.onetapbeyond.fluent.r.DeployRTaskBuilder.*
import static io.onetapbeyond.fluent.r.encoding.DeployRDataFactory.*
import io.onetapbeyond.fluent.r.*
import io.onetapbeyond.fluent.r.tasks.DeployRTask
import com.revo.deployr.client.data.RData

/*
 * DeployR Implicit (DiscreteTaskBroker) Fluent R Integration Tests.
 */
class TaskOnImplicitBrokerTests extends GroovyTestCase {

	private DeployRTask anonTask
	private DeployRTask authTask

	private String endpoint = System.getProperty("endpoint")
	private String username = System.getProperty("username")
	private String password = System.getProperty("password")
	private boolean blindTrust = Boolean.getBoolean("blindTrust")

	void setUp() {

		try {

			// Test: Anonymous [Implicit RBroker] DeployRTask
			anonTask = fluentTask(endpoint)

			// Test: Authenticated [Implicit RBroker] DeployRTask
			authTask = fluentTask(endpoint, username, password)

		} catch(Exception ex) {
			// Test setup failed.
		}
	}

	void tearDown() {
	}

	void testScriptCallNoInputs() {

		def result = anonTask.stream(SCRIPT_CALL_NO_INPUTS)
							 .blindTrust(blindTrust)
							 .execute()

		assert result.success
		assert result.error == null
		assert result.errorCause == null

	}

	void testScriptCallRepeatedNoInputs() {

		def result = anonTask.stream(SCRIPT_CALL_NO_INPUTS)
							 .blindTrust(blindTrust)
							 .execute()

		assert result.success
		assert result.error == null
		assert result.errorCause == null

		result = fluentTask(anonTask).stream(SCRIPT_CALL_NO_INPUTS)
							   		 .blindTrust(blindTrust)
						   			 .execute()

		assert result.success
		assert result.error == null
		assert result.errorCause == null

	}

	void testScriptCallRDataInputs() {

		def result = authTask.stream(SCRIPT_CALL_REQUIRED_INPUTS)
							 .send(RDATA_INPUTS_EXPECTED)
							 .blindTrust(blindTrust)
							 .execute()

		assert result.success
		assert result.error == null
		assert result.errorCause == null

	}

	void testScriptCallMismatchInputs() {

		def result = authTask.stream(SCRIPT_CALL_REQUIRED_INPUTS)
							 .send(RDATA_INPUTS_MISMATCH)
							 .blindTrust(blindTrust)
							 .execute()

		assert !result.success
		assertNotNull result.error
		assertNotNull result.errorCause
		assert result.errorCause instanceof FluentServerException

	}

	void testScriptCallNotFound() {

		def result = authTask.stream(SCRIPT_CALL_NOT_FOUND)
							 .blindTrust(blindTrust)
							 .execute()

		assert !result.success
		assertNotNull result.error
		assertNotNull result.errorCause
		assert result.errorCause instanceof FluentServerException

	}

	void testScriptCallBadSyntax() {

		def result = authTask.stream(SCRIPT_CALL_BAD_SYNTAX)
							 .blindTrust(blindTrust)
							 .execute()

		assert !result.success
		assertNotNull result.error
		assertNotNull result.errorCause
		assert result.errorCause instanceof FluentSyntaxException

	}

	void testScriptCallLoadWd() {

		def result = authTask.stream(SCRIPT_CALL_LOAD_WD)
							 .blindTrust(blindTrust)
							 .execute()

		assert result.success
		assert result.error == null
		assert result.errorCause == null

	}

	void testScriptCallLoadWdNotFound() {

		def result = authTask.stream(SCRIPT_CALL_LOAD_WD_NOT_FOUND)
							 .blindTrust(blindTrust)
							 .execute()

		assert !result.success
		assertNotNull result.error
		assertNotNull result.errorCause
		assert result.errorCause instanceof FluentServerException

	}

	void testScriptCallLoadWdBadSyntax() {

		def result = authTask.stream(SCRIPT_CALL_LOAD_WD_BAD_SYNTAX)
							 .blindTrust(blindTrust)
							 .execute()

		assert !result.success
		assertNotNull result.error
		assertNotNull result.errorCause
		assert result.errorCause instanceof FluentSyntaxException

	}


	void testScriptCallLoadWorkspace() {

		def result = authTask.stream(SCRIPT_CALL_LOAD_WORKSPACE)
							 .blindTrust(blindTrust)
							 .execute()

		assert result.success
		assert result.error == null
		assert result.errorCause == null

	}

	void testScriptCallLoadWorkspaceNotFound() {

		def result = authTask.stream(SCRIPT_CALL_LOAD_WORKSPACE_NOT_FOUND)
							 .blindTrust(blindTrust)
							 .execute()

		assert !result.success
		assertNotNull result.error
		assertNotNull result.errorCause
		assert result.errorCause instanceof FluentServerException

	}

	void testScriptCallLoadWorkspaceBadSyntax() {

		def result = authTask.stream(SCRIPT_CALL_LOAD_WORKSPACE_BAD_SYNTAX)
							 .blindTrust(blindTrust)
							 .execute()

		assert !result.success
		assertNotNull result.error
		assertNotNull result.errorCause
		assert result.errorCause instanceof FluentSyntaxException

	}

}
	