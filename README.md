# amos lam's framework

## 根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发，支持oracle、mysql数据库

# 配置步骤
## 1、在resources下创建generate-code.properties文件。
```
  #mysql jdbc URL
  generate.jdbc.url=jdbc:mysql://127.0.0.1:3306/amos
  #mysql jdbc 用户名
  generate.jdbc.username=root
  #mysql jdbc 密码
  generate.jdbc.password=123456
  #mysql driver 默认：com.mysql.jdbc.Driver
  #generate.jdbc.driver=
  #项目在硬盘上的位置，默认：System.getProperty("user.dir")
  #generate.project.path=D:/project/study/generator/
  #模板所在项目的位置，默认：System.getProperty("user.dir") + /src/main/resources/generator/template
  #generate.template.file.path=
  #java 文件路径 默认：/src/main/java
  #generate.java.path=
  #java 资源文件路径 默认：/src/main/resources
  #generate.resources.path=
  #生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）
  generate.base.package=com.amos.springboot.rest
  #代码生成作者，缺省amos lam
  generate.author=amos lam

```
## 2、随意新建一个类，集成自CodeGenerator，实现如下方法（注：以下方法支持自定义校验字段，如不需要自定义可不实现，直接调用CodeGenerator就好）。
```
  @Override
	protected List<String> saveExcludeField(List<String> saveExcludeField) {
		
		/*
			默认值
			saveExcludeField.add("id");
			saveExcludeField.add("isDisable");
			saveExcludeField.add("isDeleted");
			saveExcludeField.add("createdTime");
			saveExcludeField.add("modifedTime");
		*/
		return super.saveExcludeField(saveExcludeField);
	}

	@Override
	protected List<String> saveHoldField(List<String> saveHoldField) {
		
		return super.saveHoldField(saveHoldField);
	}

	@Override
	protected List<String> saveHoldAnnotation(List<String> saveAddAnnotation) {
		
		/*
			默认值
		*/
		return super.saveHoldAnnotation(saveAddAnnotation);
	}

	
	
	@Override
	protected List<String> saveExcludeAnnotation(List<String> saveExcludeAnnotation) {
		
		saveExcludeAnnotation.add("exclude");
		return super.saveExcludeAnnotation(saveExcludeAnnotation);
	}

	@Override
	protected List<String> updateExcludeField(List<String> updateExcludeField) {
		
		/*
			默认值
			updateExcludeField.add("createdTime");
			updateExcludeField.add("modifedTime");
		*/
		return super.updateExcludeField(updateExcludeField);
	}

	@Override
	protected List<String> updateHoldField(List<String> updateHoldField) {
		
		return super.updateHoldField(updateHoldField);
	}

	@Override
	public List<String> updateHoldAnnotation(List<String> updateAddAnnotation) {
		
		/*
			默认值
			updateAddAnnotation.add("id");
		*/
		return super.updateHoldAnnotation(updateAddAnnotation);
	}

	@Override
	public List<String> updateExcludeAnnotation(List<String> updateExcludeAnnotation) {
		
		return super.updateExcludeAnnotation(updateExcludeAnnotation);
	}

	@Override
	protected List<String> deleteExcludeField(List<String> deleteExcludeField) {
		
		return super.deleteExcludeField(deleteExcludeField);
	}

	@Override
	protected List<String> deleteHoldField(List<String> deleteHoldField) {
		
		deleteHoldField.add("id");
		return super.deleteHoldField(deleteHoldField);
	}

	@Override
	public List<String> deleteHoldAnnotation(List<String> deleteHoldAnnotation) {
		
		deleteHoldAnnotation.add("id");
		return super.deleteHoldAnnotation(deleteHoldAnnotation);
	}

	@Override
	public List<String> deleteExcludeAnnotation(List<String> deleteExcludeAnnotation) {
		
		return super.deleteExcludeAnnotation(deleteExcludeAnnotation);
	}

	@Override
	protected List<String> getByIdExcludeField(List<String> getByIdExcludeField) {
		
		return super.getByIdExcludeField(getByIdExcludeField);
	}

	@Override
	protected List<String> getByIdHoldField(List<String> getByIdHoldField) {
		
		getByIdHoldField.add("id");
		return super.getByIdHoldField(getByIdHoldField);
	}

	@Override
	protected List<String> getByIdHoldAnnotation(List<String> getByIdHoldAnnotation) {
		
		getByIdHoldAnnotation.add("id");
		return super.getByIdHoldAnnotation(getByIdHoldAnnotation);
	}

	@Override
	protected List<String> getByIdExcludeAnnotation(List<String> getByIdExcludeAnnotation) {
		
		return super.getByIdExcludeAnnotation(getByIdExcludeAnnotation);
	}

	@Override
	protected List<String> findByModelExcludeField(List<String> findByModelExcludeField) {
		
		return super.findByModelExcludeField(findByModelExcludeField);
	}

	@Override
	protected List<String> findByModelHoldField(List<String> findByModelHoldField) {
		
		return super.findByModelHoldField(findByModelHoldField);
	}

	@Override
	protected List<String> findByModelHoldAnnotation(List<String> findByModelHoldAnnotation) {
		
		findByModelHoldAnnotation.add("exclude");
		return super.findByModelHoldAnnotation(findByModelHoldAnnotation);
	}

	@Override
	protected List<String> findByModelExcludeAnnotation(List<String> findByModelExcludeAnnotation) {
		
		return super.findByModelExcludeAnnotation(findByModelExcludeAnnotation);
	}
```

## 3、调用生成器
```
    自定义类继承自CodeGenerator cg = new 自定义类继承自CodeGenerator();
    cg.genCode("src/main/resources/generate-code.properties","user",null);
```
