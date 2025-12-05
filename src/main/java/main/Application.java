package main;

import me.friwi.jcefmaven.*;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Application extends JFrame {
    Application(String startURL, boolean useOSR, boolean isTransparent, String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        CefAppBuilder builder = new CefAppBuilder();
        builder.getCefSettings().windowless_rendering_enabled = useOSR;
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                if (state == CefAppState.TERMINATED)
                    System.exit(0);
            }
        });

        if (args.length > 0)
            builder.addJcefArgs(args);

        CefApp cefApp = builder.build();

        CefClient client = cefApp.createClient();

        CefMessageRouter msgRouter = CefMessageRouter.create();
        client.addMessageRouter(msgRouter);

        CefBrowser browser = client.createBrowser(startURL, useOSR, isTransparent);
        Component browserUI = browser.getUIComponent();

        getContentPane().add(browserUI, BorderLayout.CENTER);
        pack();
        setSize(800, 600);
        setVisible(true);
        setTitle("Dungeon Crawler");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });
    }
}