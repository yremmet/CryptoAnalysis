package crypto.android;

import boomerang.jimple.Statement;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import crypto.extractparameter.CallSiteWithParamIndex;

public class UIHierarchyData {
    private Multimap<CallSiteWithParamIndex, Statement> parametersToValues;

    public void createHeirarchy(){

    }

    public Multimap<CallSiteWithParamIndex, Statement> getParametersToValues() {
        return parametersToValues;
    }

    public void setParametersToValues(Multimap<CallSiteWithParamIndex, Statement> parametersToValues) {
        this.parametersToValues = parametersToValues;
    }
}
