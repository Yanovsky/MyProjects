package ru.crystals.pos.fiscalprinter.nonfiscalmode.emulator.exceptions;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ManualExceptionAppender extends Remote {
	public void throwException(ManualFiscalPrinterException exception) throws RemoteException;
	public void resetException() throws RemoteException;

	public void setCashDrawerOpen(boolean open) throws RemoteException;
	public boolean isDrawerOpened() throws RemoteException;

    public String getPrintedDocumentWithOffset(int offset) throws RemoteException;

	public boolean hasCutter() throws RemoteException;

	public void setCutter(boolean cutter) throws RemoteException;

	public boolean isArchiveClosed() throws RemoteException;

	public void setArchiveClosed(boolean fnArchiveClosed) throws RemoteException;
}
