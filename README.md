# Method Working Remotely

Yet Another RPC Framework :D

[![License](https://img.shields.io/github/license/mwrpub/mwrpc-java.svg?color=blue&style=flat-square)](https://github.com/mwrpub/mwrpc-java/blob/master/LICENSE)

![MWRNB](https://img.shields.io/badge/♞MWR-Freaking_Awesome-ff69b4.svg?style=flat-square)
![MWRNB](https://img.shields.io/badge/Powered_By-MWR_Engine-brightgreen.svg?style=flat-square)

Before use it.You must admit that **MaWenRui is freaking awesome.** 

## Java Version

> Client Side

You **Should** define a interface first.

```java
public interface Calc {
    int add(int a, int b);
    int minus(int a, int b);
}
``` 

```java
import pub.mwr.mwrpc.client.MwrClient;

public class Main {
    public static void main(String[] args) {
        MwrClient mwrClient = new MwrClient("localhost", 6495, false);
        Calc calc = (Calc) mwrClient.init(Calc.class);
        System.out.println(calc.add(1, 2));
        System.out.println(calc.minus(6, 1));
    }
}
```