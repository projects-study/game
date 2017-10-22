package saveYourLife.model.level;

import saveYourLife.enums.RuleType;

import java.util.List;

public class Rule {

    private RuleType type;
    private Float singleValue;
    private List<Integer> listValue;

    public List<Integer> getListValue() {
        return listValue;
    }

    public void setListValue(List<Integer> listValue) {
        this.listValue = listValue;
    }

    public Float getSingleValue() {

        return singleValue;
    }

    public void setSingleValue(Float singleValue) {
        this.singleValue = singleValue;
    }

    public RuleType getType() {

        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }
}
