package headfirst.proxy.staticProxy;

//主题类，代理类和实际代理类实现此接口
public interface Subject {
    //谈判(代理类完成)
    void talk();

    //签合同(代理类完成)
    void order();

    //拍戏(被代理人完成)
    void film();

    //收钱(代理类完成)
    void checkout();
}
