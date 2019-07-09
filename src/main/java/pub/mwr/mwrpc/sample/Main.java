package pub.mwr.mwrpc.sample;

import pub.mwr.mwrpc.client.MwrClient;

public class Main {
    public static void main(String[] args) {
        MwrClient mwrClient = new MwrClient("mwr-server--ivanlulyf.repl.co", 80, false);
        Calc calc = (Calc) mwrClient.init(Calc.class);
        System.out.println(calc.add(1, 2));
        System.out.println(calc.minus(6, 1));
    }
}
