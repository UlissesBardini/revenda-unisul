package br.unisul.revendaunisul.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class Mascara {

	public static MaskFormatter criar(String pattern) {
		return criar(pattern, true);
	}
	
	public static MaskFormatter criar(String pattern, boolean mask) {
		try {
			MaskFormatter mascara = new MaskFormatter(pattern);
			if (mask) {
				mascara.setPlaceholderCharacter('_');
			}
			return mascara;
		} catch (ParseException pe) {
			throw new IllegalArgumentException("O padrão '" + pattern + "' é inválido");
		}
	}
	
}
