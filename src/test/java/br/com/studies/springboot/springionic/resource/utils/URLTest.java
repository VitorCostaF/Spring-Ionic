package br.com.studies.springboot.springionic.resource.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class URLTest {

	@Test
	public void decodeParamTest() {
		assertEquals("test teste", URL.decodeParam("test%20teste"));
		assertEquals("test teste", URL.decodeParam("test teste"));
		assertEquals("", URL.decodeParam(""));
	}
	
	@Test
	public void decodeParamNuloTest() {
		assertEquals("", URL.decodeParam(null));
	}

	@Test
	public void decodeIntListTest() {
		List<Integer> list = URL.decodeIntList("1,2,4");
		assertArrayEquals(Arrays.asList(1, 2, 4).toArray(), list.toArray());

		list = URL.decodeIntList("1");
		assertArrayEquals(Arrays.asList(1).toArray(), list.toArray());
	}
	
	@Test
	public void listaDeNumerosInvalidaTest() {
		Assertions.assertThrows(NumberFormatException.class, () -> URL.decodeIntList("a"));
		Assertions.assertThrows(NumberFormatException.class, () -> URL.decodeIntList("1,b"));
	}
	
	@Test
	public void listaDeNumerosNulaTest() {
		Assertions.assertThrows(NullPointerException.class, () -> URL.decodeIntList(null));
	}

}
