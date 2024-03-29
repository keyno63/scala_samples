package jp.co.who.swing.calc

import scala.language.postfixOps

object Calculator {
  def calculate(formula: String): String = {
    val tmpStack = new scala.collection.mutable.Stack[String]

    try {
      // 計算式を逆ポーランド記法にする
      val rpn = convertRPN(formula)

      // 計算開始
      while(!rpn.isEmpty) {
        val strTmp = rpn.pop()
        if(strTmp.equals("+") || strTmp.equals("-") || strTmp.equals("×") || strTmp.equals("÷")) {
          val topElem = tmpStack.pop().toFloat
          val secondElem = tmpStack.pop().toFloat

          strTmp match {
            case "+" => tmpStack.push((secondElem + topElem) toString)
            case "-" => tmpStack.push((secondElem - topElem) toString)
            case "×" => tmpStack.push((secondElem * topElem) toString)
            case "÷" => tmpStack.push((secondElem / topElem) toString)
            case _ =>
          }
        }
        else {
          tmpStack.push(strTmp)
        }
      }
      if(tmpStack.top.endsWith(".0")) {
        tmpStack.pop().dropRight(2)
      }
      else {
        tmpStack.pop()
      }
    } catch {
      case e: Exception => "エラー"
    }
  }

  // 計算式を逆ポーランド記法に並べ替える
  def convertRPN(formula: String): scala.collection.mutable.Stack[String] = {
    val tmp: StringBuffer = new StringBuffer
    val st1 = new scala.collection.mutable.Stack[String]
    val st2 = new scala.collection.mutable.Stack[String]

    formula.foreach(
      c =>
        // 演算子が検出されるまで数字を退避
        if ((c >= '0' && c <= '9') || c.equals('.')) {
          tmp.append(c)
        }
        else if (c.equals(')')) {
          if(tmp.length() != 0) {
            st1.push(tmp.toString())
            tmp.setLength(0)
          }
          while(!st2.top.toString().equals("(")) {
            st1.push(st2.pop())
          }
          if(!st2.isEmpty) {
            st2.pop()
          }
        }
        else if (c.equals('(')) {
          st2.push(c.toString())
        }
        else if (c.equals('%')) {
          st1.push((tmp.toString().toFloat / 100).toString())
          tmp.setLength(0)
        }
        // 演算子が加減乗除の場合
        else {
          if(tmp.length() != 0) {
            st1.push(tmp.toString())
            tmp.setLength(0)
          }
          if(!st2.isEmpty && !st2.top.equals("(")) {
            // 計算優先度を比較
            if(chkPriority(c.toString()) == 0 && chkPriority(st2.top) == 1) {
              st2.push(c.toString())
            }
            else {
              st1.push(st2.pop())
            }
          }
          else {
            st2.push(c.toString())
          }
        }
    )

    if (tmp.length() != 0) {
      st1.push(tmp.toString())
      tmp.setLength(0)
    }

    while(!st1.isEmpty) {
      st2.push(st1.pop())
    }

    st2
  }

  // 演算子の優先度を決定
  def chkPriority(str: String): Int = {
    str match {
      case x if x.equals("×") || x.equals("÷") => 0
      case x if x.equals("+") || x.equals("-") => 1
      case _ => 2
    }
  }

}
