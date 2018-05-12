package com.maochong.thread.controller;

public class TestController {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true){
            if(td.isFlag()){
                System.out.println("-------true------");
                break;
            }

        }
    }

    static class ThreadDemo implements Runnable{

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        private volatile boolean flag = false;

        @Override
        public void run() {
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException ex){

            }
            setFlag(true);
            System.out.println("flag = "+ isFlag());
        }
    }
}
