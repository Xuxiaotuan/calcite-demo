package cn.xuyinyin.magic.example;


import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelTrait;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelDistributionTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalProject;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 20:04
 */
public class DemoProjectConverter extends ConverterRule {

    public static final DemoProjectConverter INSTANCE = new DemoProjectConverter(
            LogicalProject.class,
            Convention.NONE,
            DemoRel.CONVENTION,
            "DemoProjectConverter"
    );

    public DemoProjectConverter(Class<? extends RelNode> clazz, RelTrait in, RelTrait out, String descriptionPrefix) {
        super(clazz, in, out, descriptionPrefix);
    }

    @Override
    public boolean matches(RelOptRuleCall call) {
        return super.matches(call);
    }

    @Override
    public RelNode convert(RelNode rel) {
        LogicalProject logicalProject = (LogicalProject) rel;
        RelNode input = convert(logicalProject.getInput(), logicalProject.getInput().getTraitSet().replace(DemoRel.CONVENTION).simplify());

        return new DemoProject(
                logicalProject.getCluster(),
                RelTraitSet.createEmpty().plus(DemoRel.CONVENTION).plus(RelDistributionTraitDef.INSTANCE.getDefault()),
                input,
                logicalProject.getProjects(),
                logicalProject.getRowType()
        );
    }
}