package view.managed.sorting;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class RankingSortingBean implements Serializable 
{
    private SortOrder apellidoNombreOrder = SortOrder.unsorted;
    private SortOrder grauOrder = SortOrder.unsorted;


    public void sortByApellidoNombre() {
        grauOrder = SortOrder.unsorted;
        if (apellidoNombreOrder.equals(SortOrder.ascending)) {
            setApellidoNombreOrder(SortOrder.descending);
        } else {
            setApellidoNombreOrder(SortOrder.ascending);
        }
    }
    
    public void sortByGrau(){
        apellidoNombreOrder = SortOrder.unsorted;
        if (grauOrder.equals(SortOrder.ascending)) {
            setGrauOrder(SortOrder.descending);
        } else {
            setGrauOrder(SortOrder.ascending);
        }        
    }

    public void setApellidoNombreOrder(SortOrder apellidoNombreOrder) {
        this.apellidoNombreOrder = apellidoNombreOrder;
    }

    public SortOrder getApellidoNombreOrder() {
        return apellidoNombreOrder;
    }

    public void setGrauOrder(SortOrder grauOrder) {
        this.grauOrder = grauOrder;
    }

    public SortOrder getGrauOrder() {
        return grauOrder;
    }
}
