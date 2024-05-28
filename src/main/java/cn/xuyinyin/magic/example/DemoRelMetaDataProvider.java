package cn.xuyinyin.magic.example;

import com.google.common.collect.ImmutableList;
import org.apache.calcite.rel.metadata.*;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 19:57
 */
public class DemoRelMetaDataProvider extends ChainedRelMetadataProvider {

    public static final DemoRelMetaDataProvider INSTANCE = new DemoRelMetaDataProvider();

    public DemoRelMetaDataProvider() {
        super(ImmutableList.of(
                DemoCumulativeCost.SOURCE,
                RelMdPercentageOriginalRows.SOURCE,
                RelMdColumnOrigins.SOURCE,
                RelMdExpressionLineage.SOURCE,
                RelMdTableReferences.SOURCE,
                RelMdNodeTypes.SOURCE,
                RelMdRowCount.SOURCE,
                RelMdMaxRowCount.SOURCE,
                RelMdMinRowCount.SOURCE,
                RelMdUniqueKeys.SOURCE,
                RelMdColumnUniqueness.SOURCE,
                RelMdPopulationSize.SOURCE,
                RelMdSize.SOURCE,
                RelMdParallelism.SOURCE,
                RelMdDistribution.SOURCE,
                RelMdMemory.SOURCE,
                RelMdDistinctRowCount.SOURCE,
                RelMdSelectivity.SOURCE,
                RelMdExplainVisibility.SOURCE,
                RelMdPredicates.SOURCE,
                RelMdAllPredicates.SOURCE,
                RelMdCollation.SOURCE
        ));
    }
}
