package UserInterface;

public class Testing {
    static class Test {
        int i;

        public Test(int i) {
            this.i = i;
        }

        public void add(int i) {
            this.i += i;
        }

        public int getI() {
            return i;
        }
    }

    public static void main(String[] args) {
        Test t = new Test(10);
        Runnable print = () -> t.add(t.getI()); //Lambdas wait until call to run methods inside the parameters

        t.add(5);

        print.run();

        System.out.println(t.getI());
    }
}
