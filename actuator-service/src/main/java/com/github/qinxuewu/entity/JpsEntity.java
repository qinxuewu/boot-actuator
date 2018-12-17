package com.github.qinxuewu.entity;
import java.util.List;
public class JpsEntity {
	private String className; //全名
	private String smallName; //小名
	private List<String> parameters; //参数

	public JpsEntity(String className, String smallName, List<String> parameters) {
	        this.className = className;
	        this.smallName = smallName;
	        this.parameters = parameters;
	}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getSmallName() {
			return smallName;
		}

		public void setSmallName(String smallName) {
			this.smallName = smallName;
		}

		public List<String> getParameters() {
			return parameters;
		}

		public void setParameters(List<String> parameters) {
			this.parameters = parameters;
		}

}
