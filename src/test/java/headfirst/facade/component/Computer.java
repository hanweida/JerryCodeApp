package headfirst.facade.component;

public class Computer {
    CPU cpu;
    Displayer displayer;
    HardDisk hardDisk;

    public Computer(){
        cpu = new CPU();
        displayer = new Displayer();
        hardDisk = new HardDisk();
    }

    public void openCompument(){
        cpu.startCPU();
        hardDisk.openHardDisk();
        displayer.openDisplay();
    }

    public void closeDisplay(){
        displayer.closeDisplay();
    }
}
