package crypto.preanalysis;

import crypto.analysis.ClassSpecification;
import soot.SootClass;

public class TreeNodeData {
    private SootClass sootClass;
    private ClassSpecification classSpecification;

    public TreeNodeData(SootClass sootClassParam, ClassSpecification classSpecificationParam) {
        setSootClass(sootClassParam);
        setClassSpecification(classSpecificationParam);
    }

    public SootClass getSootClass() {
        return sootClass;
    }

    private void setSootClass(SootClass sootClass) {
        this.sootClass = sootClass;
    }

    public ClassSpecification getClassSpecification() {
        return classSpecification;
    }

    private void setClassSpecification(ClassSpecification classSpecification) {
        this.classSpecification = classSpecification;
    }
}
