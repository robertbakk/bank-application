/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package dao;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTests {

	@Test
	@DisplayName("1 + 1 = 2")
	void testInsert() {
		User u  = new User("test","pass","nume",false);
		UserDAO.insert(u);
		User us = UserDAO.findByUsername(u.getUsername());
		assertNotNull(us, "user shouldn't be null");
	}
}
