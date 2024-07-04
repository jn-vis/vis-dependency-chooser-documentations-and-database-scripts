package com.ccp.vis.tests.resume;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.jn.vis.commons.utils.VisCommonsUtils;
import com.vis.commons.entities.VisEntityResume;

public class GetSkillsFromResume {

	@Test
	public void curriculoQueOriginalmenteContenhaPalavraAngularDeveConterTambemPalavraJavaScriptAlemDeConterPalavraAngularJs() {
		
		CcpJsonRepresentation json = new CcpStringDecorator("documentation\\tests\\resume\\curriculoQueOriginalmenteContenhaPalavraAngularDeveConterTambemPalavraJavaScriptAlemDeConterPalavraAngularJs.json").file().asSingleJson();
		CcpJsonRepresentation jsonWithSkills = VisCommonsUtils.getJsonWithSkills(json,
				VisEntityResume.Fields.resumeText.name(), VisEntityResume.Fields.skill.name());
		
		boolean testPassed = jsonWithSkills
			.itIsTrueThatTheFollowingFields(VisEntityResume.Fields.skill.name())
			.ifTheyAreAllArrayValuesThenEachOne()
			.isTextAndItIsContainedAtTheList("ANGULARJS", "JAVASCRIPT", "ANGULAR", "JS", "FRONTEND");
			;
		assertTrue(testPassed);
	}
	
	@Test
	public void salvarCurriculoDepoisAlterarCurriculoVerificandoSeSkillsForamAlteradas() {
		
	}
	

}
