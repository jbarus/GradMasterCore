package com.github.jbarus.gradmastercore;

import com.github.jbarus.gradmastercore.runners.MicroserviceRunner;
import com.github.jbarus.gradmastercore.runners.StandaloneRunner;

public class Main {
    public static void main(String[] args) {
        new MicroserviceRunner().run();
    }
}