package testnomal;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerByCondition {

    public static void main(String[] args) {
        Container container = new Container(5);
        for (int i = 0; i < 10; i++) {
            new Thread(new Producer(container), "P-" + i).start();
            new Thread(new Consumer(container), "C-" + i).start();
        }
    }

    public static class Product {
        private String name;

        public Product(String name) {
            super();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "[name=" + name + "]";
        }

    }

    public static class Container {
        private int nextIndex = 0;
        Product[] products = null;
        private Lock lock = new ReentrantLock();
        private Condition condition = this.lock.newCondition();

        public Container(int size) {
            this.products = new Product[size > 0 ? size : 5];
        }

        public void push(Product product) {
            this.lock.lock();
            try {
                while (this.nextIndex >= this.products.length) {
                    try {
                         //this.products.wait();
                       this.condition.await();
                    } catch (InterruptedException e) {
                        System.out.println("e");
                        e.printStackTrace();
                    }
                }
                // this.products.notifyAll();
                this.condition.signalAll();
                this.products[nextIndex++] = product;
            } finally {
                this.lock.unlock();
            }
        }

        public Product pop() {
            this.lock.lock();
            try {
                while (this.nextIndex <= 0) {
                    try {
                        // this.products.wait();
                        this.condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // this.products.notifyAll();
                this.condition.signalAll();
                return this.products[--nextIndex];
            } finally {
                this.lock.unlock();
            }
        }
    }

    public static class Producer implements Runnable {

        private Container container;

        public Producer(Container container) {
            super();
            this.container = container;
        }

        @Override
        public void run() {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                Product p = new Product(Thread.currentThread().getName() + "_" + i);
                this.container.push(p);
                System.out.println("生产者[" + Thread.currentThread().getName() + "]生产>>>>>>:" + p);
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static class Consumer implements Runnable {

        private Container container;

        public Consumer(Container container) {
            super();
            this.container = container;
        }

        @Override
        public void run() {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                Product product = this.container.pop();
                System.out.println("消费者[" + Thread.currentThread().getName() + "]消费<<<<<<:" + product);
                try {
                    Thread.sleep((long) (Math.random() * 2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}