package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelTrait;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelDistributionTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalFilter;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 20:03
 */
public class DemoFilterConverter extends ConverterRule {

    public static final DemoFilterConverter INSTANCE = new DemoFilterConverter(
            LogicalFilter.class,
            Convention.NONE,
            DemoRel.CONVENTION,
            "DemoFilterConverter"
    );

    public DemoFilterConverter(Class<? extends RelNode> clazz, RelTrait in, RelTrait out, String descriptionPrefix) {
        super(clazz, in, out, descriptionPrefix);
    }

    @Override
    public boolean matches(RelOptRuleCall call) {
        return super.matches(call);
    }

    @Override
    public RelNode convert(RelNode rel) {
        LogicalFilter filter = (LogicalFilter) rel;
        RelNode input = convert(filter.getInput(),
                filter.getInput().getTraitSet().replace(DemoRel.CONVENTION).simplify());

        return new DemoFilter(
                filter.getCluster(),
                RelTraitSet.createEmpty().plus(DemoRel.CONVENTION).plus(RelDistributionTraitDef.INSTANCE.getDefault()),
                input,
                filter.getCondition()
        );
    }
}