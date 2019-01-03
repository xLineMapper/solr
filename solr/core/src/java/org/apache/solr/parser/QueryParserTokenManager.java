/* Generated By:JavaCC: Do not edit this line. QueryParserTokenManager.java */
package org.apache.solr.parser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.QParser;

/** Token Manager. */
public class QueryParserTokenManager implements QueryParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_2(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_2(int pos, long active0)
{
   return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_2()
{
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 15);
      case 41:
         return jjStopAtPos(0, 16);
      case 42:
         return jjStartNfaWithStates_2(0, 18, 61);
      case 43:
         return jjStartNfaWithStates_2(0, 12, 15);
      case 45:
         return jjStartNfaWithStates_2(0, 13, 15);
      case 58:
         return jjStopAtPos(0, 17);
      case 91:
         return jjStopAtPos(0, 26);
      case 94:
         return jjStopAtPos(0, 19);
      case 123:
         return jjStartNfaWithStates_2(0, 27, 35);
      default :
         return jjMoveNfa_2(0, 0);
   }
}
private int jjStartNfaWithStates_2(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_2(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x1L, 0x0L, 0x0L, 0x0L
};
static final long[] jjbitVec1 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec3 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec4 = {
   0xfffefffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_2(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 61;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 61:
               case 27:
                  if ((0xfbfffcfaffffd9ffL & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 0:
                  if ((0xfbff54f8ffffd9ffL & l) != 0L)
                  {
                     if (kind > 24)
                        kind = 24;
                     jjCheckNAddTwoStates(27, 28);
                  }
                  else if ((0x100002600L & l) != 0L)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if ((0x280200000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 15;
                  else if (curChar == 47)
                     jjCheckNAddStates(0, 2);
                  else if (curChar == 34)
                     jjCheckNAddStates(3, 5);
                  if ((0x7bff50f8ffffd9ffL & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAddStates(6, 10);
                  }
                  else if (curChar == 42)
                  {
                     if (kind > 23)
                        kind = 23;
                  }
                  else if (curChar == 33)
                  {
                     if (kind > 11)
                        kind = 11;
                  }
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 4:
                  if (curChar == 38 && kind > 9)
                     kind = 9;
                  break;
               case 5:
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 13:
                  if (curChar == 33 && kind > 11)
                     kind = 11;
                  break;
               case 14:
                  if ((0x280200000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 15:
                  if ((0x100002600L & l) != 0L && kind > 14)
                     kind = 14;
                  break;
               case 16:
                  if (curChar == 34)
                     jjCheckNAddStates(3, 5);
                  break;
               case 17:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddStates(3, 5);
                  break;
               case 19:
                  jjCheckNAddStates(3, 5);
                  break;
               case 20:
                  if (curChar == 34 && kind > 20)
                     kind = 20;
                  break;
               case 22:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjAddStates(11, 12);
                  break;
               case 23:
                  if (curChar == 46)
                     jjCheckNAdd(24);
                  break;
               case 24:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjCheckNAdd(24);
                  break;
               case 25:
                  if (curChar == 42 && kind > 23)
                     kind = 23;
                  break;
               case 26:
                  if ((0xfbff54f8ffffd9ffL & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 29:
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 30:
               case 32:
                  if (curChar == 47)
                     jjCheckNAddStates(0, 2);
                  break;
               case 31:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 34:
                  if (curChar == 47 && kind > 25)
                     kind = 25;
                  break;
               case 35:
                  if (curChar == 33)
                     jjCheckNAddStates(13, 15);
                  break;
               case 36:
                  if ((0x100002600L & l) != 0L)
                     jjCheckNAddTwoStates(36, 37);
                  break;
               case 37:
                  if ((0xdfffffffffffffffL & l) != 0L)
                     jjCheckNAddStates(16, 19);
                  break;
               case 38:
                  if (curChar == 61)
                     jjCheckNAddStates(20, 25);
                  break;
               case 39:
                  if (curChar == 34)
                     jjCheckNAddStates(26, 28);
                  break;
               case 40:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddStates(26, 28);
                  break;
               case 42:
                  jjCheckNAddStates(26, 28);
                  break;
               case 43:
                  if (curChar == 34)
                     jjCheckNAddStates(13, 15);
                  break;
               case 46:
                  if ((0xfffffdfefffff9ffL & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 47:
                  if (curChar == 39)
                     jjCheckNAddStates(29, 31);
                  break;
               case 48:
                  if ((0xffffff7fffffffffL & l) != 0L)
                     jjCheckNAddStates(29, 31);
                  break;
               case 50:
                  jjCheckNAddStates(29, 31);
                  break;
               case 51:
                  if (curChar == 39)
                     jjCheckNAddStates(13, 15);
                  break;
               case 52:
                  if ((0xfffffffeffffffffL & l) != 0L)
                     jjCheckNAddStates(32, 35);
                  break;
               case 53:
                  if ((0x7bff50f8ffffd9ffL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddStates(6, 10);
                  break;
               case 54:
                  if ((0x7bfff8faffffd9ffL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddTwoStates(54, 55);
                  break;
               case 56:
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddTwoStates(54, 55);
                  break;
               case 57:
                  if ((0x7bfff8faffffd9ffL & l) != 0L)
                     jjCheckNAddStates(36, 38);
                  break;
               case 59:
                  jjCheckNAddStates(36, 38);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 61:
                  if ((0x97ffffff87ffffffL & l) != 0L)
                  {
                     if (kind > 24)
                        kind = 24;
                     jjCheckNAddTwoStates(27, 28);
                  }
                  else if (curChar == 92)
                     jjCheckNAddTwoStates(29, 29);
                  break;
               case 0:
                  if ((0x97ffffff87ffffffL & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAddStates(6, 10);
                  }
                  else if (curChar == 92)
                     jjCheckNAddStates(39, 41);
                  else if (curChar == 123)
                     jjstateSet[jjnewStateCnt++] = 35;
                  else if (curChar == 126)
                  {
                     if (kind > 22)
                        kind = 22;
                     jjstateSet[jjnewStateCnt++] = 22;
                  }
                  if ((0x97ffffff87ffffffL & l) != 0L)
                  {
                     if (kind > 24)
                        kind = 24;
                     jjCheckNAddTwoStates(27, 28);
                  }
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 11;
                  else if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 8;
                  else if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 6;
                  else if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 1:
                  if (curChar == 68 && kind > 9)
                     kind = 9;
                  break;
               case 2:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 3:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 6:
                  if (curChar == 82 && kind > 10)
                     kind = 10;
                  break;
               case 7:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 8:
                  if (curChar == 124 && kind > 10)
                     kind = 10;
                  break;
               case 9:
                  if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 84 && kind > 11)
                     kind = 11;
                  break;
               case 11:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 12:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 17:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjCheckNAddStates(3, 5);
                  break;
               case 18:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 19:
                  jjCheckNAddStates(3, 5);
                  break;
               case 21:
                  if (curChar != 126)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 26:
                  if ((0x97ffffff87ffffffL & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 27:
                  if ((0x97ffffff87ffffffL & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 28:
                  if (curChar == 92)
                     jjCheckNAddTwoStates(29, 29);
                  break;
               case 29:
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 31:
                  jjAddStates(0, 2);
                  break;
               case 33:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 37:
                  if ((0xdfffffffffffffffL & l) != 0L)
                     jjCheckNAddStates(16, 19);
                  break;
               case 40:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjCheckNAddStates(26, 28);
                  break;
               case 41:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 42:
                  jjCheckNAddStates(26, 28);
                  break;
               case 44:
                  if (curChar != 125)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAddTwoStates(45, 46);
                  break;
               case 45:
                  if (curChar == 123)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 46:
                  if ((0xf7ffffffbfffffffL & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAdd(46);
                  break;
               case 48:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjCheckNAddStates(29, 31);
                  break;
               case 49:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 50;
                  break;
               case 50:
                  jjCheckNAddStates(29, 31);
                  break;
               case 52:
                  if ((0xdfffffffffffffffL & l) != 0L)
                     jjCheckNAddStates(32, 35);
                  break;
               case 53:
                  if ((0x97ffffff87ffffffL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddStates(6, 10);
                  break;
               case 54:
                  if ((0x97ffffff87ffffffL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddTwoStates(54, 55);
                  break;
               case 55:
                  if (curChar == 92)
                     jjCheckNAddTwoStates(56, 56);
                  break;
               case 56:
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddTwoStates(54, 55);
                  break;
               case 57:
                  if ((0x97ffffff87ffffffL & l) != 0L)
                     jjCheckNAddStates(36, 38);
                  break;
               case 58:
                  if (curChar == 92)
                     jjCheckNAddTwoStates(59, 59);
                  break;
               case 59:
                  jjCheckNAddStates(36, 38);
                  break;
               case 60:
                  if (curChar == 92)
                     jjCheckNAddStates(39, 41);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 61:
               case 27:
                  if (!jjCanMove_2(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 0:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  if (jjCanMove_2(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 24)
                        kind = 24;
                     jjCheckNAddTwoStates(27, 28);
                  }
                  if (jjCanMove_2(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAddStates(6, 10);
                  }
                  break;
               case 15:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 14)
                     kind = 14;
                  break;
               case 17:
               case 19:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(3, 5);
                  break;
               case 26:
                  if (!jjCanMove_2(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 29:
                  if (!jjCanMove_1(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(27, 28);
                  break;
               case 31:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(0, 2);
                  break;
               case 36:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(36, 37);
                  break;
               case 37:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(16, 19);
                  break;
               case 40:
               case 42:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(26, 28);
                  break;
               case 46:
                  if (!jjCanMove_1(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 48:
               case 50:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(29, 31);
                  break;
               case 52:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(32, 35);
                  break;
               case 53:
                  if (!jjCanMove_2(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddStates(6, 10);
                  break;
               case 54:
                  if (!jjCanMove_2(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddTwoStates(54, 55);
                  break;
               case 56:
                  if (!jjCanMove_1(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAddTwoStates(54, 55);
                  break;
               case 57:
                  if (jjCanMove_2(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(36, 38);
                  break;
               case 59:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(36, 38);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 61 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_0()
{
   return jjMoveNfa_0(0, 0);
}
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 5;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAddTwoStates(2, 3);
                  }
                  else if (curChar == 45)
                     jjCheckNAdd(2);
                  else if (curChar == 61)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 1:
                  if (curChar == 45)
                     jjCheckNAdd(2);
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAddTwoStates(2, 3);
                  break;
               case 3:
                  if (curChar == 46)
                     jjCheckNAdd(4);
                  break;
               case 4:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAdd(4);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 5 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_1(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 34;
            return 6;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_1(int pos, long active0)
{
   return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 84:
         return jjMoveStringLiteralDfa1_1(0x40000000L);
      case 93:
         return jjStopAtPos(0, 31);
      case 125:
         return jjStopAtPos(0, 32);
      default :
         return jjMoveNfa_1(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_1(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_1(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 79:
         if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_1(1, 30, 6);
         break;
      default :
         break;
   }
   return jjStartNfa_1(0, active0);
}
private int jjStartNfaWithStates_1(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_1(state, pos + 1);
}
private int jjMoveNfa_1(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 7;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xfffffffeffffffffL & l) != 0L)
                  {
                     if (kind > 34)
                        kind = 34;
                     jjCheckNAdd(6);
                  }
                  if ((0x100002600L & l) != 0L)
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(2, 4);
                  break;
               case 1:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(2, 4);
                  break;
               case 2:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddStates(42, 44);
                  break;
               case 3:
                  if (curChar == 34)
                     jjCheckNAddStates(42, 44);
                  break;
               case 5:
                  if (curChar == 34 && kind > 33)
                     kind = 33;
                  break;
               case 6:
                  if ((0xfffffffeffffffffL & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAdd(6);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
               case 6:
                  if ((0xdfffffffdfffffffL & l) == 0L)
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAdd(6);
                  break;
               case 2:
                  jjAddStates(42, 44);
                  break;
               case 4:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 8)
                        kind = 8;
                  }
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                  {
                     if (kind > 34)
                        kind = 34;
                     jjCheckNAdd(6);
                  }
                  break;
               case 2:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(42, 44);
                  break;
               case 6:
                  if (!jjCanMove_1(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 34)
                     kind = 34;
                  jjCheckNAdd(6);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 7 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   31, 33, 34, 17, 18, 20, 54, 57, 25, 58, 55, 22, 23, 36, 37, 44, 
   36, 37, 38, 44, 36, 37, 39, 47, 52, 44, 40, 41, 43, 48, 49, 51, 
   36, 37, 52, 44, 57, 25, 58, 56, 59, 29, 2, 4, 5, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 48:
         return ((jjbitVec0[i2] & l2) != 0L);
      default :
         return false;
   }
}
private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec3[i2] & l2) != 0L);
      default :
         if ((jjbitVec1[i1] & l1) != 0L)
            return true;
         return false;
   }
}
private static final boolean jjCanMove_2(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec3[i2] & l2) != 0L);
      case 48:
         return ((jjbitVec1[i2] & l2) != 0L);
      default :
         if ((jjbitVec4[i1] & l1) != 0L)
            return true;
         return false;
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, "\53", 
"\55", null, "\50", "\51", "\72", "\52", "\136", null, null, null, null, null, null, 
"\133", "\173", null, null, "\124\117", "\135", "\175", null, null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "Boost",
   "Range",
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1, -1, -1, -1, 
   -1, 1, 1, -1, 2, -1, 2, 2, -1, -1, 
};
static final long[] jjtoToken = {
   0x7fffffe01L, 
};
static final long[] jjtoSkip = {
   0x100L, 
};
protected CharStream input_stream;
private final int[] jjrounds = new int[61];
private final int[] jjstateSet = new int[122];
protected char curChar;
/** Constructor. */
public QueryParserTokenManager(CharStream stream){
   input_stream = stream;
}

/** Constructor. */
public QueryParserTokenManager(CharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(CharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 61; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(CharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 3 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 2;
int defaultLexState = 2;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   switch(curLexState)
   {
     case 0:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_0();
       break;
     case 1:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_1();
       break;
     case 2:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_2();
       break;
   }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else
        {
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
