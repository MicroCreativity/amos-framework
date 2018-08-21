package ${basePackage}.service.auto.impl;

import ${basePackage}.mapper.auto.${modelNameUpperCamel}Mapper;
import ${basePackage}.model.auto.${modelNameUpperCamel};
import ${basePackage}.service.auto.${modelAutoNameUpperCamel}Service;
import com.amos.framework.service.AbstractService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 /**
 * ===============================
 * 作者：${author}
 * 时间：${date}
 * 内容：${modelAutoNameUpperCamel}ServiceImpl
 * ===============================
*/
@Service
public class ${modelAutoNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelAutoNameUpperCamel}Service {
    

	@Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
