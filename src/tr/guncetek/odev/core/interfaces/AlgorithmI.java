package tr.guncetek.odev.core.interfaces;

import tr.guncetek.odev.core.consts.Direction;

public interface AlgorithmI {
    Float[] calculateGradient();
    Float[] direction(Direction direction) throws Exception;
    Float[] calculateNewParameters(Float[] directedGradient, Float[] previousParameters);
    Float calculateMagnitude(Float[] gradient);
    boolean isLowerEqualEpsilon(Float[] gradient);
}
