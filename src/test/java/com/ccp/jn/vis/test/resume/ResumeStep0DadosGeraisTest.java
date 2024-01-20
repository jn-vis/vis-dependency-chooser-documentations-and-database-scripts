package com.ccp.jn.vis.test.resume;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.implementations.db.dao.elasticsearch.CcpElasticSearchDao;
import com.ccp.implementations.db.setup.elasticsearch.CcpElasticSearchDbSetup;
import com.ccp.implementations.db.utils.elasticsearch.CcpElasticSearchDbRequest;
import com.ccp.implementations.http.apache.mime.CcpApacheMimeHttp;
import com.ccp.implementations.json.gson.CcpGsonJsonHandler;

public class ResumeStep0DadosGeraisTest {		//extends TemplateDeTestes{
	/*
	 * onlyHomeOffice: boolean
	 * ddds: int[]
	 * pcd: boolean
	 * disabilities: keyWord[]
	 * companiesNotAllowed: text[]
	 * disponibility: int
	 * observations: text
	 1) Testes genericos:
	 * A) Teste de presença de todos os fields(json, fields)
	 * B) Teste de tipagem de todos os fields(json, key, type)
	 * C) Teste do Lenght de todos os campos keyword e text (json, maxLenght, fields), (json, minLenght, fields), (json, maxLenght, fields), (json, minLenght, fields), , (json, lenght, fields)
	 * D) Teste de não duplicatas dos arrays (json, fields)
	 2) Testes específicos:
	 * E) Teste de domínio dos DDDs e deficiencias (json, domain[], fields), (json, domain[], fields),
	 * F) Teste de não preenchimento das deficiencias se PCD for false
	 */

	// resolvendo o extends TemplateDeTestes
	{
		CcpDependencyInjection.loadAllDependencies(new CcpGsonJsonHandler(), new CcpElasticSearchDao(),
				new CcpElasticSearchDbRequest(), new CcpApacheMimeHttp(), new CcpElasticSearchDbSetup());		
	}

	@Test
	public void testFieldsPresent() {
		CcpJsonRepresentation json =  CcpConstants.EMPTY_JSON
				.put("onlyHomeOffice", true)// TODO JASSON, FAÇA TESTES DE PREENCHIMENTOS VARIADOS DO JSON
				;
		
		
		boolean x = json.containsAllKeys("onlyHomeOffice", "ddds", "pcd", "disabilities", "companiesNotAllowed",
				"disponibility", "observations");
		assertTrue(x);
	}

	@Test
	public void testFieldsNonDuplicatedItems() {
		CcpJsonRepresentation giveOneJson =  CcpConstants.EMPTY_JSON
				.put("onlyHomeOffice", true)// TODO JASSON, FAÇA TESTES DE PREENCHIMENTOS VARIADOS DO JSON
				;
		
		boolean x = giveOneJson.itIsTrueThatTheFollowingFields("ddds", "disabilities", "companiesNotAllowed").ifTheyAreAllArrayValuesThenEachOne().hasNonDuplicatedItems();
		assertTrue(x);
	}

	@Test
	public void testAllowedValues() {
		CcpJsonRepresentation giveOneJson =  CcpConstants.EMPTY_JSON
				.put("onlyHomeOffice", true)// TODO JASSON, FAÇA TESTES DE PREENCHIMENTOS VARIADOS DO JSON
				;
		boolean x = giveOneJson.itIsTrueThatTheFollowingFields("ddds").ifTheyAreAllArrayValuesThenEachOne().isNumberAndItIsContainedAtTheList(11d, 55d);
		assertTrue(x);
		boolean y = giveOneJson.itIsTrueThatTheFollowingFields("disabilities").ifTheyAreAllArrayValuesThenEachOne().isTextAndItIsContainedAtTheList("visual", "auditivo", "motora");
		assertTrue(y);
	}

	@Test
	public void testFieldsTypes() {
		CcpJsonRepresentation giveOneJson =  CcpConstants.EMPTY_JSON
				.put("onlyHomeOffice", true)// TODO JASSON, FAÇA TESTES DE PREENCHIMENTOS VARIADOS DO JSON
				;
		boolean x = giveOneJson.itIsTrueThatTheFollowingFields("companiesNotAllowed", "disabilities", "ddds").areAllOfTheType().list();
		assertTrue(x);
		boolean y = giveOneJson.itIsTrueThatTheFollowingFields("pcd", "onlyHomeOffice").areAllOfTheType().bool();
		assertTrue(y);
		boolean z = giveOneJson.itIsTrueThatTheFollowingFields("disponibility").areAllOfTheType().longNumber();
		assertTrue(z);
	}

	@Test
	public void testMaxLenght() {
		CcpJsonRepresentation giveOneJson = CcpConstants.EMPTY_JSON
				.put("onlyHomeOffice", true)// TODO JASSON, FAÇA TESTES DE PREENCHIMENTOS VARIADOS DO JSON
				;
		boolean x = giveOneJson.itIsTrueThatTheFollowingFields("observations").ifTheyAreAll().textsThenEachOneHasTheSizeThatIs().equalsOrLessThan(500);
		assertTrue(x);
	}
	
