package com.github.jbarus.gradmastercore;

import com.github.jbarus.gradmastercore.runners.MicroserviceRunner;
import com.github.jbarus.gradmastercore.runners.StandaloneRunner;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-s")) {
            new StandaloneRunner().run();
        } else {
            new MicroserviceRunner().run();
        }
    }
}