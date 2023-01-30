public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 1.1
        this.doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 1.2
        double element = 0.0;
        try {
            for (int i = 0; i < doubElements.length; i++)
            {
                element = doubElements[_index];
            }
            // for loop to loop round vector and return item at index _index
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException)
        {
            element = -1;
        }
        return element;
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 1.3
        try
        {
            doubElements[_index] = _value;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException)
        {
            doubElements[doubElements.length - 1] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 1.4
        return doubElements;
    }

    public int getVectorSize() {
        //TODO Task 1.5
        return doubElements.length;
    }

    public Vector reSize(int _size) {
        //TODO Task 1.6
        Vector vector = Vector.this;
        if (_size == doubElements.length || _size <= 0)
        {
            vector.doubElements = doubElements;
            return vector;
        }
        else if (_size < doubElements.length) {
            double[] d1 = new double[_size];
            for (int x = 0; x < d1.length; x++)
            {
                d1[x] = doubElements[x];
            }
            vector.doubElements = d1;
            return vector;
        }
        else
        {
            double [] d2 = new double[_size];
            for (int y = 0; y < d2.length; y++)
            {
                if (y < doubElements.length) {
                    d2[y] = doubElements[y];
                }
                else {
                    d2[y] = -1;
                }
            }
            vector.doubElements = d2;
            return vector;
        }
    }

    public Vector add(Vector _v) {
        //TODO Task 1.7
        Vector vector = Vector.this;
        if (_v.getVectorSize() > doubElements.length)
        {
            vector.reSize(_v.doubElements.length);
        }
        else
        {
            _v.reSize(vector.doubElements.length);
        }
        for (int i = 0; i < doubElements.length; i++)
        {
            vector.doubElements[i] = vector.doubElements[i] + _v.doubElements[i];
        }
        return vector;
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 1.8
        Vector vector = Vector.this;
        if (_v.getVectorSize() > doubElements.length)
        {
            vector.reSize(_v.doubElements.length);
        }
        else
        {
            _v.reSize(vector.doubElements.length);
        }
        for (int i = 0; i < doubElements.length; i++)
        {
            vector.doubElements[i] = vector.doubElements[i] - _v.doubElements[i];
        }
        return vector;
    }

    public double dotProduct(Vector _v) {
        //TODO Task 1.9
        Vector vector = Vector.this;
        if (_v.getVectorSize() > doubElements.length)
        {
            vector.reSize(_v.doubElements.length);
        }
        else
        {
            _v.reSize(vector.doubElements.length);
        }
        double dotProduct = 0.0;
        if (_v.doubElements.length == doubElements.length)
        {
            for (int i = 0; i < _v.doubElements.length; i++)
            {
                dotProduct = dotProduct + _v.doubElements[i] * vector.doubElements[i];
            }
        }
        return dotProduct;
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 1.10
        Vector vector = Vector.this;
        if (_v.getVectorSize() > doubElements.length)
        {
            vector.reSize(_v.doubElements.length);
        }
        else
        {
            _v.reSize(vector.doubElements.length);
        }
        double dotProduct = 0.0;
        double varA = 0.0;
        double varB = 0.0;
        for (int x = 0; x < _v.doubElements.length; x++)
        {
            dotProduct+= _v.doubElements[x] * vector.doubElements[x];
            varA += Math.pow(_v.doubElements[x], 2);
            varB += Math.pow(vector.doubElements[x], 2);
        }
        return dotProduct / (Math.sqrt(varA) * Math.sqrt(varB));
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;
        //TODO Task 1.11
        if (v.doubElements.length == ((Vector) _obj).doubElements.length)
            boolEquals = true;
        else
            boolEquals = false;
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
