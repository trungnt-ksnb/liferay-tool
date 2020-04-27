<#assign entityName = model.currentEntity.getName()>
package ${model.packagePath}.util;

public class ${entityName}Constant {
	<#list model.currentEntity.getColumns() as column>
		public static final String ${column.getName()?upper_case} = "${column.getName()}";
	</#list>
}
