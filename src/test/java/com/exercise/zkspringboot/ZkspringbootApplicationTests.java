package com.exercise.zkspringboot;

import com.exercise.zkspringboot.model.Form;
import com.exercise.zkspringboot.service.FormService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@TestPropertySource(locations= {"classpath:application.properties"})
@SpringBootTest
class ZkspringbootApplicationTests {

	@Autowired
	FormService formService;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	public static void setUpClass() {
		log.info("!!!!!! unittest Starting !!!!!!!");
	}

	@AfterAll
	public static void tearDownClass() {
		log.info("!!!!!! unittest finished !!!!!!!");
	}

	@Test
	public void testFormService() throws Exception {
		log.info("!!!!!! testing !!!!!!!");
		String expectValue = "Form KB Appr1";
		log.info(formService.getById(Form.builder().formId(1).build()).toString());
		assertEquals(expectValue, formService.getById(Form.builder().formId(1).build()).getAbbr());
	}

}
