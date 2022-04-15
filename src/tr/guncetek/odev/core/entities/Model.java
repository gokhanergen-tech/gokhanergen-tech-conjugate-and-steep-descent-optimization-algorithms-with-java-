package tr.guncetek.odev.core.entities;


public class Model {

    private Float[] _point;

    private float _epsilon;
    private float _gradientNumber;

    public Model(Float[] parameters, float epsilon, float gradientNumber) {
        if(parameters==null){
            throw new NullPointerException();
        }
        if(epsilon<0){
            throw new IllegalArgumentException();
        }
        this._point = parameters;
        this._epsilon = epsilon;
        this._gradientNumber = gradientNumber;
    }

    public Float[] getPoint() {
        return _point;
    }

    public void setPoint(Float[] point) {
        this._point = point;
    }

    public float getEpsilon() {
        return _epsilon;
    }

    public void setEpsilon(int epsilon) {
        this._epsilon = epsilon;
    }

    public float getGradientNumber() {
        return _gradientNumber;
    }

    public void setGradientNumber(float gradientNumber) {
        this._gradientNumber = gradientNumber;
    }
}
