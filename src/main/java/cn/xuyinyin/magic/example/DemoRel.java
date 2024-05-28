package cn.xuyinyin.magic.example;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.rel.RelNode;

/**
 * @author : XuJiaWei
 * @since : 2024-05-27 19:54
 */
public interface DemoRel extends RelNode {

    Convention CONVENTION = new Convention.Impl("Demo", DemoRel.class);

}