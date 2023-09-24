package xyz.puppylpg;

import xyz.puppylpg.sealed.Coder;
import xyz.puppylpg.sealed.Junior;
import xyz.puppylpg.sealed.Person;

/**
 * @author liuhaibo on 2023/08/10
 */
public class InstanceOf {

    public static void main(String... args) {

        var person = new Coder();
        old(person);
        patternVariable(person);
    }

    private static void old(Person person) {
        if (person instanceof Coder) {
            Coder coder = (Coder) person;
            coder.say();
        } else if (person instanceof Junior) {
            Junior junior = (Junior) person;
            junior.say();
        }
    }

    private static void patternVariable(Person person) {
        if (person instanceof Coder coder) {
            coder.say();
        } else if (person instanceof Junior junior) {
            junior.say();
        }
    }
}
