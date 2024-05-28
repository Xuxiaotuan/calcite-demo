package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.*;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.metadata.RelMetadataQuery;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 19:56
 */
public class DemoTableScan extends TableScan implements DemoRel {

    private RelOptCost cost;

    protected DemoTableScan(RelOptCluster cluster, RelTraitSet traitSet, RelOptTable table) {
        super(cluster, traitSet, table);
    }

    @Override
    public RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
        if (cost != null) return cost;

        cost = DemoRelMetadataQuery.INSTANCE.getCumulativeCost(this);
        return cost;
    }
}