<#assign entityName = model.currentEntity.getName()>

package ${model.packagePath}.service.impl;
import ${model.packagePath}.NoSuch${entityName}Exception;
import java.util.Date;
import java.util.List;
import ${model.packagePath}.model.${model.currentEntity.getName()};
import ${model.packagePath}.service.base.${model.currentEntity.getName()}LocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

/**
 * @author ${model.author}
 * @see ${model.packagePath}.service.base.${model.currentEntity.getName()}LocalServiceBaseImpl
 * @see ${model.packagePath}.service.${model.currentEntity.getName()}LocalServiceUtil
 */
public class ${entityName}LocalServiceImpl extends ${entityName}LocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * ${model.packagePath}.service.${model.currentEntity.getName()}LocalServiceUtil} to
	 * access the ${entityName} local service.
	 */
	//crud-delete
	<#assign method = "">
	<#assign methodName = "delete" + entityName>
	<#assign methodReturn = "\tpublic " + entityName>
	<#assign methodThrows = "throws PortalException, SystemException">
	<#assign methodBody = "">
	<#assign primaryColumnName = "">
	<#assign primaryColumnType = "">
	<#list model.currentEntity.getColumns() as column>
		<#if column.isPrimary()>
			<#assign primaryColumnName = column.getName()> 
			<#assign primaryColumnType = column.getType()> 
		</#if>
	</#list>
	<#assign method = methodReturn + " " + methodName + "(" + primaryColumnType + " " + primaryColumnName + ") " + methodThrows + " {\n">
	<#assign method = method + methodBody + "\t\t\treturn " + entityName?lower_case + "Persistence.remove(" + primaryColumnName + ");\n\t\t}">
	${method}
	//crud-update
	<#assign method = "">
	<#assign methodName = "update" + entityName>
	<#assign methodReturn = "\tpublic " + entityName>
	<#assign methodThrows = "throws PortalException, SystemException">
	<#assign methodBody = "">
	<#assign primaryColumnName = "">
	<#list model.currentEntity.getColumns() as column>
		<#if column.isPrimary()>
			<#assign primaryColumnName = column.getName()> 
		</#if>
	</#list>
	<#assign method = methodReturn + " " + methodName + "(" + entityName + " " + entityName?lower_case + ", ServiceContext serviceContext" +  ") " + methodThrows + " {\n">
	<#assign methodBody = methodBody + "\t\t\tif(" + entityName?lower_case + ".get" + primaryColumnName?cap_first + "() == 0){\n">
	<#assign methodBody = methodBody +"\t\t\t\tlong " + primaryColumnName + " = counterLocalService.increment(" + entityName + ".class.getName());\n">
	<#assign methodBody = methodBody +"\t\t\t\t" + entityName?lower_case + ".set" + primaryColumnName?cap_first + "(" + primaryColumnName + ");\n\t\t\t}\n">
	<#assign methodBody = methodBody + "\t\t\treturn " + entityName?lower_case + "Persistence.update(" + entityName?lower_case + ");\n">
	<#assign method = method + methodBody + "\t\t}\n">
	${method}
	<#assign method = "">
	<#assign methodName = "update" + entityName>
	<#assign methodReturn = "\tpublic " + entityName>
	<#assign methodThrows = "throws PortalException, SystemException">
	<#assign param = "">
	<#assign methodBody = "">
	<#assign columSize = model.currentEntity.getColumns()?size>
	<#list model.currentEntity.getColumns() as column>
		<#if column.getName()?string != "createDate" && column.getName()?string != "modifiedDate" && column.getName()?string !="userName">
			<#if column?index == columSize - 1>
				<#assign param = param + column.getType() + " " + column.getName()>
			<#else>
				<#assign param = param + column.getType() + " " + column.getName() + ", ">
			</#if>
		</#if>
	</#list>
	<#assign method = methodReturn + " " + methodName + "(" + param + ") " + methodThrows + " {\n">
	<#assign methodBody = "\t\t\t" + entityName + " " + entityName?lower_case + " = null;\n">
	<#assign methodBody = methodBody + "\t\t\tUser user = userPersistence.findByPrimaryKey(userId);\n">
	<#assign methodBody = methodBody + "\t\t\tDate now = new Date();\n">
	<#list model.currentEntity.getColumns() as column>
		<#if column.isPrimary()>
			<#assign methodBody = methodBody + "\t\t\t" + "if(" + column.getName() + " > 0){\n">
			<#assign methodBody = methodBody + "\t\t\t\t" + entityName?lower_case + " = " + entityName?lower_case + "Persistence.findByPrimaryKey(" + column.getName() + ");\n">
			<#assign methodBody = methodBody +"\t\t\t} else {\n">
			<#assign methodBody = methodBody +"\t\t\t\t" + column.getName() + " = counterLocalService.increment(" + entityName + ".class.getName());\n">
			<#assign methodBody = methodBody +"\t\t\t\t" + entityName?lower_case + " = " +  entityName?lower_case + "Persistence.create("+ column.getName() +  ");\n\t\t\t}\n">
		<#else>
			<#if column.getName()?string == "createDate" || column.getName()?string == "modifiedDate">
				<#assign methodBody = methodBody + "\t\t\t" + entityName?lower_case + ".set" + column.getName()?cap_first + "(now);\n">
			<#elseif column.getName()?string == "userName">
				<#assign methodBody = methodBody + "\t\t\t" + entityName?lower_case + ".setUserName(user.getFullName());\n">
			<#else>
				<#assign methodBody = methodBody + "\t\t\t" + entityName?lower_case + ".set" + column.getName()?cap_first + "(" + column.getName() + ");\n">
			</#if>
		</#if>
	</#list>
	<#assign methodBody = methodBody + "\t\t\treturn " + entityName?lower_case + "Persistence.update(" + entityName?lower_case + ");\n">
	<#assign method = method + methodBody + "\t\t}\n">
	${method}
	//finder
	<#assign findAllMethod = "">
	<#assign countAllMethod = "">
	<#assign param = "">
	<#assign inputParam = "">
	<#assign throws = "throws SystemException">
	<#assign findAllMethodRerurn = "\tpublic List<" + entityName + "> ">
	<#assign findAllMethodName = "findByAll">
	<#assign countMethodName = "countAll">
	<#assign countMethodReturn = "\tpublic long ">
	<#assign countAllMethod = countAllMethod + countMethodReturn + countMethodName + "() " + throws + " {\n">
	<#assign countAllMethod = countAllMethod + "\t\t\treturn " + entityName?lower_case  + "Persistence.countAll();\n\t\t}\n">
	${countAllMethod}
	<#assign findAllMethod = findAllMethod + findAllMethodRerurn + findAllMethodName + "() " + throws + " {\n">
	<#assign findAllMethod = findAllMethod + "\t\t\treturn " + entityName?lower_case  + "Persistence.findAll();\n\t\t}\n">
	${findAllMethod}
	<#assign findAllMethod = "">
	<#assign findAllMethod = findAllMethod + findAllMethodRerurn + findAllMethodName + "(int start, int end) " + throws + " {\n">
	<#assign findAllMethod = findAllMethod + "\t\t\treturn " + entityName?lower_case  + "Persistence.findAll(start, end);\n\t\t}\n">
	${findAllMethod}
	<#list model.currentEntity.getFinders() as finder>
		<#assign finderColumSize = finder.getColumns()?size>
		<#assign returnType = finder.getReturnType()>
		<#assign finderName = finder.getName()>
		<#if returnType == "Collection">
			<#assign findMethod = "">
			<#assign countMethod = "">
			<#assign param = "">
			<#assign inputParam = "">
			<#assign throws = "throws SystemException">
			<#assign methodReturn = "public List<" + entityName + ">">
			<#assign methodName = "findBy" + finderName>
			<#assign countMethodName = "countBy" + finderName>
			<#list finder.getColumns() as column>
				<#if column?index == finderColumSize - 1>
					<#assign param = param + column.getType() + " " + column.getName()>
					<#assign inputParam = inputParam + column.getName()>
				<#else>
					<#assign param = param + column.getType() + " " + column.getName() + ", ">
					<#assign inputParam = inputParam + column.getName() + ", ">
				</#if>
			</#list>
			<#assign methodBody = "\t\t\t" + "return " + entityName?lower_case  + "Persistence.countBy" + finderName + "(" + inputParam + ");\n">
			<#assign countMethod = "public long " + countMethodName + "(" + param + ") " + throws + " {\n">
			<#assign countMethod = countMethod + methodBody + "\t\t}">
			${countMethod}
			<#assign methodBody = "\t\t\t" + "return " + entityName?lower_case  + "Persistence.findBy" + finderName + "(" + inputParam + ");\n">
			<#assign findMethod = methodReturn + " " + methodName + "(" + param + ") " + throws + " {\n">
			<#assign findMethod = findMethod + methodBody + "\t\t}">
			${findMethod}
			<#assign findMethod = "">
			<#assign param = param + ", int start, int end">
			<#assign inputParam = inputParam + ", start, end">
			<#assign methodBody = "\t\t\t" + "return " + entityName?lower_case  + "Persistence.findBy" + finderName + "(" + inputParam + ");\n">
			<#assign findMethod = methodReturn + " " + methodName + "(" + param + ") " + throws + " {\n">
			<#assign findMethod = findMethod + methodBody + "\t\t}">
			${findMethod}
		<#else>
			<#assign findMethod = "">
			<#assign fetchMethod = "">
			<#assign param = "">
			<#assign inputParam = "">
			<#assign fetchMethodThrows = "throws SystemException">
			<#assign findMethodThrows = "throws NoSuch" + entityName + "Exception, SystemException">
			<#assign methodReturn = "public " + entityName>
			<#assign fetchMethodName = "fetchBy" + finderName>
			<#assign findMethodName = "findBy" + finderName>
			<#list finder.getColumns() as column>
				<#if column?index == finderColumSize - 1>
					<#assign param = param + column.getType() + " " + column.getName()>
					<#assign inputParam = inputParam + column.getName()>
				<#else>
					<#assign param = param + column.getType() + " " + column.getName() + ", ">
					<#assign inputParam = inputParam + column.getName() + ", ">
				</#if>
			</#list>
			<#assign methodBody = "\t\t\t" + "return " + entityName?lower_case  + "Persistence.fetchBy" + finderName + "(" + inputParam + ");\n">
			<#assign fetchMethod = methodReturn + " " + fetchMethodName + "(" + param + ") " + fetchMethodThrows + " {\n">
			<#assign fetchMethod = fetchMethod + methodBody + "\t\t}">
			${fetchMethod}
			<#assign methodBody = "\t\t\t" + "return " + entityName?lower_case  + "Persistence.findBy" + finderName + "(" + inputParam + ");\n">
			<#assign findMethod = methodReturn + " " + findMethodName + "(" + param + ") " + findMethodThrows + " {\n">
			<#assign findMethod = findMethod + methodBody + "\t\t}">
			${findMethod}
		</#if>
	</#list>
}
