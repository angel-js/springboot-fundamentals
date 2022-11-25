package com.fundamentosplatzi.springboot.fundamentos.component;

import org.springframework.stereotype.Component;

@Component
public class ComponetImplement implements ComponentDependency{

    @Override
    public void saludar() {
        System.out.println("Hola Mundo desde mi componente");
    }
}
