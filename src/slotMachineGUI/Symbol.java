package slotMachineGUI;

import java.awt.Image;

public class Symbol implements ISymbol {
	

	private String path;
	private int value;
	
	Symbol(String path, int value){
		this.path = path;
		this.value = value;
	}

	@Override
	public void setImage(String path) {
		this.path = path;
		
	}

	@Override
	public String getImage() {
	  return path;
		
	}

	@Override
	public void setValue(int value) {
		this.value=value;
		
	}

	@Override
	public int getValue() {
		return value;
		
	}
	
	
}
