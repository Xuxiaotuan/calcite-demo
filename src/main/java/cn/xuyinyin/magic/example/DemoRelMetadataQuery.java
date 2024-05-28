package cn.xuyinyin.magic.example;

import org.apache.calcite.rel.metadata.JaninoRelMetadataProvider;
import org.apache.calcite.rel.metadata.RelMetadataQuery;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 19:56
 */
public class DemoRelMetadataQuery extends RelMetadataQuery {

    public DemoRelMetadataQuery(JaninoRelMetadataProvider metadataProvider,
                                RelMetadataQuery prototype) {
        super();
    }

    public DemoRelMetadataQuery() {
        super();
    }

    public static DemoRelMetadataQuery instance() {
        THREAD_PROVIDERS.set(JaninoRelMetadataProvider.of(DemoRelMetaDataProvider.INSTANCE));
        return new DemoRelMetadataQuery();
    }

    public static final DemoRelMetadataQuery INSTANCE = instance();

}
