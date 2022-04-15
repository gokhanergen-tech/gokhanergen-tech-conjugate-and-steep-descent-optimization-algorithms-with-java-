package tr.guncetek.odev.core.algorithms;

import tr.guncetek.odev.core.entities.Model;
import tr.guncetek.odev.core.interfaces.DerivationProcess;

public class DikAlgorithm extends Algorithm {


    public DikAlgorithm(Model model, DerivationProcess derivationProcess) throws Exception {
        super(model, derivationProcess);
    }

    public float calculateTheValueOfTheParameters(){
        Float[] params=getModel().getPoint();

        float x1=params[0],x2=params[1];

        return x1*x1+x2*x2-2*x1*x2;
    }


}
