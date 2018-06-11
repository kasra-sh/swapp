package ir.kasra_sh.swapp;

import ir.kasra_sh.swapp.term.Styled;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;

public class Log {
    private static final Calendar cal = Calendar.getInstance();
    private static PrintStream out = System.out;

    public static void d(String tag, String text) {
        d(tag,text,null);
    }
    public static void d(String tag, String text, Throwable t) {
        StringBuilder l = new StringBuilder(1024);
        l.append("[").append(cal.getTime().toString()).append("]").append(" (D) ").append(tag).append(" - ").append(text);
        if (t != null) {
            StackTraceElement[] e = t.getStackTrace();
            for (int i = 0; i < e.length; i++) {
                l.append("\n").append(Styled.red(e[i].toString()));
            }
        }
        out.println(l.toString());
    }

    public static void i(String tag, String text) {
        i(tag,text,null);
    }

    public static void i(String tag, String text, Throwable t) {
        StringBuilder l = new StringBuilder(1024);
        l.append("[").append(cal.getTime().toString()).append("]").append(" (I) ").append(tag).append(" - ").append(text);
        if (t != null) {
            StackTraceElement[] e = t.getStackTrace();
            for (int i = 0; i < e.length; i++) {
                l.append("\n").append(Styled.red(e[i].toString()));
            }
        }
        out.println(l.toString());
    }

    public static void e(String tag, String text) {
        e(tag, text, null);
    }

    public static void e(String tag, String text, Throwable t) {
        StringBuilder l = new StringBuilder(1024);
        l.append("[").append(cal.getTime().toString()).append("]").append(" (E) ").append(tag).append(" - ").append(text);
        if (t != null) {
            StackTraceElement[] e = t.getStackTrace();
            for (int i = 0; i < e.length; i++) {
                l.append("\n").append(Styled.red(e[i].toString()));
            }
        }
        out.println(Styled.red(l.toString()));
    }

    public static void w(String tag, String text) {
        e(tag, text, null);
    }

    public static void w(String tag, String text, Throwable t) {
        StringBuilder l = new StringBuilder(1024);
        l.append("[").append(cal.getTime().toString()).append("]").append(" (W) ").append(tag).append(" - ").append(text);
        if (t != null) {
            StackTraceElement[] e = t.getStackTrace();
            for (int i = 0; i < e.length; i++) {
                l.append("\n").append(Styled.red(e[i].toString()));
            }
        }
        out.println(Styled.yellow(l.toString()));
    }

    public static void setOutput(OutputStream os) {
        out = new PrintStream(os);
    }
}
