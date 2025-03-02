package com.esprit;

import javax.swing.SwingUtilities;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {
    public static void main(String[] args) {
        log.info("Hello world!");
        SwingUtilities.invokeLater(() -> {
            new ChessGameGUI();
        });
    }
}