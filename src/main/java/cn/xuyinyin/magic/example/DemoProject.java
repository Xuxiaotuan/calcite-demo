package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexNode;

import java.util.List;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 20:05
 */
public class DemoProject extends Project implements DemoRel {

    private RelOptCost cost;

    public DemoProject(RelOptCluster cluster, RelTraitSet traits,
                       RelNode input, List<? extends RexNode> projects,
                       RelDataType rowType) {
        super(cluster, traits, input, projects, rowType);
    }

    @Override
    public Project copy(RelTraitSet traitSet, RelNode input, List<RexNode> projects, RelDataType rowType) {
        return null;
    }

    @Override
    public RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
        if (cost != null) return cost;
        cost = DemoRelMetadataQuery.INSTANCE.getCumulativeCost(this);
        return cost;
    }
}
