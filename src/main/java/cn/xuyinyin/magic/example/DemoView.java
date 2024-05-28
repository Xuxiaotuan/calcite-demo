package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.type.RelDataType;

import java.util.List;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 20:01
 */
public class DemoView implements RelOptTable.ViewExpander {
    @Override
    public RelRoot expandView(RelDataType rowType, String queryString, List<String> schemaPath, List<String> viewPath) {
        return null;
    }
}