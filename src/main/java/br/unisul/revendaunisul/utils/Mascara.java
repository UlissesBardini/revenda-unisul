package br.unisul.revendaunisul.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class Mascara {

	public static MaskFormatter criar(String pattern) {
		try {
			MaskFormatter mascara = new MaskFormatter(pattern);
			mascara.setPlaceholderCharacter('_');
			return mascara;
		} catch (ParseException pe) {
			System.out.println("Máscara Inválida");
			pe.printStackTrace();
			return null;
		}
	}
	
}