	public static void main(String[] args) {
		CcpJsonRepresentation givenTheJson = CcpConstants.EMPTY_JSON;
		//@BooleanFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").areAllOfTheType().bool());
		//@DoubleFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").areAllOfTheType().doubleNumber());
		//@JsonFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").areAllOfTheType().json());
		//@ListFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").areAllOfTheType().list());
		//@LongFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").areAllOfTheType().longNumber());
		//@SetFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().hasNonDuplicatedItems());
		//@FieldArraysWithSizeEqualsTo(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().hasTheSizeThatIs().equalsTo(0));
		//@FieldArraysWithSizeEqualsOrGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().hasTheSizeThatIs().equalsOrGreaterThan(1));
		//@FieldArraysWithSizeEqualsOrLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().hasTheSizeThatIs().equalsOrLessThan(2));
		//@FieldArraysWithSizeGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().hasTheSizeThatIs().greaterThan(3));
		//@FieldArraysWithSizeLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().hasTheSizeThatIs().lessThan(4));
		//@BooleanArrayFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isOfTheType().bool());
		//@DoubleArrayFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isOfTheType().doubleNumber());
		//@JsonArrayFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isOfTheType().json());
		//@ListArrayFields({"field1","field2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isOfTheType().list());
		//@LongArrayFields(fields = {"field1","field2"}, list = {"item1", "item2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isOfTheType().longNumber());
		//@FieldArrayTextsThatAreContainedAtTheList(fields = {"field1","field2"}, list = {"item1", "item2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isTextAndItIsContainedAtTheList("item1", "item2"));
		//@FieldArrayTextsWithSizeEqualsOrLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isTextAnd().hasTheSizeThatIs().equalsTo(0));
		//@FieldArrayTextsWithSizeEqualsOrGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isTextAnd().hasTheSizeThatIs().equalsOrGreaterThan(1));
		//@FieldArrayTextsWithSizeEqualsOrLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isTextAnd().hasTheSizeThatIs().equalsOrLessThan(2));
		//@FieldArrayTextsWithSizeGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isTextAnd().hasTheSizeThatIs().greaterThan(3));
		//@FieldArrayTexstWithSizeLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isTextAnd().hasTheSizeThatIs().lessThan(4));
		//@FieldArrayNumbersThatAreContainedAtTheList(fields = {"field1","field2"}, list = {1d, 2d})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isNumberAndItIsContainedAtTheList(1,2));
		//@FieldArrayNumbersWithValueEqualsTo(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isNumberAndItIs().equalsTo(0));
		//@FieldArrayNumbersWithValueEqualsOrGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isNumberAndItIs().equalsOrGreaterThan(1));
		//@FieldArrayNumbersWithValueEqualsOrLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isNumberAndItIs().equalsOrLessThan(2));
		//@FieldArrayNumbersWithValueGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isNumberAndItIs().greaterThan(3));
		//@FieldArrayNumbersWithValueLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAllArrayValuesThenEachOne().isNumberAndItIs().lessThan(4));
		//@FieldObjectTextsThatAreContainedAtTheList(fields = {"field1","field2"}, list = {"item1", "item2"})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().textsThenEachOneIsContainedAtTheList("item1", "item2"));
		//@FieldObjectTextsWithSizeEqualsTo(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().textsThenEachOneHasTheSizeThatIs().equalsTo(0));
		//@FieldObjectTextsWithSizeEqualsOrGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().textsThenEachOneHasTheSizeThatIs().equalsOrGreaterThan(1));
		//@FieldObjectTextsWithSizeEqualsOrLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().textsThenEachOneHasTheSizeThatIs().equalsOrLessThan(2));
		//@FieldObjectTextsWithSizeGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().textsThenEachOneHasTheSizeThatIs().greaterThan(3));
		//@FieldObjctTextsWithSizeLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().textsThenEachOneHasTheSizeThatIs().lessThan(4));
		//@FieldObjectNumbersThatAreContainedAtTheList(fields = {"field1","field2"}, list = {1d, 2d})
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().numbersThenEachOneIsContainedAtTheList(1, 2));
		//@FieldObjectNumbersWithValueEqualsTo(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().numbersThenEachOneIs().equalsTo(0));
		//@FieldObjectNumbersWithValueEqualsOrGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().numbersThenEachOneIs().equalsOrGreaterThan(1));
		//@FieldObjectNumbersWithValueEqualsOrLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().numbersThenEachOneIs().equalsOrLessThan(2));
		//@FieldObjectNumbersWithValueGreaterThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().numbersThenEachOneIs().greaterThan(3));
		//@FieldObjctNumbersWithValueLessThan(fields={"field1","field2"}, limit=100)
		assertTrue(givenTheJson.itIsTrueThatTheFollowingFields("field1", "field2").ifTheyAreAll().numbersThenEachOneIs().lessThan(4));

	}
}
