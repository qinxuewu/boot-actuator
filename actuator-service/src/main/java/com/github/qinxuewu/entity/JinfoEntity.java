package com.github.qinxuewu.entity;
import java.util.List;
public class JinfoEntity {
	private List<String> noedefault;
    private List<String> commandLine;
    public JinfoEntity(List<String> noedefault, List<String> commandLine) {
        this.noedefault = noedefault;
        this.commandLine = commandLine;
    }
	public List<String> getNoedefault() {
		return noedefault;
	}
	public void setNoedefault(List<String> noedefault) {
		this.noedefault = noedefault;
	}
	public List<String> getCommandLine() {
		return commandLine;
	}
	public void setCommandLine(List<String> commandLine) {
		this.commandLine = commandLine;
	}
	
}
