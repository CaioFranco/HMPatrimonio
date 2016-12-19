package br.senai.hm.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class RelatorioScript extends JRDefaultScriptlet {
	
	@Override
	public void afterReportInit() throws JRScriptletException {
		
		BufferedImage buffer;
		try {
			buffer = ImageIO.read(getClass().getResourceAsStream("/rels/hm.png"));
			this.setVariableValue("img", buffer);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
