package xyz.puppylpg;

import java.util.ArrayList;

/**
 * @author liuhaibo on 2023/08/10
 */
public class Var {

    public static void main(String... args) {
        var a = 1;
        var s = """
                {
                  "beans":[
                    {
                      "name":"Hadoop:service=NameNode,name=NameNodeStatus",
                      "modelerType":"org.apache.hadoop.hdfs.server.namenode.NameNode",
                      "NNRole":"NameNode",
                      "HostAndPort":"puppy-nn0:8000",
                      "SecurityEnabled":false,
                      "LastHATransitionTime":1691379055127,
                      "State":"active"
                    }
                  ]
                }
                """;
        var list = new ArrayList<>();
        list.add(a);
        list.add(s);
        var len = list.size();
        for (var x : list) {
            System.out.println(x);
        }
    }

    /**
     * 和上面方法的字节码一毛一样
     */
    public static void main2(String... args) {
        int a = 1;
        String s = """
                {
                  "beans":[
                    {
                      "name":"Hadoop:service=NameNode,name=NameNodeStatus",
                      "modelerType":"org.apache.hadoop.hdfs.server.namenode.NameNode",
                      "NNRole":"NameNode",
                      "HostAndPort":"puppy-nn0:8000",
                      "SecurityEnabled":false,
                      "LastHATransitionTime":1691379055127,
                      "State":"active"
                    }
                  ]
                }
                """;
        ArrayList list = new ArrayList();
        list.add(a);
        list.add(s);
        int len = list.size();
        for (Object x : list) {
            System.out.println(x);
        }
    }
}
