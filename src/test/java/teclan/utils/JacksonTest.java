package teclan.utils;

import org.junit.Test;

public class JacksonTest {

    @Test
    public void xmlTest() {
        Cls cls = new Cls(100, "teclan");

        String xml = JacksonUtils.object2xml(cls);
        System.out.println("xml = " + xml);

        cls = JacksonUtils.fromXml(xml, Cls.class);
        System.out.println("cls = " + cls.toString());
    }

    @Test
    public void jsonTest1() {
        Cls cls = new Cls(100, "teclan");
        String json = GsonUtils.toJson(cls);

        String xml = JacksonUtils.json2xml(json);
        System.out.println("xml = " + xml);

        cls = JacksonUtils.fromXml(xml, Cls.class);
        System.out.println("cls = " + cls.toString());
    }

    @Test
    public void jsonTest2() {
        Cls cls = new Cls(100, "teclan");
        String json = JacksonUtils.toJson(cls);
        System.out.println("json = " + json);

        String xml = JacksonUtils.json2xml(json);
        System.out.println("xml = " + xml);

        cls = JacksonUtils.fromXml(xml, Cls.class);
        System.out.println("cls = " + cls.toString());
    }

    @Test
    public void xml2Json() {
        Cls cls = new Cls(100, "teclan");
        String xml = JacksonUtils.object2xml(cls);
        System.out.println("xml = " + xml);

        String json = JacksonUtils.xml2json(xml);
        System.out.println("json = " + json);

        xml = JacksonUtils.json2xml(json);
        System.out.println("xml = " + xml);

        cls = JacksonUtils.fromXml(xml, Cls.class);
        System.out.println("cls = " + cls.toString());

    }

    @Test
    public void xml2File() {
        Cls cls = new Cls(100, "teclan");
        JacksonUtils.object2xml(cls, "cls.xml");
    }

    @Test
    public void xmlFile2Object() {
        Cls cls = new Cls(100, "teclan");

        JacksonUtils.object2xml(cls, "cls.xml");

        cls = JacksonUtils.fromXmlFile("cls.xml", Cls.class);
        System.out.println("cls = " + cls.toString());

    }
}
