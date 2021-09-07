package headfirst.builder;

/**
 * 建造者模式，灵活构建类
 */
public class CarConfig {

    private String seat;

    public String getSeat() {
        return seat;
    }

    //隐藏默认构造
    private CarConfig(){};

    public CarConfig(Bulder bulder){
        this.seat = bulder.seat;
    };

    public static class Bulder{
        private String seat;

        public Bulder setSeat(String seat){
            //可以在设置属性时判断传参
            if("".equals(seat)){
                throw new IllegalArgumentException("不能输入空参数");
            }
            this.seat = seat;
            return this;
        }


        public CarConfig build(){
            if(null == this.seat){
                throw new IllegalArgumentException("需要输入座椅参数");
            }
            return new CarConfig(this);
        }
    }
}

