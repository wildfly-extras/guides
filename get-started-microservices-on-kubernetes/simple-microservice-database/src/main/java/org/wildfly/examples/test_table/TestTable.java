/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.examples.test_table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class TestTable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String field1;

	private String field2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TestTable testTable = (TestTable) o;
		return Objects.equals(id, testTable.id) && Objects.equals(field1, testTable.field1) && Objects.equals(field2, testTable.field2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, field1, field2);
	}
}