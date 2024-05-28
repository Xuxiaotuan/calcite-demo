package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.volcano.RelSubset;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Filter;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.metadata.RelMetadataQuery;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 19:59
 */
public class DemoCostAccumulator {

    public DemoCostAccumulator() {

    }

    public static final DemoCostAccumulator INSTANCE = new DemoCostAccumulator();

    public RelOptCost getAccumulative(RelNode node, RelMetadataQuery mq) {
        return null;
    }

    public RelOptCost getAccumulative(TableScan node, RelMetadataQuery mq) {
        return node.getCluster().getPlanner().getCostFactory().makeCost(1.0, 1.0, 1.0);
    }

    public RelOptCost getAccumulative(Project project, RelMetadataQuery mq) {
        RelNode input = project.getInput();

        double childRows = 0;

        if (input instanceof RelSubset) {
        } else {
        }

        return input.computeSelfCost(project.getCluster().getPlanner(), mq);
    }

    public RelOptCost getAccumulative(Filter filter, RelMetadataQuery mq) {
        RelNode node = filter.getInput();

        if (node instanceof RelSubset) {

        } else {

        }

        return filter.getInput().computeSelfCost(filter.getCluster().getPlanner(), mq);

    }
}