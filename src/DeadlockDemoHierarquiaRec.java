import java.util.concurrent.*;

public class DeadlockDemoHierarquiaRec {
    static final Object LOCK_A = new Object();
    static final Object LOCK_B = new Object();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("T1 -> Iniciada");//Inicia T1
            synchronized (LOCK_A) {//Definindo ordem A antes de B
                System.out.println("T1 -> Adqueriu LOCK_A");
                dormir(50);

                System.out.println("T1 -> Tentando LOCK_B"); //Inicia LOCK_B
                synchronized (LOCK_B) {
                    System.out.println("T1 -> Adqueriu LOCK_B"); //LOCK_B Sempre após LOCK_A para garantir a quebra de espera circular
                    System.out.println("T1 -> ------Concluida------");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            System.out.println("T2 -> Iniciada");
            synchronized (LOCK_A) { //Definindo ordem A antes de B
                System.out.println("T2 -> Adquiriu LOCK_A");
                dormir(50);

                System.out.println("T2 -> Tentando LOCK_B");
                synchronized (LOCK_B) {
                    System.out.println("T2 -> Adqueriu LOCK_B");
                    System.out.println("T2 -> ------Concluída------");
                }
            }
        });
        t1.start();
        t2.start();
    }
    static void dormir(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); }
    }
}