package ${basePackage}.controller.auto;
import com.amos.framework.input.PageInput;
import com.amos.framework.output.Page;
import com.amos.framework.utils.ConvertUtils;
import ${basePackage}.model.auto.${modelNameUpperCamel};
import ${basePackage}.service.auto.${modelAutoNameUpperCamel}Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ${saveInput};
import ${updateInput};
import ${deleteInput};
import ${getByIdInput};
import ${findByModelInput};

import java.util.List;

/**
 * ===============================
 * 作者：${author}
 * 时间：${date}
 * 内容：${modelAutoNameUpperCamel}Controller
 * ===============================
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelAutoNameUpperCamel}Controller {

    @Autowired
    private ${modelAutoNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/save")
    @ResponseBody
    public Boolean save(@RequestBody ${saveInputClass} request) {
        
        ${modelNameUpperCamel} user = ConvertUtils.convertObject(request, ${modelNameUpperCamel}.class);
        ${modelNameLowerCamel}Service.save(user);
        
        return true;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Boolean delete(@RequestBody ${deleteInputClass} request) {
    	
        ${modelNameLowerCamel}Service.deleteById(request.getId());
        
        return true;
    }

    @PostMapping("/update")
    @ResponseBody
    public Boolean update(@RequestBody ${updateInputClass} request) {
        
        ${modelNameUpperCamel} user = ConvertUtils.convertObject(request, ${modelNameUpperCamel}.class);
        ${modelNameLowerCamel}Service.update(user);
        
        return true;
    }

    @PostMapping("/getById")
    @ResponseBody
    public ${modelNameUpperCamel} getById(@RequestBody ${getByIdInputClass} request) {
    	
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(request.getId());
        return ${modelNameLowerCamel};
    }

    @PostMapping("/findBy${modelNameUpperCamel}")
    @ResponseBody
    public Page<${modelNameUpperCamel}> findBy${modelNameUpperCamel}(@RequestBody PageInput<${findByModelInputClass}> request) {
    
        Page<${modelNameUpperCamel}> result = new Page<${modelNameUpperCamel}>();
        
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        
        result.setDatas(list);
        return result;
    }
}
