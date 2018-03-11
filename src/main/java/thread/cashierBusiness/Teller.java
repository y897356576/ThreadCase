package thread.cashierBusiness;

import java.util.concurrent.TimeUnit;

/**
 * Created by 石头 on 2017/7/12.
 */
class Teller implements Runnable, Comparable<Teller> {

    private static int counter = 0;
    private final int id = counter++;

    //Customers served during this shift;
    private int customersServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    Teller(CustomerLine cq) {
        customers = cq;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this) {
                    customersServed++;
                    while(!servingCustomerLine)
                        wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "terminating");
    }

    public synchronized void doSomethingElse() {
        customersServed = 0;
        servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine() {
        assert !servingCustomerLine:"already serving: " + this;
        servingCustomerLine = true;
        notifyAll();
    }

    public String toString() {
        return "Teller " + id + " ";
    }

    public String shortString() {
        return "T" + id;
    }

    @Override
    public synchronized int compareTo(Teller other) {
        return customersServed < other.customersServed ? -1 :
                (customersServed == other.customersServed ? 0 :1 );
    }
}
