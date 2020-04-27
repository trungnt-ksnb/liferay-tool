package ${model.packagePath};

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.text.SimpleDateFormat;
<#assign moduleName = model.packagePath?substring(model.packagePath?last_index_of(".") + 1)?cap_first>
public class ${moduleName}Portlet extends MVCPortlet {
	<#list model.getEntities() as entity>
		<#assign method = "">
		<#assign methodReturn = "private " + entity.getName()>
		<#assign methodName = "create" + entity.getName() + "ObjectFromRequest">
		<#assign param = "ActionRequest actionRequest">
		<#assign methodBody = "">
		<#assign method = method + methodReturn + " " + methodName + "(" + param + ") {\n">
		<#assign methodBody = methodBody + "\t\t\t" + entity.getName() + " " + entity.getName()?lower_case + " = new " + entity.getName() + "Impl();\n">
		<#list entity.getColumns() as column>
			<#if column.getType()?string == "Date">
				<#assign methodBody = methodBody + "\t\t\t" + entity.getName()?lower_case + ".set" + column.getName()?cap_first + "(ParamUtil.get" + column.getWrapPrimitiveType() + "(actionRequest," + moduleName + "Constant." + column.getName()?upper_case + ", new SimpleDateFormat(\"yyyy-MM-dd\")));\n">
			<#else>
				<#assign methodBody = methodBody + "\t\t\t" + entity.getName()?lower_case + ".set" + column.getName()?cap_first + "(ParamUtil.get" + column.getWrapPrimitiveType() + "(actionRequest," + moduleName + "Constant." + column.getName()?upper_case + "));\n">
			</#if>
		</#list>
		<#assign methodBody = methodBody + "\t\t\treturn " + entity.getName()?lower_case + ";">
		<#assign method = method + methodBody + "\n">
		<#assign method = method + "\t\t}\n">

		${method}
	</#list>
}