package example.singleton.instances;

/**
 * @author puppylpg on 2017/12/26
 */
public enum Perfect {
    INSTANCE;

    public String doSomething() {
        return "blabla by " + this.getClass().getSimpleName();
    }
}
