package com.example.AvitoDemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



class AvitoDemoApplicationTests {

	@Test
	void test(){
		Calultor calultor = new Calultor();
		int res = calultor.add(20,30);
		assertThat(res).isEqualTo(50);

	}

	class Calultor{
		int add(int a,int b ){
			return a+b;
		}
	}

//	void contextLoads() {
//	}



}
