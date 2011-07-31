package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.text.WordUtils

class ProseCommand extends Command {

  DocumentFragment fragment
  int width = 80

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^[a-zA-ZæøåÆØÅ]/
  }

  static Object numMatchingLines(List<String> strings, int fromIndex) {
    int index = fromIndex
    while (index < strings.size() && matches(strings, index)) index++
    index - fromIndex
  }

  ProseCommand(DocumentFragment fragment) {
    this.fragment = fragment
  }

  void setWidth(int width) {
    this.width = width
  }

  @Override
  void updateDocument() {
    concatenateToOneLine_orMaybeTwo()
    if (fragment.length == 2) wrapWordsInLine(1)
    wrapWordsInLine(0)
  }

  private def concatenateToOneLine_orMaybeTwo() {
    while (fragment.length > 1) {
      if (cursorIsAtStartOfSecondLine()) { concatenateToTwoLinesInstead(); break }
      addSpaceBetweenLinesAsNewlineReplacement(0)
      fragment.mergeLineWithPrevious(1)
    }
  }

  private def wrapWordsInLine(int y) {
    def lines = wrapWords(fragment.lines[y])
    for (int i = 0; i < lines.size() - 1; i++) {
      fragment.splitAt(lines[i].size() + 1, y + i)
      fragment.chop(y + i)
    }
  }

  private def concatenateToTwoLinesInstead() {
    while (fragment.length > 2) {
      addSpaceBetweenLinesAsNewlineReplacement(1)
      fragment.mergeLineWithPrevious(2)
    }
  }

  private boolean cursorIsAtStartOfSecondLine() {
    return fragment.cursor && fragment.cursor.x == 0 && fragment.cursor.y == 1
  }

  private def addSpaceBetweenLinesAsNewlineReplacement(int y) {
    if (noWhiteSpaceAtEnd(y) || cursorAtEndOfLine(y)) fragment.appendTo(y, " ")
  }

  private boolean cursorAtEndOfLine(int y) {
    return fragment.cursor && fragment.cursor.y == y && fragment.cursor.x == fragment.lines[y].size()
  }

  private boolean noWhiteSpaceAtEnd(int y) {
    return !fragment.lines[y].endsWith(" ")
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    fragment.lines.collect { new FormattedLine(text: it, color: Color.black)}
  }

  private List<String> wrapWords(String string) {
    StringUtils.splitPreserveAllTokens(WordUtils.wrap(string, width), '\n')
  }

}
