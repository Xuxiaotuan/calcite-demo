package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.*;
import org.apache.calcite.plan.volcano.RelSubset;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.metadata.RelMetadataQuery;

import java.lang.reflect.Field;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 20:01
 */
public class DemoVolcanoPlanner extends VolcanoPlanner {

    public DemoVolcanoPlanner() {}

    public DemoVolcanoPlanner(RelOptCostFactory costFactory,
                              Context externalContext) {
        super(costFactory, externalContext);
    }

    public RelOptCost getCost(RelNode rel, RelMetadataQuery mq) {
        assert rel != null : "pre-condition: rel != null";

        if (rel instanceof RelSubset) {
            try {
                Field field = RelSubset.class.getDeclaredField("bestCost");
                field.setAccessible(true);
                return (RelOptCost) field.get(rel);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("this is a test");
            }
        }

        if (rel.getTraitSet().getTrait(ConventionTraitDef.INSTANCE) == Convention.NONE) {
            return costFactory.makeInfiniteCost();
        }

        RelOptCost cost = rel.computeSelfCost(this, mq);

        RelOptCost zeroCost;

        try {
            Field zero = VolcanoPlanner.class.getDeclaredField("zeroCost");
            zero.setAccessible(true);
            zeroCost = (RelOptCost) zero.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        if (!zeroCost.isLt(cost)) {
            cost = costFactory.makeTinyCost();
        }

        for (RelNode input : rel.getInputs()) {
            cost = cost.plus(getCost(input, mq));
        }

        return cost;
    }
}
