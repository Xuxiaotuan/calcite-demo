
SQlNode SqlNode() :
{
  // 解析定位
  SqlparserPos pos;
  // 源类型用一个标识符节点来表示
  SqlIdentifier sourceType;
  // 源路径标识为一个字符串，比如 "/path/xxx"
  String souceObj;
  SqlIndentifier targetType;
  SqlParserPos pos,
  SqlNodeList colMapping,
  SqlColMapping colmap,
  String separator;
}

// LOAD语法没有分支结构，一条线下去，获取相对应位置的内容并保存在变量中
<LOAD>
    {
      pos = getPos();
    }
     sourceType = CompoundIdentifier()
 <COLON> // 冒号和源括号在Calcite原生的解析文件里已经定义，我们也能使用
     sourceObj = StringLiteralValue()
 <TO>
     targetType = CompoundIdentifier()
  <COlON>
      targetObj = StringLiteralValue()
      {
      mapPos = getPos();
      }
  <LPAREN>
      {
        colMapping = new SqlNodeList(mapPos);
        colMapping.add(readOneColMapping());
      }
      (
  <COMMA>
      {
        colMapping.add(readOneColMapping());
      }
      )*
  <PRAREN>
      [<SEPARATOR> separator = StringLiteralValue()]
      // 最后构造SqlLoad对象返回
          {
          return new SqlLoad(pos, new SqlLoadSource(sourceType, sourceObj),
            new SqlLoadSource(targetType, targetObj), colMapping, separator);
          }
  }

  // 提取字符串节点的内容函数
  JAVACODE String StringLiteralValue() :
  {
    SqlNode sqlNode = StringLiteral();
    return  ((NlsString) SqlLiteral.value(sqlNode).getValue)
  }

  SqlNode readOneColMapping() :
  {
    SqlIndentifier formCol;
    SqlIndentifier toCol;
    SqlParserPos pos;
  }
  {
            {pos = getPos();}
          formCol = SimpleIdentifier()
          toCol = SimpleIdentifier()
          {
           return new SqlColMapping(pos, formCol, toCol);
          }
}