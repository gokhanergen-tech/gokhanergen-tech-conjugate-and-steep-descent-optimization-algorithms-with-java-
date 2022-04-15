package tr.guncetek.odev.core.algorithms;

import tr.guncetek.odev.core.consts.Direction;
import tr.guncetek.odev.core.entities.Model;
import tr.guncetek.odev.core.interfaces.AlgorithmI;
import tr.guncetek.odev.core.interfaces.DerivationProcess;

import java.util.Arrays;

abstract class Algorithm  implements AlgorithmI {

    private Model _model;
    private DerivationProcess _derivationProcess;

    public Algorithm(Model model,DerivationProcess derivationProcess) throws Exception {
        if(model == null || derivationProcess==null )
            throw new IllegalArgumentException();
        this._model = model;
        this._derivationProcess=derivationProcess;
    }

    @Override
    public Float[] calculateGradient() {
        return _derivationProcess.derivation(this._model.getPoint());
    }

    @Override
    public Float[] direction(Direction direction) {
        if(direction==null)  throw new IllegalArgumentException();
        int num=direction.compareTo(Direction.POSITIVE)==0?1:-1;
        return Arrays.stream(calculateGradient()).map((value)->num*value).toArray(Float[]::new);
    }

    @Override
    public Float[] calculateNewParameters(Float[] directedGradient, Float[] previousParameters) {
        float gradientValue=getModel().getGradientNumber();

        float g1=directedGradient[0];
        float g2=directedGradient[1];
        float x1=previousParameters[0];
        float x2=previousParameters[1];
        System.out.println(String.format("new parameters =%.6f,%.6f",x1+g1*gradientValue,x2+g2*gradientValue));

        return new Float[]{x1+g1*gradientValue,x2+g2*gradientValue};
    }

    @Override
    public Float calculateMagnitude(Float[] gradient) {
        return ((Double)(Math.sqrt(Arrays.stream(gradient).map((value)->((Double)Math.pow(value,2)).floatValue())
                .reduce(0.0f,(a,b)->a+b)))).floatValue();
    }

    @Override
    public boolean isLowerEqualEpsilon(Float[] gradient) {
        float magnitude = calculateMagnitude(gradient);
        float formattedFloat = Float.parseFloat(String.format("%.6f", magnitude).replace(",", "."));
        System.out.println(formattedFloat+" "+getModel().getEpsilon());
        return formattedFloat <=getModel().getEpsilon();
    }

    public Model getModel() {
        return _model;
    }
}
