package android.remote.control;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ReceiverThread {
	Socket client;
	BufferedReader br;
	PrintWriter pw;
	SerialArduinoControl serialObj;
	OutputStream os;
}
