/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.util.List;

/**
 *
 * @author Micro
 */
public class ListaRegistros {
    
    private List<List<Double>> registro;

    public ListaRegistros(List<List<Double>> registro) {
        this.registro = registro;
    }

    public List<List<Double>> getRegistro() {
        return registro;
    }

    public void setRegistro(List<List<Double>> registro) {
        this.registro = registro;
    }
    
    
    
    
    
}
