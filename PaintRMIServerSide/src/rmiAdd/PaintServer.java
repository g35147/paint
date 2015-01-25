package rmiAdd;

import db.PaintPersistanceException;
import exception.PaintException;
import java.io.IOException;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class PaintServer {

    public PaintServer() throws MalformedURLException {
        try {
            LocateRegistry.createRegistry(1099);
            Paint paint = new Paint();
            Naming.rebind("Paint", paint);
            try {
                paint.loadFigure("premierDessin");
                paint.saveFigure("deuxiemeDessin");
            } catch (PaintPersistanceException ex) {
                ex.printStackTrace();
            } catch (PaintException ex) {
                ex.printStackTrace();
            }
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("Paint Server is ready.");

    }

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
        new PaintServer();
    }

}
