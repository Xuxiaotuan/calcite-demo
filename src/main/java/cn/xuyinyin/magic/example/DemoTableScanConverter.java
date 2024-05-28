package cn.xuyinyin.magic.example;

import org.apache.calcite.adapter.enumerable.EnumerableConvention;
import org.apache.calcite.adapter.enumerable.EnumerableTableScan;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelTrait;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelDistributionTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 19:55
 */
public class DemoTableScanConverter extends ConverterRule {

    public static final DemoTableScanConverter INSTANCE = new DemoTableScanConverter(
            EnumerableTableScan.class,
            EnumerableConvention.INSTANCE,
            DemoRel.CONVENTION,
            "DemoTableScan"
    );

    public DemoTableScanConverter(Class<? extends RelNode> clazz, RelTrait in, RelTrait out, String descriptionPrefix) {
        super(clazz, in, out, descriptionPrefix);
    }

    @Override
    public boolean matches(RelOptRuleCall call) {
        return super.matches(call);
    }


    @Override
    public RelNode convert(RelNode rel) {
        EnumerableTableScan tableScan = (EnumerableTableScan) rel;
        return new DemoTableScan(tableScan.getCluster(),
                RelTraitSet.createEmpty().plus(DemoRel.CONVENTION).plus(RelDistributionTraitDef.INSTANCE.getDefault()), tableScan.getTable());
    }
}
