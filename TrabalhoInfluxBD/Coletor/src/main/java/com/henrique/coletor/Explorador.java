package com.henrique.coletor;


import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class Explorador {
    
    

    private static MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    private static ObjectName name;
    private static String[] cpuLoad = new String[]{"SystemCpuLoad"};
    private static String[] memoriaLivre = new String[]{"FreePhysicalMemorySize"};

    private static File[] f = File.listRoots();
    private static Double memoriaTotal;
    private static Double espacoTotal;
    private static AttributeList list = new AttributeList(0);

    static {
        try {
            name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            memoriaTotal = getTotalPhysicalMemorySize();
            espacoTotal = getTotalSpace();
        } catch (Exception ex) {
            Logger.getLogger(Explorador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static double getTotalPhysicalMemorySize() {
            
        
        try {
            list = mbs.getAttributes(name, new String[]{"TotalPhysicalMemorySize"});
        } catch (InstanceNotFoundException | ReflectionException ex) {
            Logger.getLogger(Explorador.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (list.isEmpty()) {
            return Double.NaN;
        }

        Attribute att = (Attribute) list.get(0);

        long value = ((long) att.getValue());

        // usually takes a couple of seconds before we get real values
        if (value == -1.0) {
            return Double.NaN;
        }
        list.clear();
        // returns a percentage value with 1 decimal point precision
        return (value / 1000000000.0);
    }

    public static double getTotalSpace(){

        return f[0].getTotalSpace() / 1000000000.0;

    }

    public static double getProcessCpuLoad(){

        
        try {
            list = mbs.getAttributes(name, cpuLoad);
        } catch (InstanceNotFoundException | ReflectionException ex) {
            Logger.getLogger(Explorador.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (list.isEmpty()) {
            return Double.NaN;
        }

        Attribute att = (Attribute) list.get(0);
        Double value = (Double) att.getValue();

        // usually takes a couple of seconds before we get real values
        if (value == -1.0) {
            return Double.NaN;
        }
        list.clear();
        // returns a percentage value with 1 decimal point precision
        return ((int) (value * 1000) / 10.0);
    }

    public static double getUsedPhysicalMemorySize(){

        
        try {
            list = mbs.getAttributes(name, memoriaLivre);
        } catch (InstanceNotFoundException | ReflectionException ex) {
            Logger.getLogger(Explorador.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (list.isEmpty()) {
            return Double.NaN;
        }

        Attribute att = (Attribute) list.get(0);

        long value = ((long) att.getValue());

        // usually takes a couple of seconds before we get real values
        if (value == -1.0) {
            return Double.NaN;
        }
        list.clear();
        // returns a percentage value with 1 decimal point precision
        return memoriaTotal - (value / 1000000000.0);
    }

    public static double getUsedSpace(){

        return espacoTotal - f[0].getFreeSpace() / 1000000000.0;

    }
    
    public static double[] getDados(){
        return new double[]{getProcessCpuLoad(), getUsedPhysicalMemorySize(), getUsedSpace()};
    }



}
