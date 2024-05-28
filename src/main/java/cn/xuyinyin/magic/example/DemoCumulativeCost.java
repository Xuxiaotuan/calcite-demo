package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.rel.metadata.*;
import org.apache.calcite.util.BuiltInMethod;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 19:58
 */
public class DemoCumulativeCost implements MetadataHandler<BuiltInMetadata.CumulativeCost> {

    public static final RelMetadataProvider SOURCE =
            ReflectiveRelMetadataProvider.reflectiveSource(
                    BuiltInMethod.CUMULATIVE_COST.method, new DemoCumulativeCost());

    public DemoCumulativeCost() {

    }

    @Override
    public MetadataDef<BuiltInMetadata.CumulativeCost> getDef() {
        return BuiltInMetadata.CumulativeCost.DEF;
    }

    public RelOptCost getCumulativeCost(DemoTableScan tableScan, RelMetadataQuery mq) {
        return DemoCostAccumulator.INSTANCE.getAccumulative(tableScan, mq);
    }

    public RelOptCost getCumulativeCost(DemoProject demoProject, RelMetadataQuery mq) {
        return DemoCostAccumulator.INSTANCE.getAccumulative(demoProject, mq);
    }

    public RelOptCost getCumulativeCost(DemoFilter filter, RelMetadataQuery mq) {
        return DemoCostAccumulator.INSTANCE.getAccumulative(filter, mq);
    }
}
