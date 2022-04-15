package tr.guncetek.odev.core.algorithms;

import tr.guncetek.odev.core.consts.Direction;
import tr.guncetek.odev.core.entities.Model;
import tr.guncetek.odev.core.interfaces.DerivationProcess;
import tr.guncetek.odev.core.interfaces.EslenikAlgorithmI;

import java.util.stream.Stream;

public class EslenikGradient extends Algorithm implements EslenikAlgorithmI {


    public EslenikGradient(Model model, DerivationProcess derivationProcess) throws Exception {
        super(model, derivationProcess);
    }

    private Float calculateTeta(Float[] oldGradient) {
        float newMagnitudeOfTheOldGradient=calculateMagnitude(calculateGradient());
        float oldMagnitudeOfTheNewGradient=calculateMagnitude(oldGradient);

        return (float)Math.pow(newMagnitudeOfTheOldGradient/oldMagnitudeOfTheNewGradient,2);
    }

    @Override
    public Float[] newDirectionWithTeta(Float[] oldGradient) {
        float teta=calculateTeta(oldGradient);
        Float[] directedGradient=direction(Direction.NEGATIVE);
        final Float[] newDirectionGradient=new Float[directedGradient.length];
        Stream.iterate(0,i->i+1)
                .limit(2)
                .forEach(i->{
                    newDirectionGradient[i]=directedGradient[i]+teta*oldGradient[i];
                });

        return newDirectionGradient;
    }
}
