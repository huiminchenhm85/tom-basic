package com.tom.basic.serialize.json;

import java.util.List;

import com.google.common.collect.Lists;
import com.tom.basic.service.ServiceResult;

import junit.framework.TestCase;

public class GsonUtilsTest extends TestCase{
	
	public void testToJson() {
		List<Foo> data = Lists.newArrayList();
		Foo foo = new Foo();
		SubFoo subFoo = new SubFoo();
		subFoo.subName = "sub";
		foo.name = "hello";
		foo.subFoo = subFoo;
		foo.parent = "parent";
		foo.root = "root";
		data.add(foo);
//		ServiceQueryResult<Foo> queryResult = ServiceQueryResult.asSuccess(data);
		
		ServiceResult<Foo> result = ServiceResult.asSuccess(foo);
		
//		System.out.println(queryResult.toJson());
		System.out.println(result.toJson(SubFoo.class));
		
//		System.out.println(new GsonBuilder().create().toJson(foo));
		
//		assertEquals("[['02','2'],['01','1'],['04','04']]", XStringUtils.convetMap2Json(sources1,sources2));
	}

}


class FooRoot{
	String root;
}

@GsonRootClass
class FooParent extends FooRoot{
	String parent;
}

class Foo extends FooParent{
	String name;
	
	SubFoo subFoo;
	
	@GsonField(name="fullName")
	public String getFullName(){
		return "full"+name;
	}
}


class SubFoo{
	String subName;
	
	@GsonField(name="subFullName")
	public String getFullName(){
		return "full"+subName;
	}
}
